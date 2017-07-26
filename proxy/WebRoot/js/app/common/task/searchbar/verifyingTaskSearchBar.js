define([ "text!./templates/verifyingTaskSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.verifyingTaskSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'verifyingTask'
		},
		templateString : template,
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
		// ----------------interface---------------------
		value : function() {
			this.logger.debug();
			return {
				processname : this.processName.combobox('getText'),
				taskname : $.trim(this.finTaskName.val()),
				startername :$.trim(this.starterName.val()),
				sheetId : $.trim(this.sheetId.val())
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.processName.combobox('setValue', '');
			this.finTaskName.val("");
			this.starterName.val("");
			this.sheetId.val("");
		}
	});
});
