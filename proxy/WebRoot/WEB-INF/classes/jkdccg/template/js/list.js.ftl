define([ "text!./templates/${listWidgetName}.html", "app/base/BaseContentList", "app/${bizAppPackage}/${entityPackage}/searchbar/${searchbarWidgetName}", "text!app/${bizAppPackage}/${entityPackage}/config/${serviceConfigName}.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.${listWidgetName}", BaseContentList, {
		// default options
		options : {
			entity : '${lowerName}',
			entityName : '${codeName}',
			entityId : '${primaryKey}',
			
			openType : '${entityOpenType}',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/${bizAppPackage}/${entityPackage}/dialog/${dialogWidgetName}'
			},
			formConfig : {
				url : 'app/${bizAppPackage}/${entityPackage}/${formWidgetName}'
			}
		},

		templateString : template
	});
});
