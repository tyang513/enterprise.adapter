define([ "text!./templates/fileParseConfDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.fileParseConfDialog", BaseDialog, {
		// default options
		options : {
			entity : 'fileParseConf'
		},

		templateString : template,
		
		_initData : function() {
			 if(this.options.action === app.constants.ACT_EDIT) {
				 this.editForm.form("load", this.options.item);
			}
		}
	});
});