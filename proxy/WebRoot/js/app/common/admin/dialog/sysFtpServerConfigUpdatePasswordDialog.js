define([ "text!./templates/sysFtpServerConfigUpdatePasswordDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.sysFtpServerConfigUpdatePasswordDialog", BaseDialog, {
		// default options
		options : {
			service : "sysFtpServerConfig/save.do",
		},

		_create : function() {
			this._super();
			this.id.val(this.options.row.id);
			this.password.val(this.options.row.password);
		},
		
		_saveData : function(callback, errorback) {
			this.editForm.ajaxForm(app.buildServiceData(this.options.service, {
				beforeSubmit : _.bind(function() {
					return this.editForm.form('validate');
				}, this),
				context : this,
				success : function(data) {
					if (data) {
						if (data.success) {
							app.messager.info(data.msg);
							callback && callback(data);
						} else {
							app.messager.warn(data.msg);
						}
					}
				},
				error : errorback
			}));
			this.editForm.submit();
		},
		
		templateString : template,
		
		_initCombobox : function() {
		}
	});
});