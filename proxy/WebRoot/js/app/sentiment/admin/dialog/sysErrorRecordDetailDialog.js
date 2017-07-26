define([ "text!./templates/sysErrorRecordDetailDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.sysErrorRecordDetailDialog", BaseDialog, {
		// default options
		options : {
			entity : 'sysErrorRecordDetail'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});