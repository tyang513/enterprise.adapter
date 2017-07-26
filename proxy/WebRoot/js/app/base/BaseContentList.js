define([ "zf/base/BaseWidget" ], function(BaseWidget) {
	return $.widget("app.BaseContentList", BaseWidget, {
		// default options
		options : {
			entity : undefined,
			entityName : undefined,
			entityId : 'id',

			autoRender : true,
			enableFilter : true,

			openType : 'dialog',
			dialogConfig : {},
			formConfig : {},
			loadMsg : "数据加载中请稍后……"
		},

		// the constructor
		_create : function() {
			this._super();
			this._dlg = null;
			if (this.options.autoRender) {
				this._initData();
			}
		},

		// -------------- interface ------------------
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');
		},

		render : function() {
			this._initSearchBar();
			this._initData();
		},

		_buildOpenConfig : function() {
			var config = {};
			if (this.options.openType === 'dialog') {
				config = this.options.dialogConfig;
			} else if (this.options.openType === 'form') {
				config = this.options.formConfig;
			}
			return config;
		},

		// ---------- event handler -------------
		_doAction : function(event, data) {
			this.logger.debug();
			console.dir([ event, data ]);
			var handlerName = app.constants.ACTION_HANDLER_PREFIX + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},

		_onClickRow : function(rowIndex, rowData) {
			this.searchBar[this.options.entity + "SearchBar"]('onGridClickRow', rowIndex, rowData);
		},
		// ---------------functions------------------
		_initSearchBar : function() {
			this.searchBar[this.options.entity + "SearchBar"]({
				autoRender : this.options.autoRender,
				enableFilter : this.options.enableFilter
			});
			this.searchBar[this.options.entity + "SearchBar"]('render');
		},

		_initData : function() {
			this.logger.debug();
			this.dataGrid.datagrid(app.buildServiceData(this.options.entity + "/list.do", {
				queryParams : this.searchBar[this.options.entity + "SearchBar"]('value'), // 查询条件
				onClickRow : _.bind(this._onClickRow, this),
				loadMsg : this.options.loadMsg,
				frozenColumns: [[{field: "fk", checkbox: true,radiobox :true}]],
				onLoadError : _.bind(this._onLoadError, this)
			}));
			app.utils.bindDatagrid(this, this.dataGrid);
		},
		_onLoadError : function(){
			this.logger.debug();
			app.messager.error("查询异常.");
		},
		_handleCreate : function() {
			this.logger.debug();
			this._openEntity({
				action : app.constants.ACT_CREATE
			});
		},

		_handleEdit : function(data) {
			this.logger.debug();
			this._openEntity({
				item : data.row,
				action : app.constants.ACT_EDIT
			});
		},

		_handleView : function(data) {
			this.logger.debug();
			this._openEntity({
				item : data.row,
				action : app.constants.ACT_VIEW
			});
		},

		_handleDelete : function(data) {
			this.logger.debug();
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

		_handleSearch : function(data) {
			this.logger.debug();
			this.dataGrid.datagrid('options').queryParams = this.searchBar[this.options.entity + "SearchBar"]('value');
			this.dataGrid.datagrid('load');
		},

		_getOpenEntityTitle : function(data) {
			var title = this.options.entityName;
			if (data.action === app.constants.ACT_CREATE) {
				title = '新建' + this.options.entityName;
			} else if (data.action === app.constants.ACT_EDIT) {
				title = '修改' + this.options.entityName;
			} else if (data.action === app.constants.ACT_VIEW) {
				title = '查看' + this.options.entityName;
			}
			return title;
		},

		_openEntity : function(data) {
			if (this.options.openType === 'dialog') {
				this._openDialog(data);
			} else if (this.options.openType === 'form') {
				this._openForm(data);
			} else {
				this.logger.error("未知实体对象的打开类型:" + this.options.openType);
			}
		},

		_openDialog : function(data) {
			this.logger.debug();
			var title = this._getOpenEntityTitle(data);
			var buttons = [ {
				text : app.constants.BTN_OK_LABEL,
				eventId : app.constants.BTN_OK,
				handler : _.bind(function(data) {
					this._dlg.dialogExt('destroy');
					this._dlg = null;
					this.refresh();
				}, this)
			}, {
				text : app.constants.BTN_CANCEL_LABEL,
				eventId : app.constants.BTN_CANCEL,
				handler : _.bind(function() {
					this._dlg.dialogExt('destroy');
					this._dlg = null;
				}, this)
			} ];

			if (data.action === app.constants.ACT_VIEW) {
				buttons = buttons.splice(1, 1);
			}

			var dialogInfo = {
				title : title,
				closed : false,
				autoClose : false,
				cache : false,
				modal : true,
				data : data,
				buttons : buttons
			};

			dialogInfo = $.extend(dialogInfo, this._buildOpenConfig(data.action));
			// 打开对话框
			this._dlg = $("<div>").dialogExt(dialogInfo);
		},

		_openForm : function(data) {
			this.logger.debug();

			data.title = this._getOpenEntityTitle(data);
			data = $.extend(data, this._buildOpenConfig(data.action));

			this._publish("app/openTab", data);
		},

		_destroy : function() {
			this._dlg = null;
			this._super();
		}
	});
});
