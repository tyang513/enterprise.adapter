define([ "text!./templates/cloudUserList.html", "app/base/BaseContentList", "app/proxy/admin/searchbar/cloudUserSearchBar", "text!app/proxy/admin/config/cloudUserService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.cloudUserList", BaseContentList, {
		// default options
		options : {
			entity : 'cloudUser',
			entityName : '云用户',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/proxy/admin/dialog/cloudUserDialog'
			},
			formConfig : {
				url : 'app/proxy/admin/cloudUserForm'
			}
		},

		templateString : template
	});
});
