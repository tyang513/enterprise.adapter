define([ "text!./templates/httpProxyList.html", "app/base/BaseContentList", "app/sentiment/sentimentconfig/searchbar/httpProxySearchBar", "text!app/sentiment/sentimentconfig/config/httpProxyService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.httpProxyList", BaseContentList, {
		// default options
		options : {
			entity : 'httpProxy',
			entityName : 'http代理',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 435,
				height : 280,
				className : 'app/sentiment/sentimentconfig/dialog/httpProxyDialog'
			},
			formConfig : {
				url : 'app/sentiment/sentimentconfig/httpProxyForm'
			}
		},

		templateString : template
	});
});
