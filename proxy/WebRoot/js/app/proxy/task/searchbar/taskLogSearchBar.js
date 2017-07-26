define([ "text!./templates/taskLogSearchBar.html", "app/base/BaseSearchBar", "text!app/proxy/task/config/taskLogSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.taskLogSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'taskLog'
		},

		templateString : template	
	});
});
