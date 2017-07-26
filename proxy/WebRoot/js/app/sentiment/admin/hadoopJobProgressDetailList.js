define([ "text!./templates/hadoopJobProgressDetailList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/hadoopJobProgressDetailSearchBar", "text!app/sentiment/admin/config/hadoopJobProgressDetailService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.hadoopJobProgressDetailList", BaseContentList, {
		// default options
		options : {
			entity : 'hadoopJobProgressDetail',
			entityName : 'Hadoop输出数据统计',
			entityId : 'id',
			openType : 'dialog',
			dialogConfig : {
				width : 700,
				height : 360,
				className : 'app/sentiment/admin/dialog/hadoopJobProgressDetailDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/hadoopJobProgressDetailForm'
			}
		},
		templateString : template
	});
});
