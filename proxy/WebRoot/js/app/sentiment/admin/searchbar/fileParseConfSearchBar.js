define([ "text!./templates/fileParseConfSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/fileParseConfSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.fileParseConfSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'fileParseConf'
		},

		templateString : template	
	});
});
