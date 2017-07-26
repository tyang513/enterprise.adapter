define([ "text!./templates/hadoopJobProgressDetailDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.hadoopJobProgressDetailDialog", BaseDialog, {
		// default options
		options : {
			entity : 'hadoopJobProgressDetail'
		},

		templateString : template,
		
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.executeTypeCombobox, 'ProgressExecuteType',{
				value : (this.options.item && this.options.item.executeType !== undefined) ? this.options.item.executeType : undefined,
				isCheckAll : false,
				cached : true,
				readonly : true
			});
			app.utils.initComboboxByDict(this.resultTypeCombobox, 'ProgressResultType',{
				value : (this.options.item && this.options.item.resultType !== undefined) ? this.options.item.resultType : undefined,
				isCheckAll : false,
				cached : true,
				readonly : true
			});
			app.utils.initComboboxByDict(this.reasonTypeCombobox, 'ProgressReasonType',{
				value : (this.options.item && this.options.item.reasonType !== undefined) ? this.options.item.reasonType : undefined,
				isCheckAll : false,
				cached : true,
				readonly : true
			});
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
			app.utils.initComboboxByDict(this.isInsertCombobox, 'ProgressIsInsert',{
				value : (this.options.item && this.options.item.isInsert !== undefined) ? this.options.item.isInsert : undefined,
				isCheckAll : false,
				cached : true,
				readonly : true
			});
		}
	});
});