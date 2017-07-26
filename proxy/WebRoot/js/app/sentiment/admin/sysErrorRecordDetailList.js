define([ "text!./templates/sysErrorRecordDetailList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/sysErrorRecordDetailSearchBar", "text!app/sentiment/admin/config/sysErrorRecordDetailService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.sysErrorRecordDetailList", BaseContentList, {
		// default options
		options : {
			entity : 'sysErrorRecordDetail',
			entityName : 'Hadoop执行异常信息',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/sentiment/admin/dialog/sysErrorRecordDetailDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/sysErrorRecordDetailForm'
			}
		},

		templateString : template,
		
		_initData : function(){
			this.logger.debug();
			
			var runRecord = (this.options.record) ? this.options.record.row : undefined;
			
			if(runRecord){
				this.searchBar.sysErrorRecordDetailSearchBar("option",{
					jobId : runRecord.id
				});
			}

			this.dataGrid.datagrid(app.buildServiceData(this.options.entity + "/list.do", {
				queryParams : this.searchBar[this.options.entity + "SearchBar"]('value'), // 查询条件
				onClickRow : _.bind(this._onClickRow, this),
				loadMsg : this.options.loadMsg,
				frozenColumns: [[{field: "fk", checkbox: true,radiobox :true}]]
			}));
		}
	});
});
