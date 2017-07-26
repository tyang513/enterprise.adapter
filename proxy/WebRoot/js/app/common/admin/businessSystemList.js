define([ "text!./templates/businessSystemList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/businessSystemListSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.businessSystemList", BaseWidget, {
		// default options
		options : {},
		//
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
			this.dataGrid.datagrid(app.buildServiceData("queryBusinessSystem", {
				queryParams : {}, // 查询条件
				loadMsg : '数据加载中请稍后……'
			}));
		},
		_handleEdit : function(rowdata) {
			this.logger.debug();
			var mode = "edit";
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '修改子系统',
				width : 500,
				height : 350,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/openBusinessSystemListDialog',
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
		},/*
		_handleAdd : function(data) {
			this.logger.debug();
			var rowdata = data.row;
			this._openCalendarDialog(null,"add");
		},
		_openCalendarDialog : function(rowdata,mode) {
			var title = "";
			if(mode=="add"){
				title = "新增日历";
			}else{
				title = "编辑日历";
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 300,
				height : 220,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/openCalendarDialog',
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
			$.ajax(app.buildServiceData("deleteCalendar", {
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
		},*/
		_destroy : function() {
			this._super();   
		}

	});
});
