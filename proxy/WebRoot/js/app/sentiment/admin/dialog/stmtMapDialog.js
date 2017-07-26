define([ "text!./templates/stmtMapDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.stmtMapDialog", BaseDialog, {
		// default options
		options : {
			entity : 'stmtMap'
		},

		templateString : template,
		
		_initData : function() {
			this._super();
		},
		
		_saveData : function(callback, errorback) {
			//判断文件是否被移除
			var fileSize=app.utils.getFileSize(this.dataFile);
			if(fileSize==-1) {
				app.messager.warn("待导入的文件不存在!");
				return;
			} else if(fileSize==0) {
				app.messager.warn("待导入的文件内容为空!");
				return;
			}
			var re='';
			var serviceUrl = '';
			serviceUrl = 'stmtMap/imporMap.do';
			re=new RegExp("(.csv)$");
			if(!re.test(this.dataFile.val())) {
				app.messager.warn("待导入的文件只能为csv文件!");
				return;
			}
			
			this.editForm.ajaxForm(app.buildServiceData(serviceUrl, {
				beforeSubmit : _.bind(function() {
					return this.editForm.form('validate');
				}, this),
				context : this,
				global : true,
				success : function(data) {
					if (data) {
						if (data.sucMessage) {
							app.messager.info(data.sucMessage);
						} else {
							app.messager.warn(data.failMessage);
						}
					}
					callback && callback(data);
				},
				error : errorback
			}));
			this.editForm.submit();
		}
		
	});
});