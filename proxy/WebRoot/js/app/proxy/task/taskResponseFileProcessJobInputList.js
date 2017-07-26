define([ "text!./templates/taskResponseFileProcessJobInputList.html", "app/base/BaseContentList", "app/proxy/task/searchbar/taskResponseFileProcessJobInputSearchBar", "text!app/proxy/task/config/taskResponseFileProcessJobInputService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.taskResponseFileProcessJobInputList", BaseContentList, {
		// default options
		options : {
			entity : 'taskResponseFileProcessJobInput',
			entityName : '响应文件处理任务',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/proxy/task/dialog/taskResponseFileProcessJobInputDialog'
			},
			formConfig : {
				url : 'app/proxy/task/taskResponseFileProcessJobInputForm'
			}
		},

		templateString : template
	});
});
