define([ "text!./templates/showAuditLogList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/auditLogListSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.showAuditLogList", BaseWidget, {
		// default options
		options : {},

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
			this.dataGrid.datagrid(app.buildServiceData("queryAuditLog", {
				queryParams : {}, // 查询条件
				loadMsg : '数据加载中请稍后……'
			}));
		},
		_handleSearch : function(data) {
			this.logger.debug();

			var queryParams = this.searchBar.auditLogListSearchBar('value');
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('reload');
		},
		_destroy : function() {
			this._super();   
		}

	});
});
