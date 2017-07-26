define([ "text!./templates/taskInfoForm.html", "zf/base/BaseWidget", "text!app/proxy/task/config/taskInfoForm.json", "app/base/BaseForm"],
		function(template, BaseWidget, formConfig) {
			app.config.taskViewConfig = $.extend(app.config.taskViewConfig, eval("(" + formConfig + ")"));
			return $.widget("app.taskInfoForm", BaseWidget, {
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
					var taskInfo = this.options.taskInfo;
					console.dir(['this.options',this.options]);
					if (taskInfo) {
						this._renderFormData({
							"taskInfo" : taskInfo
						});
					}
				},
				_renderFormData : function(formData) {
					this.logger.debug();
					var taskdata = "taskInfo";
					this.options.action = 'view';
					var formConf = {
						viewType : {
							processcode : "taskInfoForm",
							taskcode : taskdata
						}
					};
					
					this.formData = formData;
					formConf.readOnly = (this.options.action === 'view');
					this.baseForm.BaseForm(formConf);
					this.baseForm.BaseForm("render", formData);
				}
			});
		});