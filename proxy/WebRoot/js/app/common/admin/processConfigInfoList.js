define([ "text!./templates/processConfigInfoList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/processConfigInfoSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.processConfigInfoList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("queryprocessConfigInfo", {
				queryParams : {
					code : this.options.code,
					name : this.options.name
				}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess:function(data){
				}
			}));
		},
		_openprocessConfigInfoDialog : function(rowdata,mode) {
			var title = "";

			if(mode=="add"){
				title = "流程设置新增";
			}else{
				title = "流程设置编辑";
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 500,
				height : 430,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/processConfigInfoDialog',
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
			var queryParams = this.searchBar.processConfigInfoSearchBar('value');
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
			this._openprocessConfigInfoDialog(rowdata,"add");
		},
		_handleEdit : function(data) {
			this.logger.debug();
			var row = data.row;
			var rowdata = {
					id : row.id,
					code :row.code,
					name :row.name,
					version :row.version,
					processdefinitionviewurl :row.processdefinitionviewurl,
					defaultremindinterval:row.defaultremindinterval,
					defaulttasktimeout :row.defaulttasktimeout,
					description :row.description,
					systemcode :row.systemcode
					};
			this._openprocessConfigInfoDialog(rowdata,"edit");
		},
		_handleDelete : function(data) {
			this.logger.debug();
			app.messager.confirm("确认信息","确认流程设置信息?",_.bind(function (r){
				if(r){
					$.ajax(app.buildServiceData("deleteProcessConfigInfo", {
						data : {
							id : data.row.id
						},
						type : 'POST',
						context : this,
						success : function(data) {
							app.messager.info('流程设置信息删除成功！');
							this.refresh();
						}
					}));
				}
			},this));
			
		}
	});
});
