define([ "text!./templates/scriptInfoList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/scriptInfoSearchBar", "text!app/sentiment/admin/config/scriptInfoService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.scriptInfoList", BaseContentList, {
		// default options
		options : {
			entity : 'scriptInfo',
			entityName : '脚本信息',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 520,
				height : 380,
				className : 'app/sentiment/admin/dialog/scriptInfoDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/scriptInfoForm'
			}
		},
		templateString : template,
		_handleRun : function(data) {
			this.logger.debug();
			var dlg = $("<div>").dialogExt({
				data : {
					scriptCode :data.row.scriptCode,
					desc:data.row.description,
					scriptName:data.row.scriptName
				},
				title : "运行脚本",
				width : 380,
				height : 400,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/admin/dialog/scriptInfoRunDialog',
				modal : true,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						if (data) {
							if (data.success) {
								dlg.dialogExt('destroy');
								app.messager.info(data.msg);
								this.refresh();
							} else {
								app.messager.warn(data.msg);
							}
						}
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt('destroy');
					}
				} ]
			});
		},
	});
});
