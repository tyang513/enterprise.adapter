define([ "text!./templates/${searchbarWidgetName}.html", "app/base/BaseSearchBar", "text!app/${bizAppPackage}/${entityPackage}/config/${searchbarWidgetName}.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.${searchbarWidgetName}", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : '${lowerName}'
		},

		templateString : template	
	});
});
