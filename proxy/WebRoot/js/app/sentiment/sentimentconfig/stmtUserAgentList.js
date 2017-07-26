define([ "text!./templates/stmtUserAgentList.html", "app/base/BaseContentList", "app/sentiment/sentimentconfig/searchbar/stmtUserAgentSearchBar", "text!app/sentiment/sentimentconfig/config/stmtUserAgentService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.stmtUserAgentList", BaseContentList, {
		// default options
		options : {
			entity : 'stmtUserAgent',
			entityName : '用户代理',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 435,
				height : 290,
				className : 'app/sentiment/sentimentconfig/dialog/stmtUserAgentDialog'
			},
			formConfig : {
				url : 'app/sentiment/sentimentconfig/stmtUserAgentForm'
			}
		},

		templateString : template,
		_handleView :function(data){
			this._openTemplateRuleDialog({
				action : app.constants.ACT_VIEW,
				item : data.row,
			});
		},
		_handleCreate :function(){
			this._openTemplateRuleDialog({
				action : app.constants.ACT_CREATE,
			});
		},
		_handleEdit :function(data){
			this._openTemplateRuleDialog({
				action : app.constants.ACT_EDIT,
				item : data.row,
			});
		},
		_openTemplateRuleDialog : function(dataInfo) {
			this.logger.debug();
			var buttons =  [ {
				text : '确认',
				eventId : "ok",
				handler : _.bind(function(data) {
					dlg.dialogExt('destroy');
					if (data) {
						if (data.success) {
							app.messager.info(data.msg);
							this.dataGrid.datagrid('reload',{
								//tempId : this.formData.template.id
							});
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
			} ];
			
			var title = '';
			this.height='';
			this.width='';
			if(dataInfo.action === app.constants.ACT_CREATE) {
				title = '新增用户代理';
				this.height=180;
				this.width=435;
			} else if(dataInfo.action === app.constants.ACT_EDIT) {
				title = '修改用户代理';
				this.height=180;
				this.width=435;
			}else if(dataInfo.action === app.constants.ACT_VIEW){
				title = '查看用户代理';
				this.height=290;
				this.width=435;
			}
			
			var dlg = $("<div>").dialogExt({
				title : title,
				width :  this.width,
				height : this.height,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/sentimentconfig/dialog/stmtUserAgentDialog',
				modal : true,
				data : {
					item : dataInfo.item,
					mode : dataInfo.action,
				},
				buttons : buttons
			});
		}
	});
});
