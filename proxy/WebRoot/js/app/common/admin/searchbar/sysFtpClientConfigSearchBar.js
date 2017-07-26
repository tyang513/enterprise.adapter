define([ "text!./templates/sysFtpClientConfigSearchBar.html", "app/base/BaseSearchBar"], function(template, BaseSearchBar) {
	return $.widget("app.sysFtpClientConfigSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'sysFtpClientConfig'
		},

		templateString : template	
	});
});
