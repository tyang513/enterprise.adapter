define([ "text!./templates/partnerDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.partnerDialog", BaseDialog, {
		// default options
		options : {
			entity : 'partner'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});