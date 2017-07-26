define([ "app/base/BaseForm" ], function(BaseForm) {
	return $.widget("app.BaseTabForm", BaseForm, {
		// default options
		options : {
			delayRender : true
		},

		_create : function() {
			this._super();
			this._tabsData = {};
			this._domInited = false;
			this._dijitActions = {};
		},

		_initDom : function() {
			this._super();

			this._createTabs();
			for ( var p in this.tabFormTempls) {
				if (p !== 'tab0' && this.tabFormTempls[p]) {
					var tabConf = _.clone(this.tabFormTempls[p]);
					tabConf.id = p;
					this._createTab(tabConf);
				}
			}

			if (this.options.delayRender) {
				this.tabContainer.tabs({
					onSelect : _.bind(this._onSelect, this)
				});
			}
			this._domInited = true;
		},

		_createTabs : function() {
			this.logger.debug();
			var tabsHtml = '<div class="easyui-tabs" data-options="fit:true,border:false,plain:true" data-attach-point="tabContainer"></div>';
			$(tabsHtml).appendTo(this.center);
			this._parse(this.center);
		},

		_getTabTitle : function(tabId) {
			var title = tabId;
			var index = parseInt(tabId.substring(3)) - 1;
			if (index < this.tabNames.length) {
				title = this.tabNames[index];
			}
			return title;
		},

		_createTab : function(tabConf) {
			var tempHtml = [];
			tempHtml.push('<div class="easyui-layout" fit="true" data-attach-point="' + tabConf.id + '_layoutContainer">');
			if (tabConf.east && tabConf.east.length > 0) {
				tempHtml.push('<div data-options="region:\'east\',split:true,border:false" style="width: 210px;" data-attach-point="' + tabConf.id + '_east">'
						+ tabConf.east.join('') + '</div>');
			}
			if (tabConf.center && tabConf.center.length > 0) {
				tempHtml.push('<div data-options="region:\'center\',border:false" data-attach-point="' + tabConf.id + '_center">' + tabConf.center.join('')
						+ '</div>');
			}
			tempHtml.push('</div>');

			var options = {
				"title" : this._getTabTitle(tabConf.id)
			};
			var tab = this.tabContainer.tabs("add", options).tabs('getSelected');

			var templateString = tempHtml.join("");
			this._tabsData[tabConf.id] = {
				templateString : templateString,
				loaded : false
			};
			$(templateString).appendTo(tab);

			if (!this.options.delayRender) {
				this._parse(tab, {
					templateString : templateString
				});
			}
		},

		render : function(formData) {
			this._super(formData);
			var formType = this._getFormType(formData);
			formData.formType = formType;
			this._formData = formData;
			if (this._loaded) {
				// 加载第一个Tab
				this._renderTab(0);
			}
		},

		validate : function() {
			var ret = true;
			for (var i = 0; i < this.moduleDijits.length; i++) {
				var dijit = this.moduleDijits[i];
				if (this[dijit.attachPoint]) {
					var result = this[dijit.attachPoint][dijit.shortName]('validate');
					if (!result) {
						this.logger.warn("组件 [" + dijit.attachPoint + "] 验证不通过！！！");
						if (this.tabContainer) {
							this[dijit.attachPoint][dijit.shortName]('setSeletedTab', this[dijit.attachPoint]);
						}
						ret = false;
						break;
					}
				} else {
					if (dijit.options.notNull) {
						this.logger.warn("Tab [" + dijit.options.title + "] 需要确认数据！！！");
						this.tabContainer.tabs('select', (dijit.options.tab - 1));
						app.messager.warn("请确认" + dijit.options.title + "的数据！");
						ret = false;
						break;
					}					
				}
			}
			return ret;
		},
		
		value : function() {
			var val = {};
			_.each(this.moduleDijits, function(dijit) {
				if(this[dijit.attachPoint]){
					var v = this[dijit.attachPoint][dijit.shortName]('value');
					if (v != null) {
						val[dijit.attachPoint] = v;
					}
				}				
			}, this);
			return val;
		},

		_handleDijitAction : function(event, data) {
			var attachPoint = data.actionDijit;
			if (attachPoint) {
				if (!this[attachPoint]) {
					for (var i = 0; i < this.moduleDijits.length; i++) {
						if (this.moduleDijits[i].attachPoint === attachPoint) {
							this._dijitActions[attachPoint] = this._dijitActions[attachPoint] || {};
							this._dijitActions[attachPoint].actions = this._dijitActions[attachPoint].actions || [];
							this._dijitActions[attachPoint].actions.push({
								event : event,
								data : data
							});
							this.tabContainer.tabs('select', (this.moduleDijits[i].options.tab - 1));
							break;
						}
					}
				} else {
					var dijitName = this._getDijitName(attachPoint);
					var _idx = this[attachPoint][dijitName]('option', 'tab') - 1;
					var tabHeader = this.tabContainer.find(".tabs>li")[_idx];
					if (!$(tabHeader).hasClass('tabs-disabled')) {
						this.tabContainer.tabs("select", _idx);
					}
					// 传递给module dijit
					this[attachPoint][dijitName]('onFormAction', data);
				}
			} else {
				this._trigger("action", event, data);
			}
		},

		_validateModule : function(dijit) {
			var ret = true;
			return ret;
		},
		
		_triggerOnSelectEvent : function(index){
			this.logger.debug();
			var tabId = "tab" + (index + 1);
			if(this._tabsData[tabId].loaded){
				var templateString = this._tabsData[tabId].templateString;
				var tps = _.clone(templateString);
				var clss = [];
				tps.replace(/app-\w+/g, function(match, key) {
					clss.push(match);
					return "";
				});
				clss = _.uniq(clss);
				var dijits = [];
				for (var i = 0; i < clss.length; i++) {
					dijits.push(clss[i].replace("app-", ""));
				}
				_.each(dijits, function(dijit) {
					if (this[dijit]) {
						this[dijit][dijit]('onFormAction', {
							eventId : 'onSelect',
							"formData" : this._formData
						});
					}
				}, this);
			}
		},	

		_renderTab : function(index) {
			this.logger.debug();
			var tabId = "tab" + (index + 1);	
			if(this._tabsData[tabId].loaded){
				this._triggerOnSelectEvent(index);
			}
			if (this.options.delayRender && this._tabsData[tabId] && !this._tabsData[tabId].loaded) {
				var tab = this.tabContainer.tabs('getTab', index);
				var templateString = this._tabsData[tabId].templateString;
				this._parse(tab, {
					templateString : templateString
				});

				var tps = _.clone(templateString);
				var clss = [];
				tps.replace(/app-\w+/g, function(match, key) {
					clss.push(match);
					return "";
				});
				clss = _.uniq(clss);
				var dijits = [];
				for (var i = 0; i < clss.length; i++) {
					dijits.push(clss[i].replace("app-", ""));
				}
				_.each(dijits, function(dijit) {
					if (this[dijit]) {
						this[dijit][dijit]('render', this._formData);
						// 处理dijit actions
						if (this._dijitActions[dijit] && this._dijitActions[dijit].actions) {
							var actions = this._dijitActions[dijit].actions;
							for (var i = 0; i < actions.length; i++) {
								this[dijit][dijit]('onFormAction', actions[i].data);
							}
							
							this[dijit][dijit]('onFormAction', {
								eventId : 'onSelect'							
							});
							
							this._dijitActions[dijit] = null;
						}
					}
				}, this);
				this._tabsData[tabId].loaded = true;
			}
		},

		_onSelect : function(title, index) {
			this.logger.debug();
			if (!this._domInited) {
				return;
			}
			this._renderTab(index);
		}
	});
});