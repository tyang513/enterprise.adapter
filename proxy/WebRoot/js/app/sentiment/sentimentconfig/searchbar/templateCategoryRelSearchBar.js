define([ "text!./templates/templateCategoryRelSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/templateCategoryRelSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.templateCategoryRelSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'templateCategoryRel'
		},

		templateString : template	
	});
});
