define([ "text!./templates/secondaryIndexErrorRecordDetailList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/secondaryIndexErrorRecordDetailSearchBar", "text!app/sentiment/admin/config/secondaryIndexErrorRecordDetailService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.secondaryIndexErrorRecordDetailList", BaseContentList, {
		// default options
		options : {
			entity : 'secondaryIndexErrorRecordDetail',
			entityName : '二次索引异常明细',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 500,
				className : 'app/sentiment/admin/dialog/secondaryIndexErrorRecordDetailDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/secondaryIndexErrorRecordDetailForm'
			}
		},

		templateString : template
	});
});
