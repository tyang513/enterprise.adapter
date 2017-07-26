define([ "text!./templates/taskCheckRequestProcessJobInputList.html", "app/base/BaseContentList", "app/proxy/task/searchbar/taskCheckRequestProcessJobInputSearchBar", "text!app/proxy/task/config/taskCheckRequestProcessJobInputService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.taskCheckRequestProcessJobInputList", BaseContentList, {
		// default options
		options : {
			entity : 'taskCheckRequestProcessJobInput',
			entityName : '请求文件检查任务',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/proxy/task/dialog/taskCheckRequestProcessJobInputDialog'
			},
			formConfig : {
				url : 'app/proxy/task/taskCheckRequestProcessJobInputForm'
			}
		},

		templateString : template
	});
});
