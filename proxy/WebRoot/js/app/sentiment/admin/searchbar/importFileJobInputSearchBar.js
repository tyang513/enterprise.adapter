define([ "text!./templates/importFileJobInputSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/importFileJobInputSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.importFileJobInputSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'importFileJobInput'
		},

		templateString : template	
	});
});
