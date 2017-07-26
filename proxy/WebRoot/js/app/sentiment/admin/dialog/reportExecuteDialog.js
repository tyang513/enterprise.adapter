define([ "text!./templates/reportExecuteDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.reportExecuteDialog", BaseDialog, {
		// default options
		options : {
			entity : 'reportExecute'
		},

		templateString : template,
		
		_initData : function() {
			
			this._super();
			this.codeSpan.text(this.options.item.code);
			this.nameSpan.text(this.options.item.name);
			if(this.options.item.emailType !== app.constants.REPORT_CONFIG_EMAIL_TYPE_JOB_TASK) {
				this.emailContentValidate.parent().hide();
				this.emailContentValidate.validatebox("disableValidation");
			} else {
				this.emailContentValidate.parent().show();
				this.emailContentValidate.validatebox("enableValidation");
			}
			if(this.options.item.isUseDynamicFileName !== app.constants.LOGIC_BASIS_STATUS_YES) {
				this.dynamicFileNameValidate.parent().hide();
				this.dynamicFileNameValidate.validatebox("disableValidation");
			}
			this._initParamsDiv(this.options.item.paramCount);
		},
		
		_initParamsDiv : function(paramCount) {
			for(var i = 1; i <= paramCount; i++) {
				var html = "<div class=\"fitem\" >" + 
								"<label>SQL参数" + i + "： </label>  " +
								"<input name=\"param" + i + "\" type=\"text\" maxlength=\"100\" class=\"easyui-validatebox\" data-options=\"required:true\" missingMessage=\"请填写SQL参数值\">" +
								"<span class=\"required\"> *</span>" +
						   "</div>";
				this.dynamicParamsDiv.append(html);
			}
			this._parse(this.dynamicParamsDiv);
		},
		
		_saveData : function(callback, errorback) {
			
			this.editForm.ajaxForm(app.buildServiceData("reportConfig/reportExecute.do", {
				beforeSubmit : _.bind(function() {
					return this.editForm.form('validate');
				}, this),
				context : this,
				success : function(data) {
					if (data) {
						if (data.success) {
							app.messager.info(data.msg);
						} else {
							app.messager.warn(data.msg);
						}
					}
					callback && callback(data);
				},
				error : errorback
			}));
			this.editForm.submit();
		}
		
	});
});