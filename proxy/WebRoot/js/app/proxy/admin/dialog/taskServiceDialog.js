define([ "text!./templates/taskServiceDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.taskServiceDialog", BaseDialog, {
		// default options
		options : {
			entity : 'taskService'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});