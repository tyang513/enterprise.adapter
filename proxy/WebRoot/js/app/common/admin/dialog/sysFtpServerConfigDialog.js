define([ "text!./templates/sysFtpServerConfigDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.sysFtpServerConfigDialog", BaseDialog, {
		// default options
		options : {
			entity : 'sysFtpServerConfig'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});