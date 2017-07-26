define([ "text!./templates/mailTemplateManageList.html", "app/base/BaseDialog", "app/common/admin/searchbar/mailTemplateManageSearchBar" ], function(template, BaseDialog) {
	return $.widget("app.mailTemplateManageList", BaseDialog, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("queryMailTemplate", {
				queryParams : {
					code : this.options.code,
					systemcode :  this.options.systemcode
				}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess:function(data){
				}
			}));
		},
		_openmailTemplateManageDialog : function(rowdata,mode) {
			var title = "";

			if(mode=="add"){
				title = "模板新增";
			}else{
				title = "模板编辑";
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 500,
				height : 320,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/mailTemplateManageDialog',
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
			//var rowdata = data.row;
			this._openmailTemplateManageDialog(null,"add");
		},
		_handleEdit : function(data) {
			this.logger.debug();
			var rowdata = data.row;
			this._openmailTemplateManageDialog(rowdata,"edit");
		},
		_handleSearch : function(data) {
			this.logger.debug();
			var queryParams = this.searchBar.mailTemplateManageSearchBar('value');
			
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('load');
		},
		_handleDelete : function(data) {
			this.logger.debug();
			
			app.messager.confirm("确认信息","确认删除邮件模板?",_.bind(function (r){
				if(r){
					$.ajax(app.buildServiceData("deleteMailTemplate", {
						data : {
							id : data.row.id
						},
						type : 'POST',
						context : this,
						success : function(data) {
							app.messager.info('邮件模板删除成功！');
							this.refresh();
						}
					}));
				}
			},this));
			
		}
	});
});
