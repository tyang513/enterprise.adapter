define([ "text!./templates/serverInfoDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.serverInfoDialog", BaseDialog, {
		// default options
		options : {
			entity : 'serverInfo'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});