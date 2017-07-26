define([ "text!./templates/templateForm.html", "app/base/BaseTabForm", "text!app/sentiment/sentimentconfig/config/templateForm.json"],
		function(template, BaseWidget, formConfig) {
			app.config.taskViewConfig = $.extend(app.config.taskViewConfig, eval("(" + formConfig + ")"));
			return $.widget("app.templateForm", BaseWidget, {
				// default options
				options : {
					locked : true,
					ajaxLock : true
				},

				templateString : template,

				// the constructor
				_create : function() {
					this._super();
					this._initData();
				},

				_initData : function() {
					this.logger.debug();
					var template = this.options.template;
					if (template) {
						this._renderFormData({
							"template" : template,
							//"item" : this.options.subjectum
						});
					}
				},

				_onFormAction : function(event, data) {
					this.logger.debug();
					var eventId = data.eventId;
				/*	if (eventId === "save") {

					} else if (eventId === "submit") {
						if (this.baseForm.BaseTabForm('validate')) {
							this._openApplyDialog();
						}
					}*/  if (eventId === "cancel") {
						app.utils.closeTab(this, true);
					} 
				},

				_renderFormData : function(formData) {
					this.logger.debug();
					var taskCode = "viewData";
					this.options.action = 'view';
					var formConf = {
						viewType : {
							processcode : "templateForm",
							taskcode : taskCode
						}
					};
					// 只有流程上查看申请单才会设置action为view
					//formConf.readOnly = (this.options.action === 'view');
					this.metadataEntity = formData;
					this.baseForm.BaseTabForm(formConf);
					this.baseForm.BaseTabForm("render", formData);
				}
			});
		});