define([ "text!./templates/taskMemoDialog.html", "zf/base/BaseWidget", "jqueryplugins/jquery.form" ], function(template, BaseWidget) {
	return $.widget("app.taskMemoDialog", BaseWidget, {
		// default options
		options : {
			ajaxLock : true
		},
		templateString : template,

		_create : function() {
			this._super();
			 this.remark.val(this.options.remarkValue);
		},
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				if (callback) {
					this.dialogCallback = callback;
					if (!this.remark.validatebox("isValid")){
						return ;
					}
					var remark = this.remark.val();
					callback(remark);
				}
			} else if (eventId === "cancel") {
				callback();
			}
		}
	});
});
