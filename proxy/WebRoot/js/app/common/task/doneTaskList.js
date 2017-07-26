define([ "text!./templates/doneTaskList.html", "zf/base/BaseWidget", "app/common/task/searchbar/doneTaskSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.doneTaskList", BaseWidget, {
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
			this.doneTaskGrid.datagrid('reload');
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
			// console.dir(["param", param])
			var currentRow = this.doneTaskGrid.datagrid('getSelected');
			if (currentRow != null) {
				console.log("openTaskTab");
				app.utils.openTab(this, {
					"title" : currentRow.taskname,
					"url" : "app/common/task/taskForm",
					"taskData" : currentRow,
					"mode" : "view"
				});
			}
		},

		_handleSearch : function(data) {
			this.logger.debug();
			var queryParams = this.doneTaskSearchBar.doneTaskSearchBar('value');
			this.doneTaskGrid.datagrid('options').queryParams = queryParams;
			this.doneTaskGrid.datagrid('reload');
		},

		// ---------------functions------------------
		_initData : function() {
			var queryParams = this.doneTaskSearchBar.doneTaskSearchBar('value');
			this.doneTaskGrid.datagrid(app.buildServiceData("queryMyCompleteTask", {
				queryParams : queryParams, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess : function(data) {
				},
				onClickRow : function(index, rowData) {
					console.dir([ "rowData", rowData ])
				},
				onLoadError : function() {
				}
			}));
		},

		_destroy : function() {
			this._super();
		}
	});
});
