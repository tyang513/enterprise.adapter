define([ "text!./templates/taskLogList.html", "app/base/BaseContentList", "app/proxy/task/searchbar/taskLogSearchBar", "text!app/proxy/task/config/taskLogService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.taskLogList", BaseContentList, {
		// default options
		options : {
			entity : 'taskLog',
			entityName : '任务日志',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/proxy/task/dialog/taskLogDialog'
			},
			formConfig : {
				url : 'app/proxy/task/taskLogForm'
			}
		},

		templateString : template
	});
});
