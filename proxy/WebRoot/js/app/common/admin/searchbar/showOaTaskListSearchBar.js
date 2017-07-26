define([ "text!./templates/showOaTaskListSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.showOaTaskListSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'showOaTaskList'
		},

		templateString : template,
		
		_create : function() {
			this._super();		
		},
		_initData : function() {
			this.logger.debug();
			app.utils.initComboboxByDict(this.approve, {
				dicName : 'isAgree',
				isCheckAll : true,
				cached : true
			});
			app.utils.initComboboxByDict(this.status, {
				dicName : 'oaTaskStatus',
				isCheckAll : true,
				cached : true
			});
		},
		render : function() {
			this._super();
			this._initData();
		},
		value : function() {
			this.logger.debug();
			return {
				instancename : $.trim(this.instancename.val()),
				status: this.status.combobox('getValue'),
				approve: this.approve.combobox('getValue'),
				taskname : $.trim(this.taskname.val()),
				starttime : this.starttime.datebox("getValue"),
				findStartTime : this.findStartTime.datebox("getValue"),
				endtime : this.endtime.datebox("getValue"),
				findEndTime : this.findEndTime.datebox("getValue")
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.instancename.val("");
			this.status.combobox('setValue','');
			this.approve.combobox('setValue','');
			this.taskname.val("");
			this.starttime.datebox('setValue', '');
			this.findStartTime.datebox('setValue', '');
			this.endtime.datebox('setValue', '');
			this.findEndTime.datebox('setValue', '');
		}
	
	});
});
