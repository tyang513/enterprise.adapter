define([ "text!./templates/httpProxyDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.httpProxyDialog", BaseDialog, {
		// default options
		options : {
			entity : 'httpProxy'
		},

		templateString : template,
		
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.typeCombobox, 'HttpProxyType',{
				value : (this.options.item && this.options.item.type !== undefined) ? this.options.item.type : undefined,
				isCheckAll : false,
				cached : true
			});
		}
	});
});