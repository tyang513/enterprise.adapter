define([ "text!./templates/sysUserDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.sysUserDialog", BaseDialog, {
		// default options
		options : {
			entity : 'sysUser'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});