define([ "text!./templates/sysUserSearchBar.html", "app/base/BaseSearchBar", "text!app/proxy/admin/config/sysUserSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.sysUserSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'sysUser'
		},

		templateString : template	
	});
});
