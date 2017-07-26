define([ "text!./templates/secondaryIndexErrorRecordList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/secondaryIndexErrorRecordSearchBar", "text!app/sentiment/admin/config/secondaryIndexErrorRecordService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.secondaryIndexErrorRecordList", BaseContentList, {
		// default options
		options : {
			entity : 'secondaryIndexErrorRecord',
			entityName : '二次索引异常',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/sentiment/admin/dialog/secondaryIndexErrorRecordDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/secondaryIndexErrorRecordForm'
			}
		},

		templateString : template
	});
});
