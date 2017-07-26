define([ "text!./templates/scriptInfoDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.scriptInfoDialog", BaseDialog, {
		// default options
		options : {
			entity : 'scriptInfo'
		},

		templateString : template
	});
});