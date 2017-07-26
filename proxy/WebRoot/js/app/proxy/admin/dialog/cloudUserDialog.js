define([ "text!./templates/cloudUserDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.cloudUserDialog", BaseDialog, {
		// default options
		options : {
			entity : 'cloudUser'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});