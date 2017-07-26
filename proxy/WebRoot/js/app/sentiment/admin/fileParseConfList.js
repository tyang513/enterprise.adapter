define([ "text!./templates/fileParseConfList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/fileParseConfSearchBar", "text!app/sentiment/admin/config/fileParseConfService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.fileParseConfList", BaseContentList, {
		// default options
		options : {
			entity : 'fileParseConf',
			entityName : '文件解析配置',
			entityId : 'id',
			openType : 'dialog',
			dialogConfig : {
				width : 435,
				height : 360,
				className : 'app/sentiment/admin/dialog/fileParseConfDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/fileParseConfForm'
			}
		},

		templateString : template,
		
		_handleViewFileParseColumnConf : function(data){
			this._publish("app/openTab", {
				"title" : "文件解析字段配置" ,
				"url" : "app/sentiment/admin/fileParseColumnConfList",
				"fileParseConfId" : data.row.id
			});
		},
		_handleMapImport : function(){
			this.logger.debug();
			var dlg = $("<div>").dialogExt({
				title : "地图导入",
				width : 400,
				height : 150,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/admin/dialog/stmtMapDialog',
				modal : true,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						if (data) {
							if (data.sucMessage) {
								dlg.dialogExt('destroy');
								app.messager.info(data.sucMessage);
								this.refresh();
							} else {
								app.messager.warn(data.failMessage);
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
		}
	});
});
