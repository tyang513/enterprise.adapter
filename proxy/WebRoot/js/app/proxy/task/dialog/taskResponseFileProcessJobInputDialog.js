define([ "text!./templates/taskResponseFileProcessJobInputDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.taskResponseFileProcessJobInputDialog", BaseDialog, {
		// default options
		options : {
			entity : 'taskResponseFileProcessJobInput'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});