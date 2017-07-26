define([ "text!./templates/partnerList.html", "app/base/BaseContentList", "app/proxy/admin/searchbar/partnerSearchBar", "text!app/proxy/admin/config/partnerService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.partnerList", BaseContentList, {
		// default options
		options : {
			entity : 'partner',
			entityName : '合作伙伴',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/proxy/admin/dialog/partnerDialog'
			},
			formConfig : {
				url : 'app/proxy/admin/partnerForm'
			}
		},

		templateString : template
	});
});
