define([ "text!./templates/processInstanceManageList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/processInstanceManageSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.processInstanceManageList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("queryprocessInstanceManage", {
				queryParams : {
//					paramkey:  this.options.paramkey,
//					paramvalue : this.options.paramvalue,
//					systemcode :  this.options.systemcode
				}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess:function(data){
				}
			}));
		},
		_openprocessInstanceManageDialog : function(rowdata) {
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '系统参数编辑',
				width : 400,
				height : 250,
				closed : false,
				//autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/processInstanceManageDialog',
				modal : true,
				data : {
					data : rowdata
				},
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						//dlg.dialogExt('destroy');
						this.refresh();
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						//dlg.dialogExt('destroy');
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
		_handleEdit : function(data) {
			this.logger.debug();
			this.logger.debug();
			var rowdata = data.row;
			this._stopProcess(rowdata);

		},
		_handleSearch : function(data) {
			this.logger.debug();
			this.query();
		},
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');
		},
		query : function(){
			var queryParams = this.searchBar.processInstanceManageSearchBar('value');
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('reload');
		},
		_stopProcess : function(rowdata){

				$.ajax({
					type : 'POST',
					url : 'workflow/stopProcessInstance.do',
					dataType : 'json',
					data : {
						id : rowdata.id
					},
					context : this
				}).done(function(data) {
					if (data.message === "OK") {
						app.messager.info("停止流程成功！");
						return true;
					}else{
						app.messager.warn(data.message);
						return false;
					}
				}).fail(function(error) {
					app.messager.error('停止流程发生异常.');
					return false;
				});
		}
	});
});
