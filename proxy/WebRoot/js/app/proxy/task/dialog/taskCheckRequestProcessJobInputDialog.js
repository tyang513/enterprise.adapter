define([ "text!./templates/taskCheckRequestProcessJobInputDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.taskCheckRequestProcessJobInputDialog", BaseDialog, {
		// default options
		options : {
			entity : 'taskCheckRequestProcessJobInput'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});