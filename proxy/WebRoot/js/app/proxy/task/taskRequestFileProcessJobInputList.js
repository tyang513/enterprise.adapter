define([ "text!./templates/taskRequestFileProcessJobInputList.html", "app/base/BaseContentList", "app/proxy/task/searchbar/taskRequestFileProcessJobInputSearchBar", "text!app/proxy/task/config/taskRequestFileProcessJobInputService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.taskRequestFileProcessJobInputList", BaseContentList, {
		// default options
		options : {
			entity : 'taskRequestFileProcessJobInput',
			entityName : '请求文件处理任务',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/proxy/task/dialog/taskRequestFileProcessJobInputDialog'
			},
			formConfig : {
				url : 'app/proxy/task/taskRequestFileProcessJobInputForm'
			}
		},

		templateString : template
	});
});
