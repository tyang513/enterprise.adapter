define([ "text!./templates/serverInfoSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/serverInfoSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.serverInfoSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'serverInfo'
		},

		templateString : template	
	});
});
