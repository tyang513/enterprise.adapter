define([ "text!./templates/todoTaskSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.todoTaskSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'todoTask'
		},
		templateString : template,
		_create : function() {
			this._super();
			this._initData();

		},
		_initData : function() {
//			this.processName.combobox({
//				url : 'finTaskManage/queryProcessConf.do',
//				panelHeight : 'auto',
//				valueField : 'name',
//				textField : 'name'
//			});
			this.processName.combogrid({
			    mode: 'remote',
			    url: 'myTaskManage/queryProcessConf.do',
			    idField: 'name',
			    textField: 'name',
			    fitColumns:true,
			    showHeader: false,
			    columns: [[
			        {field:'name',title:'流程名称',width:120,sortable:true}
			    ]]
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
			this.processName.combogrid('setValue', '');
			this.finTaskName.val("");
			this.starterName.val("");
			this.sheetId.val("");
		}
	});
});
