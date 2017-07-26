define([ "text!./templates/taskCheckRequestProcessJobInputSearchBar.html", "app/base/BaseSearchBar", "text!app/proxy/task/config/taskCheckRequestProcessJobInputSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.taskCheckRequestProcessJobInputSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'taskCheckRequestProcessJobInput'
		},

		templateString : template	
	});
});
