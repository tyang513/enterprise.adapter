define([ "app/base/BaseFormModule" ], function(BaseFormModule) {
	return $.widget("app.BaseSheetSummary", BaseFormModule, {
		// default options
		options : {
			toolbarClass : '.toolbar',
			logoClass : '.sheetLogo',

			enableReadOnly : true,
			readOnlyButtons : [],
			subEvents : {
				"app/summary/buttons" : "_handleControlButtons"
			}

		},

		templateString : '',

		_create : function() {
			this._super();
			this.actionMsg = null;
		},

		render : function(formData) {
			this._super(formData);
			this._renderLogo();
			this._renderButtons();
		},

		_renderLogo : function() {
			var viewType = this.options.viewType;
			var iconClass = viewType.processcode + viewType.taskcode + "Icon";
			console.dir([ "iconClass", iconClass ]);
			var sheetLogo = this.element.find(this.options.logoClass);
			sheetLogo.addClass(iconClass);
		},
		// ---------------------event handler----------------------
		_handleControlButtons : function(event) {
			this.logger.debug();
			var formId = event.data.formId;
			if (formId === this.options.formId) {
//				console.dir([ "event", event ]);
				var buttons = event.data.buttons;
				if (buttons && buttons.length > 0) {
					_.each(buttons, function(button) {
						if (button.text !== undefined && this[button.eventId + "Button"]) {
							this[button.eventId + "Button"].linkbutton({
								'text' : button.text
							});
						}
						if (button.hidden !== undefined) {
							button.hidden ? this._hideButton(button.eventId) : this._showButton(button.eventId);
						}
					}, this);
				}
			}
		},

		_onClickButton : function(event) {
			this.logger.debug();
			var options = app.utils.parseOptions($(event.currentTarget));
			this._trigger("action", event, options);
		},

		_onClickMenu : function(event) {
			this.logger.debug();
			var options = app.utils.parseOptions($(event));
			delete options.widgetId;
			delete options.handler;
			console.dir([ "event", event, options ]);
			this._trigger("action", event, options);
		},

		_showButton : function(eventId) {
			var button = eventId + "Button";
			if (this[button]) {
				this[button].show();
			}
		},
		_showButtons : function(eventIds) {
			var eventIds = eventIds.split(",")
			_.each(eventIds, function(eventId) {
				this._showButton(eventId);
			}, this)
		},

		_hideButton : function(eventId) {
			var button = eventId + "Button";
			if (this[button]) {
				this[button].hide();
			}
		},
		_hideButtons : function(eventIds) {
			var eventIds = eventIds.split(",")
			_.each(eventIds, function(eventId) {
				this._hideButton(eventId);
			}, this)
		},

		__getMenuItem : function(eventId, menu) {
			var item = null;
			if (menu) {
				for ( var i = 0; i < this.moreActionMenus.length; i++) {
					if (this.moreActionMenus[i].eventId === eventId) {
						item = menu.menu('findItem', this.moreActionMenus[i].text);
						break;
					}
				}
			}
			return item;
		},

		_enableMenu : function(eventId) {
			var options = this.moreActionButton.menubutton('options');
			var menu = $(options.menu);
			var item = this.__getMenuItem(eventId, menu);
			if (item) {
				menu.menu('enableItem', item.target);
			}
		},
		_enableMenus : function(eventIds) {
			var eventIds = eventIds.split(",")
			_.each(eventIds, function(eventId) {
				this._enableMenu(eventId);
			}, this)
		},

		_disableMenu : function(eventId) {
			var options = this.moreActionButton.menubutton('options');
			var menu = $(options.menu);
			var item = this.__getMenuItem(eventId, menu);
			if (item) {
				menu.menu('disableItem', item.target);
			}			
		},
		_disableMenus : function(eventIds) {
			var eventIds = eventIds.split(",")
			_.each(eventIds, function(eventId) {
				this._disableMenu(eventId);
			}, this)
		},

		_renderButtons : function() {
			this.logger.debug();

			var _self = this;

			var _getButtonHtml = function(button, uuid) {
				var displayStyle = button.hidden ? "none" : "";

				var dataOptions = JSON.stringify(button);
				dataOptions = dataOptions.replace(/\{\"/g, "{").replace(/\":/g, ":").replace(/,\"/g, ",").replace(/\"/g, "'");
				dataOptions = dataOptions.substring(1, dataOptions.length - 1);

				var html = "<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" style=\"display:" + displayStyle + "\" data-options=\"" + dataOptions
						+ "\" data-attach-point=\"" + button.eventId + "Button\" data-attach-event=\"click:_onClickButton\">" + button.text + "</a>";
				return html;

			};

			var _getMenuButtonHtml = function(button, uuid) {
				var displayStyle = button.hidden ? "none" : "";
				var menuHtml = [];
				menuHtml.push("<a href=\"javascript:void(0)\" class=\"easyui-menubutton\" style=\"display:" + displayStyle + "\" data-options=\"menu:'#" + uuid
						+ "'\" data-attach-point=\"" + button.eventId + "Button\">" + button.text + "</a>");
				menuHtml.push("<div id=\"" + uuid + "\" style=\"width:150px;\">");

				_.each(button.menus, function(menu) {
					var dataOptions = JSON.stringify(menu);
					dataOptions = dataOptions.replace(/\{\"/g, "{").replace(/\":/g, ":").replace(/,\"/g, ",").replace(/\"/g, "'");
					dataOptions = dataOptions.substring(1, dataOptions.length - 1);

//					console.dir(['menu', menu]);
					menuHtml.push("<div data-options=\"" + dataOptions + "\" onClick=_onClickMenu();>" + menu.text + "</div>");
				});
				menuHtml.push("</div>");

				return app.utils.bind(menuHtml.join(""), _self);
			};

			console.dir([ 'this.options', this.options ]);
			
			var buttons = (this.options.enableReadOnly && this.options.readOnly) ? this.options.readOnlyButtons : this.options.buttons;
			
			var buttonsMap = {};
			
			if (buttons && buttons.length > 0) {
				var toolbar = this.element.find(this.options.toolbarClass);

				_.each(buttons, _.bind(function(button) {
					if(!buttonsMap[button.group]){
						buttonsMap[button.group] = [];
					}

					buttonsMap[button.group].push(button);
				}, this));
				
				var buttonsDivHtml = [];
				for(var p in buttonsMap){
					var buttonsHtml = [];
					buttonsHtml.push("<div class='"+p+"'>");
					var group;
					var index = 0;
					_.each(buttonsMap[p], _.bind(function(button) {
						index++;
											
						if (group !== button.group) {
							if (group !== undefined) {
								buttonsHtml.push("<span class=\"group-seperator\"></span>");
							}
							group = button.group;
						}

						var uuid = this.widgetId + "_" + index;
						if (button.type === "menubutton" && button.eventId === "moreAction") {
							this.moreActionMenus = _.clone(button.menus);
						}
						var html = button.type === "menubutton" ? _getMenuButtonHtml(button, uuid) : _getButtonHtml(button, uuid);
						buttonsHtml.push(html);
					}, this));
					buttonsHtml.push("</div>");
					buttonsDivHtml.push(buttonsHtml.join(''));
				}

				$(buttonsDivHtml.join('')).appendTo(toolbar);
				this._parse(toolbar);
			}
		},
		__initActionMsg : function() {
			this.logger.debug();
			var toolbar = this.element.find(this.options.toolbarClass);
			if (toolbar) {
				this.actionMsg = $("<div class=\"actionMsg\"></div>");
				this.actionMsg.insertBefore(toolbar);
			}
		},
		_appendActionMsg : function(msg) {
			this.logger.debug();
			if (!this.actionMsg) {
				this.__initActionMsg();
			}
			var msgHtml = "<div>" + msg + "</div>";
			$(msgHtml).appendTo(this.actionMsg);
			this._trigger("action", null, {
				actionDijit : "form",
				eventId : "resize",
				size : {
					width : this.element.width(),
					height : this.element.height()
				}
			});
		}
	});
});
