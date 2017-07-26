define([ "text!./templates/scriptRunRecordDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.scriptRunRecordDialog", BaseDialog, {
		// default options
		options : {
			entity : 'scriptRunRecord'
		},

		templateString : template,
		_initData : function(){
			this.options.item = _.clone(this.options.item);
			this.options.item.beginTime = app.formatter.formatTimeStamp1(this.options.item.beginTime);
			this.options.item.endTime = app.formatter.formatTimeStamp1(this.options.item.endTime);
			this._super();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.typeCombobox, 'ScriptInfoType',{
				value : (this.options.item && this.options.item.type !== undefined) ? this.options.item.type : undefined,
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.targetDateCombobox, 'ScriptInfoType',{
				value : (this.options.item && this.options.item.targetDate !== undefined) ? this.options.item.targetDate : undefined,
				isCheckAll : false,
				cached : true
			});
		}
	});
});