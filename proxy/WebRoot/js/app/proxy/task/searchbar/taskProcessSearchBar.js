define([ "text!./templates/taskProcessSearchBar.html", "app/base/BaseSearchBar", "text!app/proxy/task/config/taskProcessSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.taskProcessSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'taskProcess'
		},

		templateString : template
	});
});
