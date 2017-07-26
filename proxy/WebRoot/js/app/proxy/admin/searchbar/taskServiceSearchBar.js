define([ "text!./templates/taskServiceSearchBar.html", "app/base/BaseSearchBar", "text!app/proxy/admin/config/taskServiceSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.taskServiceSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'taskService'
		},

		templateString : template	
	});
});
