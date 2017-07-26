define([ "text!./templates/scriptStepRecordList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/scriptStepRecordSearchBar", "text!app/sentiment/admin/config/scriptStepRecordService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.scriptStepRecordList", BaseContentList, {
		// default options
		options : {
			entity : 'scriptStepRecord',
			entityName : '脚本运行步骤记录',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/sentiment/admin/dialog/scriptStepRecordDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/scriptStepRecordForm'
			}
		},

		templateString : template,
		
		_initData : function(){
			this.logger.debug();
			
			var runRecord = (this.options.runRecord) ? this.options.runRecord.row : undefined;
			
			if(runRecord){
				this.searchBar.scriptStepRecordSearchBar("option",{
					scriptRunId : runRecord.id
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
