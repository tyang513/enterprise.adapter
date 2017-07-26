define([ "text!./templates/partnerSearchBar.html", "app/base/BaseSearchBar", "text!app/proxy/admin/config/partnerSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.partnerSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'partner'
		},

		templateString : template	
	});
});
