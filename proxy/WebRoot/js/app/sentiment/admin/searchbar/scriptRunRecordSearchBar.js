define([ "text!./templates/scriptRunRecordSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/scriptRunRecordSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.scriptRunRecordSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'scriptRunRecord'
		},

		templateString : template,
		
		_create : function() {
			this._super();
			this._initData();
		},
		_initData : function() {
			app.utils.initComboboxByDict(this.scriptTypeInput, {
				dicName : 'ScriptInfoType',
				isCheckAll : true,
				cached : true
			});
		}
		
	});
});
