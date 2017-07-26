define([ "text!./templates/temporaryAuthorizeDialog.html", "zf/base/BaseWidget", "app/base/BaseSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.temporaryAuthorizeDialog", BaseWidget, {
		// default options
		options : {
		},

		templateString : template,

		_create : function() {
			this._super();
			this._initData();
		},
		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("getSalesTakeoverSettings", {
				queryParams : {
					'userUmId' : this.options.submituserumid
				}, // 查询条件
				loadMsg : '数据加载中请稍后……'
			}));
		},
		// ---------- event handler -------------
		_doAction : function(event, data) {
			var handlerName = "_handle" + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},
		_handleDelete : function(data) {
			this.logger.debug();
			var datagrid = this;
			app.messager.confirm("确认删除", "是否确认删除",function(r){
				if (r) {
					$.ajax(app.buildServiceData("removeSalesTakeoverSettingPerson", {
						data : {
							id : data.row.id
						},
						type : 'POST',
						context : this,
						success : function(data) {
							app.messager.info(data.msg);
							datagrid.refresh();
						},
						error : function(data) {
							app.messager.info('个人临时授权删除失败！');
						}
						
					}));
				}
			}); 
		},
		_handleAdd : function(data) {
			this.logger.debug();
				// 打开对话框
				var dlg = $("<div>").dialogExt({
					title : '新增临时授权人',
					width : 400,
					height : 250,
					closed : false,
					autoClose : false,
					cache : false,
					className : 'app/common/admin/dialog/temporaryAuthorizeAddDialog',
					modal : true,
					data : {"messageInfo" : data.row},
					buttons : [ {
						text : '提交',
						eventId : "ok",
						handler:_.bind(function(){
							dlg.dialogExt('destroy');
							this.refresh();
						},this)
					}, {
						text : '取消',
						eventId : "cancel",
						handler : function() {
							dlg.dialogExt('destroy');
						}
					} ]
				});
		},
		refresh : function() {
			this.logger.debug();
			var queryParams;
			queryParams = {
				'userUmId' : this.options.submituserumid
			};
			//var queryParams.userUmId = this.options.data.submituserumid;
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('reload');
		}
	});
});
