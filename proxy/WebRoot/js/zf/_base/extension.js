define(
		[ "jquery", "underscore", "underscoreplugins/underscore.string.min", "json", "jqueryplugins/jquery.channel", "jqueryui/jquery.ui.widget" ],
		function($, _) {
			// ------------------jquery extension--------------------
			$.extend({
				publish : function(topic, data) {
					$.channel("publish", topic, data);
				},
				subscribe : function(topic, handler) {
					$.channel("subscribe", topic, handler);
				},
				unsubscribe : function(topic, handler) {
					$.channel("unsubscribe", topic, handler);
				}
			});

			// ------------------ jquery ui extension -----------------------
			var proWidgetFactory = $.widget;
			$.widget = function(name, base, prototype) {
				proWidgetFactory(name, base, prototype);
				var namespace = name.split(".")[0];
				var name = name.split(".")[1];
				var ctr = $[namespace][name];

				var _proto = ctr._proto;
				$.each(_proto, function(prop, value) {
					if ($.isFunction(value)) {
						_proto[prop].nom = prop;
					}
				});

				return ctr;
			};
			$.widget.extend = proWidgetFactory.extend;
			$.widget.bridge = proWidgetFactory.bridge;

			// ------------------mask-----------------------
			$
					.extend(
							$.fn,
							{
								mask : function(msg, inDialog, maskDivClass) {
									this.unmask();
									// 参数
									var op = {
										opacity : 0.6,
										z : 10000
									};
									var original = $(document.body);
									var position = {
										top : 0,
										left : 0
									};
									if (this[0] && this[0] !== window.document) {
										original = this;
										position = original.position();
									}
									// 创建一个 Mask 层，追加到对象中
									var maskDiv = $('<div class="maskdivgen">&nbsp;</div>');
									maskDiv.appendTo(original);
									var maskWidth = original.outerWidth();
									if (!maskWidth) {
										maskWidth = original.width();
									}
									var maskHeight = original.outerHeight();
									if (!maskHeight) {
										maskHeight = original.height();
									}
									var cssObj = {
										position : 'absolute',
										top : position.top,
										left : position.left,
										'z-index' : op.z,
										width : maskWidth,
										height : maskHeight,
										'background-color' : '#EEEEEE',
										'border-color' : '#DDDDDD',
										opacity : 0
									};
									if (inDialog) {
										cssObj.top = -10;
										cssObj.left = -10;
										cssObj.width += 34;
										cssObj.height += 92;
									}
									maskDiv.css(cssObj);
									if (maskDivClass) {
										maskDiv.addClass(maskDivClass);
									}
									if (msg) {
										var msgDiv = $('<div style="position:absolute;border:#6593cf 0px solid; padding:2px;background:#ccca"><div style="line-height:24px;border:#a3bad9 1px solid;background:white;padding:2px 10px 2px 10px">'
												+ msg + '</div></div>');
										msgDiv.appendTo(maskDiv);
										var widthspace = (maskDiv.width() - msgDiv.width());
										var heightspace = (maskDiv.height() - msgDiv.height());
										msgDiv.css({
											cursor : 'wait',
											top : (heightspace / 2 - 2),
											left : (widthspace / 2 - 2)
										});
									}
									maskDiv.fadeIn('fast', function() {
										// 淡入淡出效果
										$(this).fadeTo('slow', op.opacity);
									});
									return maskDiv;
								},
								unmask : function() {
									var original = $(document.body);
									if (this[0] && this[0] !== window.document) {
										original = $(this[0]);
									}
									original.find("> div.maskdivgen").fadeOut('slow', 0, function() {
										$(this).remove();
									});
								}
							});

			// ------------------ underscore extension ----------------------
			_.mixin({
				clone : function(obj) {
					var objStr = JSON.stringify(obj);
					return JSON.parse(objStr);
				},

				bindAll : function(obj) {
					var funcs = Array.prototype.slice.call(arguments, 1);
					if (funcs.length === 0)
						throw new Error("bindAll must be passed function names");
					_.each(funcs, function(f) {
						if (_.isArray(f)) {
							_.each(f, function(sf) {
								obj[sf] = _.bind(obj[sf], obj);
							});
						} else {
							obj[f] = _.bind(obj[f], obj);
						}
					});
					return obj;
				}
			});

			_.string.lowerFirst = function(str) {
				return str.charAt(0).toLowerCase() + str.slice(1);
			};
		});