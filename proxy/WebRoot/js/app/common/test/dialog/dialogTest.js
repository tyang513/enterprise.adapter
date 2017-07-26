define([ "text!./templates/dialogTest.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.dialogTest", BaseWidget, {
		// default options
		options : {
			
		},

		templateString : template,

		_create : function() {
			this._super();
			this.sheetId = this.options.sheetId;
			this.sheetType = this.options.sheetType;
		},
		_printParam : function(){
			app.messager.alert('信息', "sheetId:"+this.sheetId + "<br> sheetType:"+this.sheetType, 'info');
		},
		_getData : function(){
			return "data对象";
		},
		_isValid : function(){
			return true;
		},
		execute : function(eventId, callback) {
			var data = this._getData();
			var isValid = this._isValid();
			if (eventId === "ok" && isValid) {
				callback && callback(data);
			} else if (eventId === "cancel") {
				callback();
			}
		}
	});
});
