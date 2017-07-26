define([ "zf/base/BaseWidget", "text!app/config/searchBar.json" ], function(BaseWidget, searchBarConfig) {
	var app = window.app = window.app || {};
	app.config = app.config || {};
	app.config.searchBar = app.config.searchBar || {};
	app.config.searchBar = eval("(" + searchBarConfig + ")");

	return $.widget("app.BaseSearchBar", BaseWidget, {
		// default options
		options : {
			enableFilter : true,
			layout : "asc", // asc|desc

			toolBar : ".datagrid-toolbar-tool",
			filterBar : ".datagrid-toolbar-filter",

			grid : null,
			unselectMsg : '请选择一行数据',

			searchBar : null,
			condition : undefined,
			buttons : [],

			autoRender : true,
			enableAuthorize : false,

			// callbacks
			action : null
		},

		templateString : '<div class="datagrid-toolbar-tool"></div><div class="datagrid-toolbar-filter"></div>',

		// the constructor
		_create : function() {
			this._super();

			if (this.options.autoRender) {
				this.render();
			}
		},
		render : function() {
			this.logger.debug();
			if (!app.config.searchBar) {
				this.__loadConfig(_.bind(this.__initData, this));
			} else {
				this.__initData();
			}
		},

		value : function() {
			this.logger.debug();
			return this.searchForm.serializeObject();
		},

		_onReset : function(event) {
			this.logger.debug();
			this.searchForm.find('input').val('');
		},

		__loadConfig : function(callback) {
			app.getJSON("js/app/config/searchBar.json", _.bind(function(data) {
				app.config.searchBar = data;
				callback();
			}, this));
		},

		__renderButtons : function(buttons) {
			if (buttons && buttons.length > 0) {
				var actionButtons = [];
				var filterButtons = [];

				_.each(buttons, function(button) {
					if (button.group === "filter") {
						filterButtons.push(button);
					} else {
						actionButtons.push(button);
					}
				});

				var _getButtonHtml = function(button) {
					var dataOptions = JSON.stringify(button);
					dataOptions = dataOptions.replace(/\{\"/g, "{").replace(/\":/g, ":").replace(/,\"/g, ",").replace(/\"/g, "'");
					dataOptions = dataOptions.substring(1, dataOptions.length - 1);
					var regionClass = "";
					if (button.group === "right") {
						regionClass = "toolbar-btn-right";
					}
					var html = "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton " + regionClass + "\" data-options=\"" + dataOptions + "\" "
							+ "data-attach-point=\"" + button.eventId + "Btn\"" + " data-attach-event=\"click:_onClickButton\">" + button.text + "</a>";
					return html;
				};

				if (actionButtons.length > 0) {
					var buttonHtml = [];
					_.each(actionButtons, function(button) {
						buttonHtml.push(_getButtonHtml(button));
					});
					$(buttonHtml.join('')).appendTo(this.toolBar);
					this._parse(this.toolBar);
					this.toolBar.find("a").addClass("l-btn-plain");
				}

				if (filterButtons.length > 0) {
					var buttonHtml = [];
					buttonHtml.push("<div class=\"button-panel\">");
					_.each(filterButtons, function(button) {
						buttonHtml.push(_getButtonHtml(button));
					});
					buttonHtml.push("</div>");
					$(buttonHtml.join('')).appendTo(this.filterBar);
					this._parse(this.filterBar);
					this.filterBar.find("a").addClass("l-btn-plain");
				}
			}
		},

		__initData : function() {
			if (this.options.searchBar) {
				this.options = $.extend(this.options, app.config.searchBar[this.options.searchBar]);
			}

			// cache elements
			this.toolBar = this.element.find(this.options.toolBar);
			this.filterBar = this.element.find(this.options.filterBar);
			if (this.options.enableFilter) {
				this.toggleButton = $("<a href=\"javascript:void(0)\"></a>").appendTo(this.toolBar);
				this.toggleButton.css("float", "right").linkbutton({
					plain : true,
					iconCls : 'icon-filter-close'
				});
				this._on(this.toggleButton, {
					"click" : "_toggleFilter"
				});
				this.toolBar.css("height", "26px");
			}
			this.filterBar.find(".criteria-panel").resize(_.bind(function() {
				this._resizeSearchPane();
			}, this)).keydown(_.bind(function(e){
				if(e.keyCode == 13){
					var data = {
						"action" : "search"
					};
					this._onAction(e, data);
				}
			},this));
			var buttons = this.options.buttons;
			var resourceId = "searchBar/" + this.options.searchBar;

			if (this.options.condition) {
				buttons = buttons[this.options.condition];
				resourceId += "/" + this.options.condition;
			}

			if (this.options.enableAuthorize) {
				app.security.authorize(resourceId, buttons, _.bind(this.__renderButtons, this));
			} else {
				this.__renderButtons(buttons);
			}

			if (this.options.grid && _.isString(this.options.grid)) {
				this.options.grid = $(this.options.grid);
			}
		},
		_resizeSearchPane : function() {
			var _searchHeight = this.filterBar.find(".criteria-panel").innerHeight();
			this.filterBar.css("height", _searchHeight + "px");
		},
		_onClickButton : function(event) {
			this.logger.debug();
			if($(event.currentTarget).hasClass('l-btn-disabled')){
				return;
			}
			
			var options = app.utils.parseOptions($(event.currentTarget));
			var action = options.eventId;

			if (action) {
				var selfHandle = options.selfHandle === undefined ? false : options.selfHandle;
				if (selfHandle) {
					var handlerName = "_on" + _.string.capitalize(action);
					if (this[handlerName] && _.isFunction(this[handlerName])) {
						this[handlerName](event);
					}
				} else {
					var data = {
						"action" : action
					};
					var checkSelect = options.checkSelect === undefined ? true : options.checkSelect;
					if (checkSelect) {
						var row = this.options.grid.datagrid('getSelected');
						if (!row) {
							app.messager.warn(this.options.unselectMsg);
							return;
						}
						data.row = row;
					}
					this._onAction(event, data);
				}
			}
		},

		onGridClickRow : function(rowIndex, rowData) {
		},

		_onAction : function(event, data) {
			this._trigger("action", event, data);
		},

		_toggleFilter : function(event) {
			this.toggleButton.find(".l-btn-empty").toggleClass("icon-filter-close");
			this.toggleButton.find(".l-btn-empty").toggleClass("icon-filter-open");
			this.filterBar.toggle();
			if (this.options.grid) {
				this.options.grid.resize();
			}
		},

		// _setOptions is called with a hash of all options that are changing
		// always refresh when changing options
		_setOptions : function(options) {
			// _super and _superApply handle keeping the right this-context
			this._superApply(arguments);
		},

		// _setOption is called for each individual option that is changing
		_setOption : function(key, value) {
			if (key === "layout") {
				this.options.layout = value;
				if (value === "asc") {
					this.toolBar.after(this.filterBar);
				} else if (value === "desc") {
					this.toolBar.before(this.filterBar);
				}
			}
			this._super(key, value);
		},

		_destroy : function() {
			this._super();

			delete this.toolBar;
			delete this.filterBar;
		}
	});
});
