define([ "text!./templates/taskRequestFileProcessJobInputDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.taskRequestFileProcessJobInputDialog", BaseDialog, {
		// default options
		options : {
			entity : 'taskRequestFileProcessJobInput'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});