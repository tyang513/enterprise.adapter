define([ "text!./templates/taskResponseFileProcessJobInputSearchBar.html", "app/base/BaseSearchBar", "text!app/proxy/task/config/taskResponseFileProcessJobInputSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.taskResponseFileProcessJobInputSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'taskResponseFileProcessJobInput'
		},

		templateString : template	
	});
});
