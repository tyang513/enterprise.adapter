define([ "text!./templates/jobConfigErrorInfoList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/jobConfigErrorInfoSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.jobConfigErrorInfoList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("queryjobConfigErrorInfo", {
				queryParams : {
					jkey : this.options.jkey,
					name : this.options.name,
					status :  this.options.status
				}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess:function(data){
				}
			}));
		},
		_openjobConfigErrorInfoDialog : function(rowdata,mode) {
			var title = "";

			if(mode=="add"){
				title = "定时任务错误信息新增";
			}else{
				title = "定时任务错误信息查看";
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 450,
				height : 350,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/jobConfigErrorInfoDialog',
				modal : true,
				data : {
					data : rowdata,
					mode : mode
				},
				buttons : [
//				           {
//					text : '确认',
//					eventId : "ok",
//					handler : _.bind(function(data) {
//						dlg.dialogExt('destroy');
//						this.refresh();
//					}, this)
//				},
				{
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
			var queryParams = this.searchBar.jobConfigErrorInfoSearchBar('value');
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('load');
		},
		_handleView : function(data) {
			this.logger.debug();
			var row = data.row;
			var rowdata = {
					id : row.id,
					jobkey :row.jobkey,
					createtime :row.createtime,
					jobinputid :row.jobinputid,
					errorinfo :row.errorinfo
					};
			this._openjobConfigErrorInfoDialog(rowdata,"edit");
		},
		_handleDelete : function(data) {
			this.logger.debug();
			app.messager.confirm("确认信息","确认定时任务错误信息?",_.bind(function (r){
				if(r){
					$.ajax(app.buildServiceData("deletejobConfigErrorInfo", {
						data : {
							id : data.row.id
						},
						type : 'POST',
						context : this,
						success : function(data) {
							app.messager.info('定时任务错误信息删除成功！');
							this.refresh();
						}
					}));
				}
			},this));
			
		}
	});
});
