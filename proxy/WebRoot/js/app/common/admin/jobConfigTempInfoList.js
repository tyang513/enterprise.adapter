define([ "text!./templates/jobConfigTempInfoList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/jobConfigTempInfoListSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.jobConfigTempInfoList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this.dicts = {};
			this._initData();
		},
		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("queryJobConfigTempList", {
				queryParams : {}, // 查询条件
				loadMsg : '数据加载中请稍后……'
			}));
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
			var queryParams = this.searchBar.jobConfigTempInfoListSearchBar('value');
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('load');
		},
		_handleRetry : function(data) {
			this.logger.debug();
			$.ajax(app.buildServiceData("updateJobConfigTemp", {
				data : {
					id: data.row.id 
				},
				type : 'POST',
				context : this,
				success : function(data) {
					if(data.success){
						app.messager.info(data.success);
						this.refresh();
					}
					else
						app.messager.warn(data.error);
				}
			}));
		}
	});
});
