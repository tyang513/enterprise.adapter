define([ "text!./templates/stmtUserAgentDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.stmtUserAgentDialog", BaseDialog, {
		// default options
		options : {
			entity : 'stmtUserAgent'
		},
		_create : function() {
			this._super();			
			this._initData();
		},
		templateString : template,
		
		_initCombobox : function() {
		},
		_initData : function() {
			this._super();
			if (this.options.mode === app.constants.ACT_CREATE ) {
				this.addUserAgent.show();
				this.viewUserAgent.hide();
			}else if(this.options.mode === app.constants.ACT_EDIT){
				this.addUserAgent.show();
				this.viewUserAgent.hide();
				this.editForm.form("load", this.options.item);
			}else if(this.options.mode === app.constants.ACT_VIEW) {
				this.addUserAgent.hide();
				this.viewUserAgent.show();
				this.name.val(this.options.item.name);
				this.description.val(this.options.item.description);
			}
			
		}
	});
});