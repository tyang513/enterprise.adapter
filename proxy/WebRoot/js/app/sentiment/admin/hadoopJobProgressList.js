define([ "text!./templates/hadoopJobProgressList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/hadoopJobProgressSearchBar", "text!app/sentiment/admin/config/hadoopJobProgressService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.hadoopJobProgressList", BaseContentList, {
		// default options
		options : {
			entity : 'hadoopJobProgress',
			entityName : 'Hadoop输入数据统计',
			entityId : 'id',
			openType : 'dialog',
			dialogConfig : {
				width : 700,
				height : 320,
				className : 'app/sentiment/admin/dialog/hadoopJobProgressDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/hadoopJobProgressForm'
			}
		},
		templateString : template
	});
});
