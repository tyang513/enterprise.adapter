define([ "text!./templates/sysFtpClientConfigDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.sysFtpClientConfigDialog", BaseDialog, {
		// default options
		options : {
			entity : 'sysFtpClientConfig'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});