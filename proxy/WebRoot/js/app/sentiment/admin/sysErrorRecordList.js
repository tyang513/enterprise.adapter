define([ "text!./templates/sysErrorRecordList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/sysErrorRecordSearchBar", "text!app/sentiment/admin/config/sysErrorRecordService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.sysErrorRecordList", BaseContentList, {
		// default options
		options : {
			entity : 'sysErrorRecord',
			entityName : 'ETL数据抽取错误记录',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/sentiment/admin/dialog/sysErrorRecordDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/sysErrorRecordForm'
			}
		},

		templateString : template,
		
		_handleViewErrorDetail : function(data){
			this.logger.debug();
			var datagrid = {
				"title" : "查看异常详细信息" + data.row.id,
				"url" : "app/sentiment/admin/sysErrorRecordDetailList",
				"record" : data
			};
			app.utils.openTab(this, datagrid);
		}
	});
});
