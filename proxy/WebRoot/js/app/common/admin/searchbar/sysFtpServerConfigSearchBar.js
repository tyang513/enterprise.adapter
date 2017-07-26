define([ "text!./templates/sysFtpServerConfigSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.sysFtpServerConfigSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'sysFtpServerConfig'
		},

		templateString : template	
	});
});
