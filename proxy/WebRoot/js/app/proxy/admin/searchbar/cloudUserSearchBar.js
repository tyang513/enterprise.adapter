define([ "text!./templates/cloudUserSearchBar.html", "app/base/BaseSearchBar", "text!app/proxy/admin/config/cloudUserSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.cloudUserSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'cloudUser'
		},

		templateString : template	
	});
});
