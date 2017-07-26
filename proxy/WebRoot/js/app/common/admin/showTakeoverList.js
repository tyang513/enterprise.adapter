define([ "text!./templates/showTakeoverList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/showTakeoverListSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.showTakeoverList", BaseWidget, {
		// default options
		options : {},

		templateString : template,

		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		// -------------- interface ------------------
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');
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
		// ---------------functions------------------ contactAndAccount
		_initData : function() {
			this.logger.debug();
			this.dataGrid.datagrid(app.buildServiceData("getSalesTakeoverSettings", {
				queryParams : {flag:'admin'}, // 查询条件
				loadMsg : '数据加载中请稍后……'
			}));
		},
		_handleSearch : function(data) {
			this.logger.debug();

			var queryParams = this.searchBar.showTakeoverListSearchBar('value');
			queryParams.flag = 'admin';
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('reload');
		},
		_handleAdd : function(data) {
			this.logger.debug();
			this._openTaskOverDialog(null, "add");
		},
		_handleEdit : function(data) {
			this.logger.debug();
			var rowdata = data.row;
			this._openTaskOverDialog(rowdata, "edit");
		},
		_openTaskOverDialog : function(rowdata, mode) {
			var title = "";
			if (mode == "add") {
				title = "新增临时授权记录";
			} else {
				title = "编辑临时授权记录";
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 400,
				height : 320,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/openTaskOverDialog',
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
		_handleDelete : function(data) {
			this.logger.debug();
			
			app.messager.confirm("确认信息","确认删除临时授权?",_.bind(function (r){
				if(r){
					$.ajax(app.buildServiceData("removeSalesTakeoverSetting", {
						data : {
							id : data.row.id
						},
						type : 'POST',
						context : this,
						success : function(data) {
							if(data && data.sucMessage){
								app.messager.info(data.sucMessage);
							}else{
								app.messager.warn(data.failMessage);
							}
							this.refresh();
						}
					}));
				}
			},this));
			
		},
		_destroy : function() {
			this._super();   
		}

	});
});
