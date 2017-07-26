define([ "./Logger", "jqueryui/jquery.ui.widget", "./extension" ], function(Logger) {
	var _parseOptions = function(target, context) {
		// 合并配置
		var options = {};
		var s = $.trim(target.attr('data-options'));
		if (s) {
			if (s.substring(0, 1) != '{') {
				s = '{' + s + '}';
			}
			options = (new Function('return ' + s))();
		}
		context.options = $.extend(context.options, options);

		// 处理attach points
		context.attachPointNames = [];
		var attachPoints = context.options.attachPoints;
		if (attachPoints) {
			for ( var selector in attachPoints) {
				var el = target.find(selector);
				var attachPointName = attachPoints[selector].replace(/\s+/g, "");
				if (attachPointName) {
					context[attachPointName] = el;
					context.attachPointNames.push(attachPointName);
				}
			}
		}

		// 处理attach events
		var attachEvents = context.options.attachEvents;
		if (attachEvents) {
			for ( var selector in attachEvents) {
				context._on(target.find(selector), attachEvents[selector]);
			}
		}

		// 处理sub events
		if (context.options.subEvents) {
			context._subscribeAll(context.options.subEvents);
		}
	};

	var _parseAttachPoints = function(target, context) {
		target.find("[data-attach-point]").each(function() {
			var el = $(this);
			var attachPointName = el.attr("data-attach-point").replace(/\s+/g, "");
			if (attachPointName) {
				context[attachPointName] = el;
				context.attachPointNames.push(attachPointName);
			}
		});
	};

	var _parseEvents = function(target, context) {
		target.find("[data-attach-event]").each(function() {
			var el = $(this);
			var cc = el.attr("data-attach-event").replace(/\s+/g, "");
			if (cc.length > 0 && cc.indexOf(":") > 0) {
				cc = cc.replace(/,/g, '","').replace(/:/g, '":"');
				var attachEvts = JSON.parse('{"' + cc + '"}');
				context._on(el, attachEvts);
			}
		});
	};

	var _parseDataGrid = function(target, context) {
		target.find(".easyui-datagrid").each(function() {
			var el = $(this);
			var columns = el.datagrid("options").columns[0];
			_.each(columns, function(col) {
				if (col.formatter && _.isString(col.formatter) && context[col.formatter] && _.isFunction(context[col.formatter])) {
					col.formatter = _.bind(context[col.formatter], context);
				}
			});
		});
	};

	var _parseApps = function(target, templateString) {
		var tps = _.clone(templateString);
		var clss = [];
		tps.replace(/app-\w+/g, function(match, key) {
			clss.push(match);
			return "";
		});

		if (clss.length > 0) {
			clss = _.uniq(clss);
			_.each(clss, function(name) {
				var r = target.find("." + name);
				var f = name.split("-")[1];
				if (r.length) {
					if (r[f]) {
						r[f]();
					}
				}
			});
		}
	};

	return $.widget("app.BaseWidget", {
		options : {
			autoParse : true,

			ajaxLock : false,
			lockMsg : '数据处理中，请稍候  ...',
			locked : false
		},

		_parse : function(target, options) {
			// step1: parse easyui
			$.parser.parse(target);

			// step2: parse data-attach-point
			_parseAttachPoints(target, this);

			// step3: parse data-attach-event
			_parseEvents(target, this);

			// step4: parse easyui-datagrid
			_parseDataGrid(target, this);

			// step5: parse app-class
			if (options && options.templateString) {
				_parseApps(target, options.templateString);
			}
		},

		_create : function() {
			this._super();

			this.subBindings = [];
			this.subs = {};

			Logger.getLogger(this);

			this.widgetId = this.widgetFullName + "_" + this.uuid;
			this.element.attr("wid", this.widgetId);

			// step1: 预处理template
			var _self = this;
			this.templateString = this.templateString.replace(/\$\{([^\s\}]+)\}/g, function(match, key) {
				return _self[key];
			});
			this.element.html(this.templateString);

			// step2: 处理options
			_parseOptions(this.element, this);

			// step3: 处理template中的配置
			var options = {};
			if (this.options.autoParse) {
				options.templateString = this.templateString;
			}
			this._parse(this.element, options);
		},

		// _setOptions is called with a hash of all options that
		// are changing
		// always refresh when changing options
		_setOptions : function(options) {
			// _super and _superApply handle keeping the right
			// this-context
			this._superApply(arguments);
		},

		// _setOption is called for each individual option that
		// is changing
		_setOption : function(key, value) {
			if (key === "autoParse") {
				this.options.autoParse = value;
			} else if (key === "ajaxLock") {
				this.options.ajaxLock = value;
			} else if (key === "lockMsg") {
				this.options.lockMsg = value;
			} else if (key === "locked") {
				this.options.locked = value;
				if (value === true) {
					this.element.mask(this.options.lockMsg);
				} else {
					this.element.unmask();
				}
			}
			this._super(key, value);
		},

		lock : function(msg) {
			msg = msg || this.options.lockMsg;
			this.options.locked = true;
			var inDialog = this.options.inDialog || this.element.find(".dialog").length > 0;
			this.element.mask(this.options.lockMsg, inDialog);
		},

		unlock : function() {
			this.options.locked = false;
			this.element.unmask();
		},

		_publish : function(topic, data) {
			$.publish(topic, data);
		},

		_subscribe : function(topic, handler) {
			this.subs[topic] = this.subs[topic] || [];

			// 确保一个handler只绑定一次
			if (_.indexOf(this.subBindings, handler) < 0) {
				this.subBindings.push(handler);
				this[handler] = _.bind(this[handler], this);
			}

			// 每个事件只订阅一次
			if (_.indexOf(this.subs[topic], handler) < 0) {
				$.subscribe(topic, this[handler]);
				this.subs[topic].push(handler);
			}
		},

		_subscribeAll : function(handlers) {
			_.each(handlers, function(handler, topic) {
				this._subscribe(topic, handler);
			}, this);
		},

		_unsubscribe : function(topic, handler) {
			var handlers = this.subs[topic];
			if (handlers && handlers.length > 0) {
				if (handler) {
					var index = _.indexOf(handlers, handler);
					if (index > -1) {
						$.unsubscribe(topic, this[handler]);
						this.subs[topic] = _.without(this.subs[topic], handler);
					} else {
						// do nothing
					}
				} else {
					var _self = this;
					_.each(handlers, function(handler) {
						$.unsubscribe(topic, _self[handler]);
					});
					delete this.subs[topic];
				}
			}
		},

		_unsubscribeAll : function() {
			for ( var topic in this.subs) {
				this._unsubscribe(topic);
			}
			this.subs = {};
		},

		_destroy : function() {
			// step1: 销毁订阅事件
			this._unsubscribeAll();

			// step2: 销毁attach points
			_.each(this.attachPointNames, function(attachPointName) {
				delete this[attachPointName];
			}, this);

			this._super();
		}
	});
});
