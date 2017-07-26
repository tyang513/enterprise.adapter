define([ "zf/base/BaseWidget", "text!app/config/form.json" ], function(BaseWidget, formConfig) {
	var app = window.app = window.app || {};
	app.config.taskViewConfig = eval("(" + formConfig + ")");

	return $.widget("app.BaseForm", BaseWidget, {
		// default options
		options : {
			readOnly : false,
			viewType : undefined,
			enableAuthorize : false
		},

		templateString : '<div class="easyui-layout" fit="true" data-attach-point="layoutContainer">'
				+ '<div data-options="region:\'north\',border:false" style="height: 120px;" data-attach-point="north"></div>'
				// + '<div data-options="region:\'east\',split:true,border:false" style="width: 210px;" data-attach-point="east"></div>'
				+ '<div data-options="region:\'center\',border:false" data-attach-point="center"></div>' + '</div>',

		_create : function() {
			this._super();

			this.classPaths = [];
			this.formTempls = {};
			this.tabFormTempls = {};
			this.moduleDijits = [];

			this.tabNames = [];
			this._loaded = false;
		},

		_handleDijitAction : function(event, data) {
			var attachPoint = data.actionDijit;
			var dijitName = this._getDijitName(attachPoint);
			if (attachPoint && this[attachPoint]) {
				// 传递给module dijit
				this[attachPoint][dijitName]('onFormAction', data);
			} else {
				// 传递给parent dijit
				this._trigger("action", event, data);
			}
		},

		_onFormAction : function(event, data) {
			this.logger.debug();
			var attachPoint = data.actionDijit;
			if (attachPoint === "form") {
				var eventId = data.eventId;
				if (eventId === "showModule") {
					var module = data.module;
					if (_.isString(module)) {
						module = this[module].data(this[module].attr("class"));
					}
					if (module.options.tab && this.tabContainer) {
						var _idx = module.options.tab - 1;
						if (data.show) {
							this.tabContainer.tabs("enableTab", _idx);
							this.tabContainer.tabs("select", _idx);
						} else {
							this.tabContainer.tabs("disableTab", _idx);
							this.tabContainer.tabs("select", 0);
						}

					} else {
						var panel = module.widget().parent().parent();
						if (panel) {
							if (panel.hasClass("panel")) {
								if (data.show) {
									panel.show();
								} else {
									panel.hide();
								}
								this.layoutContainer.layout('resize');
							}
						}
					}
				} else if (eventId === "dataChange") {
					this._formData = data.formData;
					_.each(this.moduleDijits, function(dijit) {
						this[dijit.attachPoint][dijit.shortName]('onFormAction', data);
					}, this);
				} else if (eventId === "getModuleData") {
					var attachPoint = data.module;
					var callback = data.callback;

					if (attachPoint && callback) {
						var dijitName = this._getDijitName(attachPoint);
						var data = this[attachPoint][dijitName]('data');
						callback(data);
					}
				} else if (eventId === "resize") {
					var northPanel = this.layoutContainer.layout('panel', 'north');
					northPanel.panel('resize', {
						height : data.size.height
					});
					this.layoutContainer.layout('resize');
				}
			} else {
				this._handleDijitAction(event, data);
			}
		},

		_getDijitName : function(attachPoint) {
			this.logger.debug();
			var dijitName = null;
			for (var i = 0; i < this.moduleDijits.length; i++) {
				if (this.moduleDijits[i].attachPoint === attachPoint) {
					dijitName = this.moduleDijits[i].shortName;
					break;
				}
			}
			return dijitName;
		},

		_buildFormTemplateString : function(formConfs) {
			if (formConfs.tabs) {
				this.tabNames = formConfs.tabs.split(',');
			}
			for ( var p in formConfs) {
				if (p !== 'tabs' && formConfs[p].length > 0) {
					var modules = formConfs[p];
					_.each(modules, function(module) {
						var tabIndex = module.options.tab === undefined ? 0 : module.options.tab;
						var tabId = 'tab' + tabIndex;
						this.tabFormTempls[tabId] = this.tabFormTempls[tabId] || {};
						this.tabFormTempls[tabId][p] = this.tabFormTempls[tabId][p] || [];
						var modulesTemp = this.tabFormTempls[tabId][p];

						var classPath = module.classPath;
						this.classPaths.push(classPath);
						var className = module.className;
						if (className === undefined) {
							var aa = classPath.split("/");
							className = "app-" + aa[aa.length - 1];
						}
						var shortName = className.split('-')[1];
						var title = module.options.title || "Panel";
						module.options.readOnly = this.options.readOnly || module.options.readOnly || false;
						module.options.formId = this.uuid;
						module.options.viewType = this.options.viewType;
						var attachPoint = module.options.attachPoint || shortName;

						var dataOptions = JSON.stringify(module.options);
						dataOptions = dataOptions.replace(/\{\"/g, "{").replace(/\":/g, ":").replace(/,\"/g, ",").replace(/\"/g, "'");
						dataOptions = dataOptions.substring(1, dataOptions.length - 1);

						if (p === "north") {
							modulesTemp.push("    <div class='" + className + "' data-options=\"" + dataOptions + "\" data-attach-point='" + attachPoint
									+ "' data-attach-event=\"" + shortName.toLowerCase() + "action:_onFormAction\"></div>");
						} else {
							modulesTemp.push("<div class='easyui-panel " + className + "-panel'style='margin-bottom:5px;' data-options=\"custom:true,border:false,title:'" + title
									+ "'\">");
							modulesTemp.push("    <div class='" + className + "' data-options=\"" + dataOptions + "\" data-attach-point='" + attachPoint
									+ "' data-attach-event=\"" + shortName.toLowerCase() + "action:_onFormAction\"></div>");
							modulesTemp.push("</div>");
						}

						this.moduleDijits.push({
							shortName : shortName,
							attachPoint : attachPoint,
							options : module.options
						});
					}, this);

					this.formTempls[p] = this.tabFormTempls['tab0'][p];
				}
			}
		},

		_initDom : function() {
			this.logger.debug();
			for ( var p in this.formTempls) {
				if (this.formTempls[p]) {
					var templateString = this.formTempls[p].join("");
					$(templateString).appendTo(this[p]);
					this._parse(this[p], {
						templateString : templateString
					});
				}
			}
		},

		_initData : function(formData) {
			var data = app.config.taskViewConfig;
			var taskDijits = [];
			console.dir([ "Base Form viewType", this.options.viewType ]);
			var viewType = this.options.viewType;
			if (viewType && viewType.processcode && viewType.taskcode) {
				taskDijits = data[viewType.processcode] && data[viewType.processcode][viewType.taskcode];
				if (!taskDijits) {
					this.logger.error("the task view config is error!, reason class not find.");
					return;
				}
			}

			if (this.options.enableAuthorize) {
				taskDijits = _.clone(app.security.authorizeForm(viewType));
			} else {
				taskDijits = _.clone(taskDijits);
			}
			console.dir([ "Base Form Config", taskDijits ]);

			this._buildFormTemplateString(taskDijits);

			// _.each([ "north", "east", "center" ], function(region) {
			// if (!this.formTempls[region]) {
			// this.layoutContainer.layout('remove', region);
			// }
			// }, this);
			// this.layoutContainer.layout('resize');

			require(this.classPaths, _.bind(function() {
				this._initDom();

				// render form
				this._loaded = true;
				this.render(formData);
			}, this));
		},

		_preLoadConfig : function(callback, formData) {
			app.getJSON("js/app/config/formview.json", _.bind(function(confs) {
				var deferreds = [];
				_.each(confs, function(url) {
					deferreds.push(app.getJSON(url));
				});
				$.when.apply(null, deferreds).done(_.bind(function() {
					var args = _.isString(arguments[1]) ? [ arguments ] : arguments;
					var data = {};
					var confData;
					_.each(args, function(arg) {
						confData = eval("(" + arg[0] + ")");
						data = $.extend(data, confData);
					});
					app.config.taskViewConfig = data;
					this._lazyload(formData);
				}, this));
			}, this));
		},

		_dynamicLoadConfig : function(callback, formData) {
			var url = "js/app/config/" + this.options.viewType.processcode + ".json";
			app.getJSON(url, _.bind(function(data) {
				app.config.taskViewConfig = $.extend(app.config.taskViewConfig, data);
				callback(formData);
			}, this));
		},

		_lazyload : function(formData) {
			if (!app.config.taskViewConfig) {
				this._preLoadConfig(_.bind(this._initData, this), formData);
			} else if (!app.config.taskViewConfig[this.options.viewType.processcode]) {
				this._dynamicLoadConfig(_.bind(this._initData, this), formData);
			} else {
				this._initData(formData);
			}
		},

		// 支持外部数据
		render : function(formData) {
			this.logger.debug();
			if (!this._loaded) {
				this._lazyload(formData);
			} else {
				var formType = this._getFormType(formData);
				formData.formType = formType;
				_.each(this.moduleDijits, function(dijit) {
					if (this[dijit.attachPoint]) {
						this[dijit.attachPoint][dijit.shortName]('render', formData);
					}
				}, this);
			}
		},

		getFormType : function() {
			return this.formType;
		},

		getSheetData : function() {
			var sheet;
			var sheetName;
			var sheetObjectName;
			var className;
			if (this.formType === app.constants.SHEET_SUBJECTUM) {
				sheet = this.formData.subjectum;
				sheetName = "结算主体配置";
				sheetObjectName = "subjectum";
				className = "app/settle/settleconfig/subjectumForm";
			} else if(this.formType === app.constants.SHEET_SETTLE_TEMPLATE) {
				sheet = this.formData.settleTemplate;
				sheetName = "结算模板配置";
				sheetObjectName = "settleTemplate";
				className = "app/settle/settleconfig/settleTemplateForm";
			} else if(this.formType === app.constants.SHEET_SETTLE_SHEET) {
				sheet = this.formData.settleSheet;
				sheetName = "结算单";
				sheetObjectName = "settleSheet";
				className = "app/settle/settle/settleSheetForm";
			}

			return {
				className : className,
				sheetName : sheetName,
				sheetObjectName : sheetObjectName,
				sheet : sheet
			};
		},

		_getFormType : function(formData) {
			this.logger.debug();
			this.formData = formData;
			console.dir([ "formData", formData ]);

			var formType = undefined;
			if (formData.taskInfo) {
				formType = app.constants.TASK_INFO;
				this.logger.debug("表单类型: 任务信息");
			}
			else if (formData.template){
				formType = app.constants.TEMPLATE;
				this.logger.debug("表单类型: 模板");
			}
			else {
				this.logger.error("表单类型: 未知！！！");
			}
			this.formType = formType;
			return formType;
		},

		validate : function() {
			var ret = true;
			_.each(this.moduleDijits, function(dijit) {
				var result = this[dijit.attachPoint][dijit.shortName]('validate');
				if (!result) {
					this.logger.warn("组件 [" + dijit.attachPoint + "] 验证不通过！！！");
					if (this.tabContainer) {
						this[dijit.attachPoint][dijit.shortName]('setSeletedTab', this[dijit.attachPoint]);
					}
				}
				ret = ret && result;
			}, this);
			return ret;
		},

		value : function() {
			var val = {};
			_.each(this.moduleDijits, function(dijit) {
				var v = this[dijit.attachPoint][dijit.shortName]('value');
				if (v != null) {
					val[dijit.attachPoint] = v;
				}
			}, this);
			return val;
		}
	});
});
