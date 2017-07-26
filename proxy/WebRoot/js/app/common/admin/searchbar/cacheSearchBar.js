define([ "text!./templates/cacheSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.cacheSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : false,
			unselectMsg : '请选择记录',

			grid : null,
			searchBar : 'cache',

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
			//				paramkey : this.ipt_paramEngName.val(),
			//				paramvalue : this.ipt_paramValue.val(),
			//				systemcode: this.ipt_paramSubSystem.combobox('getValue'),
			};
		},
		_onReset : function(event) {
			//			this.logger.debug();
			//			this.ipt_paramEngName.val("");
			//			this.ipt_paramValue.val("");
			//			this.ipt_paramSubSystem.combobox('setValue','');
		}
	});
});
