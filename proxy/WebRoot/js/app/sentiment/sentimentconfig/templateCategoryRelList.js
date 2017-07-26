define([ "text!./templates/templateCategoryRelList.html", "app/base/BaseContentList", "app/sentiment/sentimentconfig/searchbar/templateCategoryRelSearchBar", "text!app/sentiment/sentimentconfig/config/templateCategoryRelService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.templateCategoryRelList", BaseContentList, {
		// default options
		options : {
			entity : 'templateCategoryRel',
			entityName : '',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/sentiment/sentimentconfig/dialog/templateCategoryRelDialog'
			},
			formConfig : {
				url : 'app/sentiment/sentimentconfig/templateCategoryRelForm'
			}
		},

		templateString : template
	});
});
