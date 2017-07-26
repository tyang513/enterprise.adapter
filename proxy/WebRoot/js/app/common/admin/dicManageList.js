define([ "text!./templates/dicManageList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/dicManageSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.dicManageList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		invoiceDicName : [],
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("querydic", {
				queryParams : {
					dicName : this.invoiceDicName
//					code : this.options.code,
//					systemcode :  this.options.systemcode
				}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess:function(data){
				}
			}));
		},
		_opendicManageDialog : function(rowdata,mode) {
			var title = "";

			if(mode=="add"){
				title = "数据字典新增";
			}else{
				title = "数据字典编辑";
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 350,
				height : 180,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/dicManageDialog',
				modal : true,
				data : {
					data : rowdata,
					mode : mode
				},
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						dlg.dialogExt('destroy');
						this.refresh();

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
			var rowdata = data.row;
			this._opendicManageDialog(null,"add");
		},
		_handleEdit : function(data) {
			this.logger.debug();
			var rowdata = data.row;
			this._opendicManageDialog(rowdata,"edit");
		},
		_handleViewItem : function(data) {
			this.logger.debug();
			var rowData = data.row;
			if (rowData != null) {
				console.log("openTaskTab");
				this._publish("app/openTab", {
					"title" : "字典项管理-"+rowData.description,
					"url" : "app/common/admin/dicItemList",
					"rowData" : rowData
					
				});
			}
		},
		
		_handleSearch : function(data) {
			this.logger.debug();
			var queryParams = this.searchBar.dicManageSearchBar('value');
			
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('load');
		},
		_handleDelete : function(data) {
			this.logger.debug();
			app.messager.confirm("确认信息","确认删除数据字典?",_.bind(function (r){
				if(r){
					$.ajax(app.buildServiceData("deletedic", {
						data : {
							id : data.row.id
						},
						type : 'POST',
						context : this,
						success : function(data) {
							app.messager.info('数据字典删除成功！');
							this.refresh();
						}
					}));
				}
			},this));
			
		}
	});
});
