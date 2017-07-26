define([ "text!./templates/exceptionDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.exceptionDialog", BaseDialog, {
		// default options
		options : {
			entity : 'exception'
		},

		templateString : template,
		
		_initCombobox : function() {
		},
		_create : function() {
			this._super();
			this._initData();
		},
		_initData : function() {
			var item = this.options.item.item;
			console.dir(['this.options',this.options]);
			this.exceptoinSpan.text(item.errorInfo);
		}
	});
});