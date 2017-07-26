define([ "text!./templates/templateRule.html", "app/base/BaseFormModule"], function(template, BaseFormModule) {
	return $.widget("app.templateRule", BaseFormModule, {
		// default options
		options : {
			pagination:true,
			entity:"templateRule",
			autoRender:false
		},
		
		templateString : template,
		
		// ---------------- interface -----------------
		onFormAction : function(data) {
			var eventId = data.eventId;
			if (eventId === "addTemplateRule") {
				this._openTemplateRuleDialog({
					action : app.constants.ACT_CREATE,
					tempId : this.template.id,
					categoryCode : this.formData.item.categoryCode,
					categoryName : this.formData.item.categoryName
				});
			}
		},
		
		render : function(formData) {
			this._super(formData);
			this._initData();
		},
		
		validate : function() {
			if (this.options.notNull) {
				var data = this.dataGrid.datagrid("getRows");
				if(data.length === 0) {
					app.messager.warn("计算规则的数据不能为空！");
					return false;
				}
			}
			return true;
		},

		value : function() {
			return {};
		},
		
		// --------biz function-----------------
		_initData : function() {
			this.logger.debug();
			
			var thisCopy = this;
			this.dataGrid.datagrid(app.buildServiceData("templateRule/list.do", {
				pagination:this.options.pagination,
				queryParams : {
					tempId : this.formData.template.id,
					checkedId : false
				},
				onLoadSuccess : function(data) {
					 
						if(thisCopy.options.readOnly){
							thisCopy.readOnlyDiv.hide();
						} else {
							thisCopy.readOnlyDiv.show();
						}
						thisCopy.dataGrid.show();
					 
				}
			}));
			app.utils.bindDatagrid(this, this.dataGrid); 
		},
		
		_onSave : function(dataInfo){
			 
			this._openTemplateRuleDialog ({
				action : app.constants.ACT_CREATE,
				tempId : this.formData.template.id,
				categoryCode : this.formData.template.categoryCode,
				categoryName : this.formData.template.categoryName
			});
		},
		
		_onEdit : function(dataInfo){
			var data = this.dataGrid.datagrid('getSelected');
			if (data == null) {
				app.messager.warn("未选择模板计算规则！");
				return ;
			};
			this._openTemplateRuleDialog ({
				action : app.constants.ACT_EDIT,
				item : data,
				categoryCode : this.formData.template.categoryCode,
				categoryName : this.formData.template.categoryName
			});
		},
		
		_onDelete : function(dataInfo){
			var data = this.dataGrid.datagrid('getSelected');
			if (data == null) {
				app.messager.warn("未选择模板计算规则！");
				return ;
			};
			app.messager.confirm("提示", "确认要删除模板计算规则吗？", _.bind(function(b) {
				if (b) {
					$.ajax(app.buildServiceData("templateRule/deleteById.do", {
						data : {
							id : data.id
						},
						context : this,
						success : function(data) {
							if (data) {
								if (data.success) {
									app.messager.info(data.msg + "操作成功");
									this.dataGrid.datagrid('reload');
								} else {
									app.messager.warn(data.msg + "操作失败");
								}
							}
						},
						error : function(error) {
							app.messager.error("已使用的不能删除");
						}
					}));
				}
			}, this));
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
								tempId : this.formData.template.id
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
			if(dataInfo.action === app.constants.ACT_CREATE) {
				title = '新增模板计算规则';
			} else if(dataInfo.action === app.constants.ACT_EDIT) {
				title = '修改模板计算规则';
			}
			
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 540,
				height : 600,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/sentimentconfig/dialog/templateRuleDialog',
				modal : true,
				data : dataInfo,
				buttons : buttons
			});
		}
	});
});
