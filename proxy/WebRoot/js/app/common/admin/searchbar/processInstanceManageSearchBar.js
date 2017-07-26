define([ "text!./templates/processInstanceManageSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.processInstanceManageSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			unselectMsg : '请选择记录',
			grid : null,
			searchBar : 'processInstanceManage',
			condition : undefined
		},
		templateString : template,
		_create : function() {
			this._super();
			this._initData();
		},
		_initData : function() {
			//流程状态
	    	app.utils.initComboboxByDict(this.ipt_processStatus, "ProcessState", {
	    		isCheckAll : false,
				cached : true
			});
	    	 //申请单类型
	    	 app.utils.initComboboxByDict(this.ipt_sheettype, "ApplySheetType", {
				isCheckAll : false,
				cached : true
			});
		},
		// ----------------interface---------------------
		value : function() {
			this.logger.debug();
			return {
				processname :  $.trim(this.ipt_processname.val()),
				sheetid :   $.trim(this.ipt_sheetid.val()),
				startername :   $.trim(this.ipt_startername.val()),
				sheettype :   this.ipt_sheettype.combobox('getValue'),
				status :   this.ipt_processStatus.combobox('getValue'),
				starttime : this.fromstarttime.datebox("getValue"),
				findStartTime : this.tostarttime.datebox("getValue"),
				endtime : this.fromendtime.datebox("getValue"),
				findEndTime : this.toendtime.datebox("getValue")

			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.ipt_processname.val("");
			this.ipt_startername.val("");
			this.ipt_sheetid.val("");
			this.ipt_processStatus.combobox('setValue','');
			this.ipt_sheettype.combobox('setValue','');
			this.fromstarttime.datebox('setValue', '');
			this.tostarttime.datebox('setValue', '');
			this.fromendtime.datebox('setValue', '');
			this.toendtime.datebox('setValue', '');
		}
	});
});
