define([ "text!./templates/jobConfigInfoSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.jobConfigInfoSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			unselectMsg : '请选择记录',

			grid : null,
			searchBar : 'jobConfigInfo',

			condition : undefined
		},

		templateString : template,

		_create : function() {
			this._super();
			this._initData();
			
		},
		_initData : function() {

		},
		// ----------------interface---------------------
		value : function() {
			this.logger.debug();
			return {
				jkey : this.ipt_code.val(),
				name : this.ipt_name.val(),
				status :   this.ipt_status.combobox('getValue')
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.ipt_code.val("");
			this.ipt_name.val("");
			this.ipt_status.combobox('setValue','');
		}
	});
});
