define([ "text!./templates/tempJobInputSearchBar.html", "app/base/BaseSearchBar", "text!app/common/admin/config/tempJobInputSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.tempJobInputSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'tempJobInput'
		},

		templateString : template,
		
		_create : function() {
			this._super();
			this._initData();
		},
		
		_initData : function() {
			//任务状态
	    	app.utils.initComboboxByDict(this.ipt_taskStatus, "CallStatus", {
	    		value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
	    		isCheckAll : false,
				cached : true
			});
		}
		
	});
});
