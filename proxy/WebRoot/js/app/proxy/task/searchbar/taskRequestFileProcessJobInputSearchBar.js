define([ "text!./templates/taskRequestFileProcessJobInputSearchBar.html", "app/base/BaseSearchBar", "text!app/proxy/task/config/taskRequestFileProcessJobInputSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.taskRequestFileProcessJobInputSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'taskRequestFileProcessJobInput'
		},

		templateString : template	
	});
});
