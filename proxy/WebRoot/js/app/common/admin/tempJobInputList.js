define([ "text!./templates/tempJobInputList.html", "app/base/BaseContentList", "app/common/admin/searchbar/tempJobInputSearchBar", "text!app/common/admin/config/tempJobInputService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.tempJobInputList", BaseContentList, {
		// default options
		options : {
			entity : 'tempJobInput',
			entityName : '临时任务',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 350,
				height : 320,
				className : 'app/common/admin/dialog/tempJobInputDialog'
			},
			formConfig : {
				url : 'app/common/admin/tempJobInputForm'
			}
		},

		templateString : template
	});
});
