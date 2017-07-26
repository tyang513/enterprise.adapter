define([ "text!./templates/scriptStepRecordDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.scriptStepRecordDialog", BaseDialog, {
		// default options
		options : {
			entity : 'scriptStepRecord'
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
		}
	});
});