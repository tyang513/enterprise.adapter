define([ "text!./templates/taskInfoList.html", "app/base/BaseContentList", "app/proxy/task/searchbar/taskInfoSearchBar", "text!app/proxy/task/config/taskInfoService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.taskInfoList", BaseContentList, {
		// default options
		options : {
			entity : 'taskInfo',
			entityName : '任务信息',
			entityId : 'id',
			autoRender : false,
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/proxy/task/dialog/taskInfoDialog'
			},
			formConfig : {
				url : 'app/proxy/task/taskInfoForm'
			}
		},
		templateString : template,
		_create : function(){
			this._super();
			this._initData();
		},
		_initData : function(){
			this.searchBar.taskInfoSearchBar("option",{
				condition : this.options.anchor
			});
			this.searchBar.taskInfoSearchBar("render");
			this.dataGrid.datagrid(app.buildServiceData(this.options.entity + "/list.do", {
				queryParams : this.searchBar[this.options.entity + "SearchBar"]('value'), // 查询条件
				onClickRow : _.bind(this._onClickRow, this),
				loadMsg : this.options.loadMsg,
				frozenColumns: [[{field: "fk", checkbox: true,radiobox :true}]],
				onLoadError : _.bind(this._onLoadError, this)
			}));
			app.utils.bindDatagrid(this, this.dataGrid);
		},
		_handleView : function(){
			var gdata = this.dataGrid.datagrid('getSelected');
			if (gdata) {
				this._openTaskInfoForm({
					action :  app.constants.ACT_VIEW ,
					item : gdata
				});
			} else {
				app.messager.warn('未选模板！');
				return;
			}
		},
		_openTaskInfoForm : function(data) {
			if (data != null) {
				app.utils.openTab(this, {
					"title" : "任务号-" + data.item.taskCode,
					"url" : "app/proxy/task/taskInfoForm",
					"taskInfo" : data.item,
					"mode" : "view"
				});
			}
		}
	});
});
