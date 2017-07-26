define([ "text!./templates/mailServerConfigManageList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/mailServerConfigManageSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.mailServerConfigManageList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("querymailServerConfig", {
				queryParams : {
//					code : this.options.code,
//					name : this.options.name,
//					systemcode :  this.options.systemcode
				}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess:function(data){
				}
			}));
		},
		_openmailServerConfigManageDialog : function(rowdata,mode) {
			var title = "";

			if(mode=="add"){
				title = "邮件配置新增";
			}else{
				title = "邮件配置编辑(可修改密码)";
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 380,
				height : 320,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/mailServerConfigManageDialog',
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
		_handleEdit : function(data) {
			this.logger.debug();
			var rowdata = data.row;
			this._openmailServerConfigManageDialog(rowdata,"edit");
		},
		_handleSearch : function(data) {
			this.logger.debug();
			var queryParams = this.searchBar.mailServerConfigManageSearchBar('value');
			
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('load');
		}
	});
});
