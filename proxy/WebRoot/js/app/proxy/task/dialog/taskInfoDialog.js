define([ "text!./templates/taskInfoDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.taskInfoDialog", BaseDialog, {
		// default options
		options : {
			entity : 'taskInfo'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});