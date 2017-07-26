define([ "text!./templates/sysUserList.html", "app/base/BaseContentList", "app/proxy/admin/searchbar/sysUserSearchBar", "text!app/proxy/admin/config/sysUserService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.sysUserList", BaseContentList, {
		// default options
		options : {
			entity : 'sysUser',
			entityName : '用户',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/proxy/admin/dialog/sysUserDialog'
			},
			formConfig : {
				url : 'app/proxy/admin/sysUserForm'
			}
		},

		templateString : template
	});
});
