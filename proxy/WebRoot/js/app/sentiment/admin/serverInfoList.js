define([ "text!./templates/serverInfoList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/serverInfoSearchBar", "text!app/sentiment/admin/config/serverInfoService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.serverInfoList", BaseContentList, {
		// default options
		options : {
			entity : 'serverInfo',
			entityName : '服务器信息',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/sentiment/admin/dialog/serverInfoDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/serverInfoForm'
			}
		},

		templateString : template
	});
});
