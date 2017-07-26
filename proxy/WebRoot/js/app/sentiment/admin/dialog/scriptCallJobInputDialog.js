define([ "text!./templates/scriptCallJobInputDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.scriptCallJobInputDialog", BaseDialog, {
		// default options
		options : {
			entity : 'scriptCallJobInput'
		},

		templateString : template,
		_initData : function(){
			this.options.item = _.clone(this.options.item);
			this.options.item.expectedCallTime = app.formatter.formatTimeStamp1(this.options.item.expectedCallTime);
			this._super();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.statusCombobox, 'CallStatus',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.handleStatusCombobox, 'HandleStatus',{
				value : (this.options.item && this.options.item.handleStatus !== undefined) ? this.options.item.handleStatus : undefined,
				isCheckAll : false,
				cached : true
			});
		}
	});
});