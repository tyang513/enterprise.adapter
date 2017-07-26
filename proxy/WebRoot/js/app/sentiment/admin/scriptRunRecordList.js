define([ "text!./templates/scriptRunRecordList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/scriptRunRecordSearchBar",
		"text!app/sentiment/admin/config/scriptRunRecordService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.scriptRunRecordList", BaseContentList, {
		// default options
		options : {
			entity : 'scriptRunRecord',
			entityName : '脚本运行步骤记录',
			entityId : 'id',

			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/sentiment/admin/dialog/scriptRunRecordDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/scriptRunRecordForm'
			}
		},

		templateString : template,

//		_initData : function() {
			/*this.treeGrid.treegrid({
				idField : 'id',
				treeField : 'scriptName',
				rownumbers : true,
				pagination : true,
				url : this.options.entity + "/list.do",
				loadFilter : _.bind(function(data, parentId) {
					if (parentId) {
						return data.rows;
					} else {
						return data;
					}
					this.treeGrid.treegrid("pageSize",100);
				},this),
				onBeforeLoad : function(row, param) {
					if (!row) { // load top level rows
						param.id = ""; // set id=0, indicate to load new page
										// rows
					}
				}
			});*/
//		}
		
		_handleViewStepRecord : function(data){
			this.logger.debug();
			var datagrid = {
				"title" : "查看脚本运行步骤记录",
				"url" : "app/sentiment/admin/scriptStepRecordList",
				"runRecord" : data
				};
			app.utils.openTab(this, datagrid);
			
		}
	});
});
