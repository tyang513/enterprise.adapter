define([ "text!./templates/extendedAttri.html", "app/base/BaseFormModule", "text!app/sentiment/sentimentconfig/config/extendedAttriService.json" ], function(
		template, BaseFormModule, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.extendedAttri", BaseFormModule, {
		// default options
		options : {
			pagination : true,
			entity : "extendedAttri",
			autoRender : false
		},

		templateString : template,

		// ---------------- interface -----------------
		onFormAction : function(data) {

			var eventId = data.eventId;
			if (eventId === "addExtendedAttr") {
				this._openExtendedAttrDialog({
					action : app.constants.ACT_CREATE,
					tempId : this.template.id
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
				if (data.length === 0) {
					app.messager.warn("模板扩展属性的数据不能为空！");
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
			this.dataGrid.datagrid(app.buildServiceData("extendedAttri/list.do", {
				pagination : this.options.pagination,
				queryParams : {
					templateId : this.formData.template.id
				},
				onLoadSuccess : function(data) {

					if (thisCopy.options.readOnly) {
						thisCopy.readOnlyDiv.hide();
					} else {
						thisCopy.readOnlyDiv.show();

					}
					thisCopy.dataGrid.show();

				}
			}));
			this._bindDatagrid(this, this.dataGrid);
			// app.utils.bindDatagrid(this, this.dataGrid);
		},

		_bindDatagrid : function(context, datagridEl) {
			var columns = datagridEl.datagrid("options").columns[0];
			_.each(columns, function(col) {
				if (col.formatter && _.isString(col.formatter) && context[col.formatter] && _.isFunction(context[col.formatter])) {
					col.formatter = _.bind(context[col.formatter], context);
				}
			});
		},

		_onSave : function(dataInfo) {
			this._openExtendedAttrDialog({
				action : app.constants.ACT_CREATE,
				tempId : this.formData.template.id
			});
		},

		_onEdit : function(dataInfo) {
			var data = this.dataGrid.datagrid('getSelected');
			if (data == null) {
				app.messager.warn("未选择模板扩展属性！");
				return;
			}
			;
			this._openExtendedAttrDialog({
				action : app.constants.ACT_EDIT,
				item : data
			});
		},

		_onDelete : function(dataInfo) {
			var data = this.dataGrid.datagrid('getSelected');
			if (data == null) {
				app.messager.warn("未选择模板扩展属性！");
				return;
			}
			;
			app.messager.confirm("提示", "确认要删除模板扩展属性吗？", _.bind(function(b) {
				if (b) {
					$.ajax(app.buildServiceData("extendedAttri/deleteById.do", {
						data : {
							id : data.id
						},
						context : this,
						success : function(data) {
							if (data) {
								if (data.success) {
									app.messager.info(data.msg);
									this.dataGrid.datagrid('reload', {
										templateId : this.formData.template.id
									});
								} else {
									app.messager.warn(data.msg);
								}
							}
						},
						error : function(error) {
							app.messager.error("操作异常");
						}
					}));
				}
			}, this));
		},

		_openExtendedAttrDialog : function(dataInfo) {
			this.logger.debug();
			var height = 180;

			var title = '新增模板扩展属性';
			if (dataInfo.action === app.constants.ACT_CREATE) {
				title = '新增模板扩展属性';
			} else if (dataInfo.action === app.constants.ACT_EDIT) {
				title = '修改模板扩展属性';
			}

			var dlg = $("<div>").dialogExt({
				title : title,
				width : 310,
				height : height,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/sentimentconfig/dialog/extendedAttriDialog',
				modal : true,
				data : dataInfo,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						dlg.dialogExt('destroy');
						if (data) {
							if (data.success) {
								app.messager.info(data.msg);
								this.dataGrid.datagrid('reload', {
									templateId : this.formData.template.id
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
				} ]
			});
		}
	});
});
