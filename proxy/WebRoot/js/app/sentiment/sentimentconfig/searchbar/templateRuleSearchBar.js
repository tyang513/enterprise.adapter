define([ "text!./templates/templateRuleSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/templateRuleSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.templateRuleSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'templateRule'
		},

		templateString : template	
	});
});
