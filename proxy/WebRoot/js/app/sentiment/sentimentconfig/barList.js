define([ "text!./templates/barList.html", "app/base/BaseContentList", "app/sentiment/sentimentconfig/searchbar/templateSearchBar","text!app/sentiment/sentimentconfig/config/templateService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.barList", BaseContentList, {
		// default options
		options : {
			entity : 'template',
			entityName : '模板',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 460,
				height : 290,
				className : 'app/sentiment/sentimentconfig/dialog/templateDialog'
			},
			formConfig : {
				url : 'app/sentiment/sentimentconfig/templateForm'
			}
			
		},

		templateString : template,
		
		_handleCreate:function(){
			this._openTemplateDialog({
				action : app.constants.ACT_CREATE,
				/*item : {
					subjectumId : this.subjectum.id,
					type : this.subjectum.type
				}*/
			});
		},
		_editTemplate:function(){

			var gdata = this.dataGrid.datagrid('getSelected');
			if (gdata) {
				this._openTemplateDialog({
					action : app.constants.ACT_EDIT,
					item : gdata
				});
			} else {
				app.messager.warn('未选模板！');
				return;
			}
		},
		_handleView:function(){

			var gdata = this.dataGrid.datagrid('getSelected');
			if (gdata) {
				this._showTemplate({
					action :  app.constants.ACT_VIEW ,
					item : gdata
					//subjectum : this.subjectum
				});
			} else {
				app.messager.warn('未选模板！');
				return;
			}
		},
		_handleUpgradeTemplate:function(){
			 var data = this.dataGrid.datagrid('getSelected');
			//如果最新版本未启用或禁用，不允许升级
				if(data.status!="1"){
					app.messager.error('模版未启用，不能升级模版');
					return;
				}
				app.messager.confirm("提示", "确认要升级模板吗？", _.bind(function(b) {
					if (b) {
						$.ajax(app.buildServiceData("template/upgradeTemplate.do", {
							data : {
								id : data.id,
							},
							context : this,
							success : function(data) {
								if (data) {
									if (data.success) {
										app.messager.info(data.msg);
										this._initData();
									} else {
										app.messager.warn(data.msg);
									}
								}
							},
							error : function(error) {
								app.messager.error("升版异常");
							}
						}));
					}
				}, this));
		/*	$.ajax(app.buildServiceData("template/queryMaxVersionTemplate.do", {
				data : {
					//subjectumId : this.subjectum.id
				},
				context : this,
				success : function(data) {
					if (data.data) {
						if (data.success) {
							this._upgradeTemplate({
								action : app.constants.ACT_EDIT,
								item : data.data
							});
						} else {
							app.messager.warn(data.msg);
						}
					}
				},
				error : function(error) {
					app.messager.error("升版异常");
				}
			}));*/
		},
		_showTemplate : function(dataInfo){
			
				app.utils.openTab(this, {
					"title" : "模板--" + dataInfo.item.templateCode + "(" + dataInfo.item.version + ")",
					"url" : "app/sentiment/sentimentconfig/templateForm",
					"template" : dataInfo.item,
					"action" : dataInfo.action,
					//"subjectum" : dataInfo.subjectum,
					"taskData" : this.taskData
				});
		},
	/*	_upgradeTemplate : function(dataInfo) {
			this.logger.debug();
			
			//如果最新版本未启用或禁用，不允许升级
			if(dataInfo.item.status!="1"){
				app.messager.error('模版未启用，不能升级模版');
				return;
			}
			
			app.messager.confirm("提示", "确认要升级模板吗？", _.bind(function(b) {
				if (b) {
					$.ajax(app.buildServiceData("template/upgradeTemplate.do", {
						data : {
							id : dataInfo.item.id,
							//subjectumId : this.subjectum.id
						},
						context : this,
						success : function(data) {
							if (data) {
								if (data.success) {
									app.messager.info(data.msg);
									this._initData();
								} else {
									app.messager.warn(data.msg);
								}
							}
						},
						error : function(error) {
							app.messager.error("升版异常");
						}
					}));
				}
			}, this));
		},*/
		_handleDelete : function(data) {
			this.logger.debug();
			if(data.row.status != app.constants.SENTIMENT_UNENABLE){
				app.messager.warn("模板的状态为启用或禁用，不能删除");
				return ;
			}
			//this._super();
			app.messager.confirm(app.constants.CONFIRM_TITLE, app.constants.CONFIRM_DELETE + this.options.entityName + "?", _.bind(function(r) {
				if (r) {
					$.ajax(app.buildServiceData(this.options.entity + "/deleteById.do", {
						data : {
							id : data.row[this.options.entityId]
						},
						context : this,
						success : function(data) {
							if (data) {
								var action = "删除操作执行";
								if (data.success) {
									app.messager.info(data.msg || action + "成功!");
								} else {
									app.messager.warn(data.msg || action + "失败!");
								}
							}
							this.refresh();
						}
					}));
				}
			}, this));
		},
		_handleEnableTemplate : function() {
			this.logger.debug();
			var data = this.dataGrid.datagrid("getSelected");
			if(data === null) {
				app.messager.warn('未选模板配置！');
				return;
			}
			if(data.status === app.constants.LOGIC_ENABLE){
				app.messager.confirm("提示","是否确定禁用所选模板配置？<br/>注：禁用后，当前时间范围内的批次将无法生成！",_.bind(function(r){
					if(r){
						$.ajax(app.buildServiceData("template/updateEnableDisableTemplate.do", {
							data : {
								id : data.id,
								//subjectumId : this.subjectum.id,
								status : app.constants.LOGIC_DISABLE
							},
							context : this,
							success : function(data) {
								if(data.success){
									app.messager.info('所选模板配置已禁用！');
									//this._loadData();
									this._initData();
								}else {
									app.messager.warn(data.msg);
								}
							}
						}));
					}},this));
			} else {
				app.messager.confirm("提示","是否确定启用所选模板配置？",_.bind(function(r){
					if(r){
						if(data.effectDate === null || data.expiryDate === null) {
							app.messager.warn("模板未配置有效时间！");
							return false;
						}
						if(data.merchantId === null || data.merchantId === "") {
							app.messager.warn("模板的商户唯一标识为空！");
							return false;
						}
						
						$.ajax(app.buildServiceData("template/updateEnableDisableTemplate.do", {
							data : {
								id : data.id,
								//subjectumId : this.subjectum.id,
								status : app.constants.LOGIC_ENABLE
							},
							context : this,
							success : function(data) {
								if(data.success){
									app.messager.info('所选模板配置已启用！');
									this._initData();
									//this._loadData();
								}else {
									app.messager.warn(data.msg);
								}
							}
						}));
					}},this));
			}
		},
		_openTemplateDialog : function(dataInfo) {
			this.logger.debug();
			var height = 290;
			var dialogInfo;
				dialogInfo = {
						title : dataInfo.action === app.constants.ACT_CREATE ? '新增模板' : '修改模板',
						width : 460,
						height : height,
						closed : false,
						autoClose : false,
						cache : false,
						className : 'app/sentiment/sentimentconfig/dialog/templateDialog',
						modal : true,
						data : dataInfo
				};
		
			dialogInfo = $.extend(dialogInfo, this._buildOpenConfig(dataInfo.action));
			// 打开对话框
			this._dlg = $("<div>").dialogExt(dialogInfo);
		},
		_buildOpenConfig : function() {
			var openConf = this._super();
			openConf = $.extend(openConf, {
				buttons : [ {
					text : app.constants.BTN_OK_LABEL,
					eventId : app.constants.BTN_OK,
					handler : _.bind(function(data,model) {
						this._dlg.dialogExt('destroy');
						this._dlg = null;
						if (data.success && model=="create") {
							this._openPartner(data.data);
						} else {
							this.refresh();
						}
					}, this)
				}, {
					text : app.constants.BTN_CANCEL_LABEL,
					eventId : app.constants.BTN_CANCEL,
					handler : _.bind(function() {
						this._dlg.dialogExt('destroy');
						this._dlg = null;
					}, this)
				} ]
			});
			return openConf;
		}
		
	});
});
