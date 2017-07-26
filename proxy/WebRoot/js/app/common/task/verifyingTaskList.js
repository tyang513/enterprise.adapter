define([ "text!./templates/verifyingTaskList.html", "zf/base/BaseWidget", "app/common/task/searchbar/verifyingTaskSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.verifyingTaskList", BaseWidget, {
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

		// ------------event handler-------------
		_doAction : function(event, data) {
			this.logger.debug();
			console.dir([ event, data ]);
			var handlerName = "_handle" + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},

		_handleView : function(data) {
			this.logger.debug();
			if (data.row != null) {
				console.log("openTaskTab");
				app.utils.openTab(this, {
					"title" : data.row.taskname,
					"url" : "app/common/task/taskForm",
					"taskData" : data.row,
					"mode" : "view"
				});
			}
		},

		_handleSearch : function(data) {
			this.logger.debug();
			var queryParams = this.verifyingTaskSearchBar.verifyingTaskSearchBar('value');
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('reload');
		},

		// ---------------functions------------------
		_initData : function() {
			var queryParams = this.verifyingTaskSearchBar.verifyingTaskSearchBar('value');
			this.dataGrid.datagrid(app.buildServiceData("queryMyVerifyingTask", {
				queryParams : queryParams, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onClickRow : function(index, rowData) {
					console.dir([ "rowData", rowData ])
				}
			}));
		},

		_destroy : function() {
			this._super();
		}
	});
});
