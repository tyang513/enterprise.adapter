define([ "text!./templates/taskManageList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/taskManageSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.taskManageList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("querytaskManage", {
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
		_openFindUserDialog : function(rowdata) {
			this.logger.debug();
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '选择用户',
				width : 600,
				height : 400,
				closed : false,
				cache : false,
				className : 'app/common/admin/dialog/userSearchDialog',
				modal : true,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						this._doDistribute(rowdata,data.umid,data.username);
						this.refresh();
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
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
			if(rowdata.status == 2 ||rowdata.status == 3|| rowdata.status==4 ){
				app.messager.alert('', '已完成或已结束或被系统结束的任务不能再分配!');
			}else{
				this._distributeTask(rowdata);
			}
			

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
			var queryParams = this.searchBar.taskManageSearchBar('value');
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('reload');
		},
		_distributeTask : function(rowdata){
			this._openFindUserDialog(rowdata);
		},
		_doDistribute : function(rowdata,umid,umname){
			
				$.ajax({
					type : 'POST',
					url : 'workflow/reAssignee.do',
					dataType : 'json',
					data : {
						id : rowdata.id,
						assignerumid : umid,
						assignerumname : umname
					},
					context : this
				}).done(function(data) {
					if (data.message === "OK") {
						app.messager.info("分配任务成功！");
						this.refresh();
						return true;
					}else{
						app.messager.warn(data.message);
						return false;
					}
				}).fail(function(error) {
					app.messager.error('分配任务发生异常.');
					return false;
				});	
		}
	});
});
