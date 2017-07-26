define([ "text!./templates/importFileJobInputDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.importFileJobInputDialog", BaseDialog, {
		// default options
		options : {
			entity : 'importFileJobInput'
		},

		templateString : template,
		
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.statusCombobox, 'CallStatus',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
				isCheckAll : false,
				cached : true
			});
		}
	});
});