define([ "text!./templates/fileParseColumnConfList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/fileParseColumnConfSearchBar", "text!app/sentiment/admin/config/fileParseColumnConfService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.fileParseColumnConfList", BaseContentList, {
		// default options
		options : {
			entity : 'fileParseColumnConf',
			entityName : '文件解析列配置',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 435,
				height :300,
				className : 'app/sentiment/admin/dialog/fileParseColumnConfDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/fileParseColumnConfForm'
			}
		},

		templateString : template,
		_initData : function() {
			this.logger.debug();
			this.dataGrid.datagrid(app.buildServiceData("fileParseColumnConf/list.do", {
				queryParams : {'fileParseConfId' : this.options.fileParseConfId}, // 查询条件
				loadMsg : '数据加载中请稍后……',
			}));
			
			
		},
		_handleCreate:function(){
			this._openTemplateDialog(this.options.fileParseConfId, null, "add");
		},
		_handleEdit : function(data) {
			this.logger.debug();
			var data = this.dataGrid.datagrid('getSelected');
			if (data == null) {
				app.messager.warn("未选择记录");
				return ;
			}; 
			var rowdata = data;
			this._openTemplateDialog(this.options.fileParseConfId, rowdata, "edit");
		},
		_handleView : function(data) {
			this.logger.debug();
			var data = this.dataGrid.datagrid('getSelected');
			if (data == null) {
				app.messager.warn("未选择记录");
				return ;
			}; 
			var rowdata = data;
			this._openTemplateDialog(this.options.fileParseConfId, rowdata, "view");
		},
		_openTemplateDialog : function(fileParseConfId, rowdata, mode) {
			this.logger.debug();
			var title = "";
			var buttons = [{
				text : '确认',
				eventId : "ok",
				handler : _.bind(function(data) {
					if (data.success) {
						dlg.dialogExt('destroy');
						this.refresh();
					}
				}, this)
			}, {
				text : '取消',
				eventId : "cancel",
				handler : function() {
					dlg.dialogExt('destroy');
				}
			} ];
			if (mode == "add") {
				title = "新建文件解析列配置";
			} else if(mode == "edit"){
				title = "修改文件解析列配置";
			} else {
				title = "查看文件解析列配置";
				buttons = [ {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt('destroy');
					}
				} ];
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 435,
				height : 300,
				closed : false,
				global : true,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/admin/dialog/fileParseColumnConfDialog',
				modal : true,
				data : {
					fileParseColumnConf : rowdata,
					mode : mode,
					fileParseConfId : fileParseConfId
				},
				buttons : buttons
			});
		}
		
	});
});
