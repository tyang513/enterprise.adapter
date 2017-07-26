define([ "text!./templates/stmtUserAgentSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/stmtUserAgentSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.stmtUserAgentSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'stmtUserAgent'
		},

		templateString : template	
	});
});
