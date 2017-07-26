define([ "text!./templates/httpProxySearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/httpProxySearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.httpProxySearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'httpProxy'
		},

		templateString : template	
	});
});
