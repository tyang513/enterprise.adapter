define([ "text!./templates/taskConfigInfoList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/taskConfigSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.taskConfigInfoList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("querytaskConfig", {
				queryParams : {
					code : this.options.code,
					name : this.options.name,
					processname  : this.options.processname
				}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess:function(data){
				}
			}));
		},
		_opentaskConfigInfoDialog : function(rowdata,mode) {
			var title = "";

			if(mode=="add"){
				title = "任务设置新增";
			}else{
				title = "任务设置编辑";
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 480,
				height : 460,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/taskConfigInfoDialog',
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
		_handleSearch : function(data) {
			this.logger.debug();
			var queryParams = this.searchBar.taskConfigSearchBar('value');
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('load');
		},
		_handleCreate : function(data) {
			this.logger.debug();
			var rowdata = {
					/*
					id : null,
					dicid : this.options.rowData.id,
					dicitemkey :null,
					dicitemvalue :null
					*/
				};
			this._opentaskConfigInfoDialog(rowdata,"add");
		},
		_handleEdit : function(data) {
			this.logger.debug();
			var row = data.row;
			var rowdata = {
					id : row.id,
					code :row.code,
					name :row.name,
					timeout :row.timeout,
					remindinterval :row.remindinterval,
					taskdescription :row.taskdescription,
					timeoutremindflag :row.timeoutremindflag,
					processname :row.processconfigid,
					rolecode :row.rolecode,
					taskstartemailtemplateid :row.taskstartemailtemplateid,
					taskendemailtemplateid :row.taskendemailtemplateid,
					taskdescription :row.taskdescription
			
					};
			this._opentaskConfigInfoDialog(rowdata,"edit");
		},
		_handleDelete : function(data) {
			this.logger.debug();
			app.messager.confirm("确认信息","确认任务设置信息?",_.bind(function (r){
				if(r){
					$.ajax(app.buildServiceData("deleteTaskConfig", {
						data : {
							id : data.row.id
						},
						type : 'POST',
						context : this,
						success : function(data) {
							app.messager.info('任务设置信息删除成功！');
							this.refresh();
						}
					}));
				}
			},this));
			
		}
	});
});
