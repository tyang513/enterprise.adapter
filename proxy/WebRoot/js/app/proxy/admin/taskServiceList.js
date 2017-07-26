define([ "text!./templates/taskServiceList.html", "app/base/BaseContentList", "app/proxy/admin/searchbar/taskServiceSearchBar", "text!app/proxy/admin/config/taskServiceService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.taskServiceList", BaseContentList, {
		// default options
		options : {
			entity : 'taskService',
			entityName : '服务注册',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/proxy/admin/dialog/taskServiceDialog'
			},
			formConfig : {
				url : 'app/proxy/admin/taskServiceForm'
			}
		},

		templateString : template
	});
});
