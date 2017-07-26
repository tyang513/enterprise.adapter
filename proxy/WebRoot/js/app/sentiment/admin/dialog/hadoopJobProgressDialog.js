define([ "text!./templates/hadoopJobProgressDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.hadoopJobProgressDialog", BaseDialog, {
		// default options
		options : {
			entity : 'hadoopJobProgress'
		},

		templateString : template,
		
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.taskNameCombobox, 'ProgressTaskName',{
				value : (this.options.item && this.options.item.taskName !== undefined) ? this.options.item.taskName : undefined,
				isCheckAll : false,
				cached : true,
				readonly : true
			});
			app.utils.initComboboxByDict(this.jobTypeCombobox, 'ProgressJobType',{
				value : (this.options.item && this.options.item.jobType !== undefined) ? this.options.item.jobType : undefined,
				isCheckAll : false,
				cached : true,
				readonly : true
			});
		}
	});
});