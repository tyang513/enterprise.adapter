define([ "text!./templates/importFileJobInputList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/importFileJobInputSearchBar", "text!app/sentiment/admin/config/importFileJobInputService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.importFileJobInputList", BaseContentList, {
		// default options
		options : {
			entity : 'importFileJobInput',
			entityName : '文件导入',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/sentiment/admin/dialog/importFileJobInputDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/importFileJobInputForm'
			}
		},

		templateString : template
	});
});
