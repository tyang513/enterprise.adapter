define([ "text!./templates/sysParamList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/sysParamSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.sysParamList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("querysysParam", {
				queryParams : {
					paramkey:  this.options.paramkey,
					paramvalue : this.options.paramvalue,
					systemcode :  this.options.systemcode
				}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess:function(data){
				}
			}));
		},
		_opensysParamDialog : function(rowdata) {
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '系统参数编辑',
				width : 400,
				height : 250,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/sysParamDialog',
				modal : true,
				data : {
					data : rowdata
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
		
		_handleEdit : function(data) {
			this.logger.debug();
			var rowdata = data.row;
			this._opensysParamDialog(rowdata);

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
			var queryParams = this.searchBar.sysParamSearchBar('value');
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('reload');
		}
	});
});
