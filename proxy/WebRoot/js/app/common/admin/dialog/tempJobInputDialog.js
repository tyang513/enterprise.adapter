define([ "text!./templates/tempJobInputDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.tempJobInputDialog", BaseDialog, {
		// default options
		options : {
			entity : 'tempJobInput'
		},

		templateString : template,
		
		_initData : function() {
			this._super();
			this._initCombobox();
			this.umId.val(app.userInfo.user.loginName);
			this.umName.val(app.userInfo.user.name);
		},
		
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.statusCombobox, 'CallStatus',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
				isCheckAll : false,
				cached : true
			});
		}
	});
});