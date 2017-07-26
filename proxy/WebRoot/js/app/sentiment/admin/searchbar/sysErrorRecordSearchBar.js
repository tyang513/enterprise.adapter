define([ "text!./templates/sysErrorRecordSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/sysErrorRecordSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.sysErrorRecordSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'sysErrorRecord'
		},

		templateString : template	
	});
});
