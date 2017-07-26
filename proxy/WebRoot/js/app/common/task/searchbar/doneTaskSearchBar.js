define([ "text!./templates/doneTaskSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.doneTaskSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'doneTask'
		},

		templateString : template,

		// the constructor
		_create : function() {
			this._super();		
			this._initData();
		},
		_initData : function() {
			this.processName.combobox({
				url : 'myTaskManage/queryProcessConf.do',
				panelHeight : 'auto',
				valueField : 'name',
				textField : 'name'
			});
		},
		getParamData : function(operate){
			// if operate is 'search' that return search condition object
			return "param";
		},
		value : function() {
			this.logger.debug();
			var queryParams = [];
		 	queryParams["processName"] = this.processName.combobox('getText'),
			queryParams["taskName"] =  $.trim(this.taskName.val()),
			queryParams["sheetId"] = $.trim(this.sheetId.val()),
			queryParams["starterName"] = $.trim(this.starterName.val()),
			queryParams["taskCompleteStartTime"] = this.taskCompleteStartTime.datebox('getValue'),
			queryParams["taskCompleteEndTime"] = this.taskCompleteEndTime.datebox('getValue');
			return queryParams;
		},
		_onReset : function(event) {
			this.logger.debug();
			this.processName.combobox('setValue', '');
			this.taskName.val("");
			this.sheetId.val("");
			this.starterName.val("");
			this.taskCompleteStartTime.datebox('setValue', '');
			this.taskCompleteEndTime.datebox('setValue', '');
		},
		openTask : function(event){
//			var data = {
//				opertion : "openTask",
//				param : this.getParamData("openTask")
//			}
			this._onAction(event ,data);
		},
		// events bound via _on are removed automatically
		// revert other modifications here
		_destroy : function() {
			this._super();
		}
	});
});
