define([ "text!./templates/scriptCallJobInputList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/scriptCallJobInputSearchBar", "text!app/sentiment/admin/config/scriptCallJobInputService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.scriptCallJobInputList", BaseContentList, {
		// default options
		options : {
			entity : 'scriptCallJobInput',
			entityName : '任务输入',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 390,
				className : 'app/sentiment/admin/dialog/scriptCallJobInputDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/scriptCallJobInputForm'
			}
		},
		
	
		_handleOpenRetryDialog : function(data){
			this.logger.debug();
			if(data.row.status == "1" && data.row.handleStatus =="1"){
				app.messager.info('该任务正在处理中，请重新选择！');
			}else if(data.row.status == "0" && data.row.handleStatus =="0"){
				app.messager.info('该任务已是未处理状态，请重新选择！');
			}else{
				var dlg = $("<div>").dialogExt({
					data : {
						selectId : data.row.id,
						type : "scriptCallJobInput",
						},
					title : "重试调用脚本",
					width : 360,
					height : 130,
					closed : false,
					autoClose : false,
					cache : false,
					className : 'app/sentiment/admin/dialog/ScriptCallJobInputRetryStatusDialog',
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
				
			}
		},
		
		_handleOpenBSWCDialog:function(data){
			this.logger.debug();
			if(data.row.status == "1" && data.row.handleStatus =="1"){
				app.messager.info('该任务正在处理中，请重新选择！');
			}else if(data.row.status == "2" && data.row.handleStatus =="2"){
				app.messager.info('该任务已是完成状态，请重新选择！');
			}else{
				var dlg = $("<div>").dialogExt({
					data : {
						selectId : data.row.id,
						type : "scriptCallJobInput"
					},
					title : "标识完成",
					width : 360,
					height : 130,
					closed : false,
					autoClose : false,
					cache : false,
					className : 'app/sentiment/admin/dialog/scriptCallJobInputBSWCStatusDialog',
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
			}
		},
		
		_handleOpenBSYCDialog : function(data){
			this.logger.debug();
			if(data.row.status == "1" && data.row.handleStatus =="1"){
				app.messager.info('该任务正在处理中，请重新选择！');
			}else if(data.row.status == "-1" && data.row.handleStatus =="-1"){
				app.messager.info('该任务已是异常状态，请重新选择！');
			}else{
				var dlg = $("<div>").dialogExt({
					data : {
						selectId : data.row.id,
						type : "scriptCallJobInput"
					},
					title : "标识异常",
					width : 360,
					height : 130,
					closed : false,
					autoClose : false,
					cache : false,
					className : 'app/sentiment/admin/dialog/scriptCallJobInputBSYCStatusDialog',
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
			}
		},

		templateString : template
	});
});
