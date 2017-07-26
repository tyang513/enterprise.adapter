define([ "text!./templates/dicItemList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/dicItemSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.dicItemList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			var dicid = null;
			var parentid = null;
			if (this.options.rowData.dicid) {
				dicid = this.options.rowData.dicid;
				parentid = this.options.rowData.id;
			} else {
				dicid = this.options.rowData.id;
			}
			this.dataGrid.datagrid(app.buildServiceData("queryDicItemList", {
				queryParams : {
					dicid : dicid,
					parentid : parentid
				// systemcode : this.options.systemcode
				}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess : function(data) {
				}
			}));
		},
		_opendicItemDialog : function(rowdata, mode) {
			var title = "";

			if (mode == "add") {
				title = "字典项新增";
			} else {
				title = "字典项编辑";
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 350,
				height : 180,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/dicItemDialog',
				modal : true,
				data : {
					id : rowdata.id,
					dicid : rowdata.dicid,
					parentid : rowdata.parentid,
					dicitemkey : rowdata.dicitemkey,
					dicitemvalue : rowdata.dicitemvalue,
					mode : mode
				},
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function() {
						this.refresh();
						dlg.dialogExt('destroy');
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

		// ---------- event handler -------------
		_doAction : function(event, data) {
			this.logger.debug();
			console.dir([ event, data ]);
			var handlerName = "_handle" + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');

		},
		_handleCreate : function(data) {
			this.logger.debug();
			// var rowdata = data.row;
			var dicid = 0;
			var parentid = 0;
			if (this.options.rowData.dicid) {
				dicid = this.options.rowData.dicid;
				parentid = this.options.rowData.id;
			} else {
				dicid = this.options.rowData.id;
			}
			var rowdata = {
				id : null,
				dicid : dicid,
				parentid : parentid,
				dicitemkey : null,
				dicitemvalue : null
			};
			this._opendicItemDialog(rowdata, "add");
		},
		_handleEdit : function(data) {
			this.logger.debug();
			var row = data.row;
			var rowdata = {
				id : row.id,
				dicid : row.dicid,
				parentid : row.parentid,
				dicitemkey : row.dicitemkey,
				dicitemvalue : row.dicitemvalue
			};
			this._opendicItemDialog(rowdata, "edit");
		},
		_handleSearch : function(data) {
			this.logger.debug();
			var queryParams = this.searchBar.dicItemSearchBar('value');

			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('load');
		},
		_handleDelete : function(data) {
			this.logger.debug();
			app.messager.confirm("确认信息", "确认删除数据字典项?", _.bind(function(r) {
				if (r) {
					$.ajax(app.buildServiceData("deletedicItem", {
						data : {
							id : data.row.id
						},
						type : 'POST',
						context : this,
						success : function(data) {
							app.messager.info('数据字典项删除成功！');
							this.refresh();
						}
					}));
				}
			}, this));

		},
		_handleViewItem : function(data) {
			this.logger.debug();
			var rowData = data.row;
			if (rowData != null) {
				this._publish("app/openTab", {
					"title" : "字典项管理-" + rowData.dicitemvalue,
					"url" : "app/common/admin/dicItemList",
					"rowData" : rowData
				});
			}
		}
	});
});
