define([ "text!./templates/taskLogDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.taskLogDialog", BaseDialog, {
		// default options
		options : {
			entity : 'taskLog'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});