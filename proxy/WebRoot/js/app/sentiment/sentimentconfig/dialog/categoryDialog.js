define([ "text!./templates/categoryDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.categoryDialog", BaseDialog, {
		// default options
		options : {
			entity : 'category'
		},

		templateString : template,
		
		_initCombobox : function() {
		},
		
		_create: function(){
			this._super();
			this._initData();
		},
		_initData: function() {
			this._super();
			if (this.options.action === app.constants.ACT_CREATE) {
				this.editForm.form("load", this.options.item);
			} else if(this.options.action === app.constants.ACT_EDIT) {
				this.codeInput.attr("readonly","readonly");
				this.nameInput.attr("readonly","readonly");
			}
		}
	});
});