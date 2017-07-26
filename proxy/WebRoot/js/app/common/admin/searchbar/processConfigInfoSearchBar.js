define([ "text!./templates/processConfigInfoSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.processConfigInfoSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			unselectMsg : '请选择记录',

			grid : null,
			searchBar : 'processConfigInfo',

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
				code : this.ipt_code.val(),
				name : this.ipt_name.val()
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.ipt_code.val("");
			this.ipt_name.val("");
		}
	});
});
