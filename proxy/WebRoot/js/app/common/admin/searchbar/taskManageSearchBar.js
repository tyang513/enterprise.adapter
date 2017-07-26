define([ "text!./templates/taskManageSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.taskManageSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			unselectMsg : '请选择记录',
			grid : null,
			searchBar : 'taskManage',
			condition : undefined
		},
		templateString : template,
		_create : function() {
			this._super();
			this._initData();
		},
		_initData : function() {
			//任务状态
	    	app.utils.initComboboxByDict(this.ipt_taskStatus, "ProcessState", {
	    		isCheckAll : false,
				cached : true
			});
	    	
		},
		// ----------------interface---------------------
		value : function() {
			this.logger.debug();
			return {
				processname :  this.ipt_processname.val(),
				taskname :  this.ipt_taskname.val(),
				assignername :   this.ipt_assignername.val(),
				status :   this.ipt_taskStatus.combobox('getValue'),
				starttime : this.fromstarttime.datebox("getValue"),
				findStartTime : this.tostarttime.datebox("getValue"),
				endtime : this.fromendtime.datebox("getValue"),
				findEndTime : this.toendtime.datebox("getValue")

			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.ipt_processname.val("");
			this.ipt_taskname.val("");
			this.ipt_assignername.val("");
			this.ipt_taskStatus.combobox('setValue','');
			this.fromstarttime.datebox('setValue', '');
			this.tostarttime.datebox('setValue', '');
			this.fromendtime.datebox('setValue', '');
			this.toendtime.datebox('setValue', '');
		}
	});
});
