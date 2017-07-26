define([ "text!./templates/extendedAttriDefinitionList.html", "app/base/BaseContentList", "app/sentiment/sentimentconfig/searchbar/extendedAttriDefinitionSearchBar", "text!app/sentiment/sentimentconfig/config/extendedAttriDefinitionService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.extendedAttriDefinitionList", BaseContentList, {
		// default options
		options : {
			entity : 'extendedAttriDefinition',
			entityName : '扩展属性定义',
			entityId : 'id',
			autoRender : false,
			
			openType : 'dialog',
			dialogConfig : {
				width : 430,
				height : 260,
				className : 'app/sentiment/sentimentconfig/dialog/extendedAttriDefinitionDialog'
			},
			formConfig : {
				url : 'app/sentiment/sentimentconfig/extendedAttriDefinitionForm'
			}
		},

		_buildOpenConfig : function(action) {
			var config = {};
			if (this.options.openType === 'dialog') {
				config = this.options.dialogConfig;
			} else if (this.options.openType === 'form') {
				config = this.options.formConfig;
			}
			
			if(action == app.constants.ACT_VIEW || action == app.constants.ACT_EDIT) {
				config.height = 265;
			}
			
			return config;
		},
		
		templateString : template,
		
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function(){
			this.searchBar.extendedAttriDefinitionSearchBar('option', {
				condition : this.options.anchor,
				searchBar : this.options.anchor,
				enableFilter : false
			});
			
			this.searchBar.extendedAttriDefinitionSearchBar("hideEnable");
			this.dataGrid.datagrid(app.buildServiceData(this.options.entity + "/list.do", {
				queryParams : this.searchBar[this.options.entity + "SearchBar"]('value'), // 查询条件
				onClickRow : _.bind(this._onClickRow, this),
				loadMsg : this.options.loadMsg,
				pageSize : 50,
				frozenColumns: [[{field: "fk", checkbox: true,radiobox :true }]]
			}));
			
			app.utils.bindDatagrid(this, this.dataGrid);
		},
		
		_handleLeadIn : function() {
			this.logger.debug();
			var dlg = $("<div>").dialogExt({
				data : {
					type : "extendedAttriDefinition"
				},
				title : "扩展属性定义导入",
				width : 360,
				height : 300,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/sentimentconfig/dialog/leadInDataDialog',
				modal : true,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						if (data) {
							if (data.success) {
								dlg.dialogExt('destroy');
								app.messager.info(data.msg);
								this.refresh();
							} else {
								app.messager.warn(data.msg);
							}
						}
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt('destroy');
					}
				} ]
			});
		},
		
		_handleLeadOut : function() {
			this.logger.debug();
			var data = this.dataGrid.datagrid("getSelections");
			var id = "";
			if(data != undefined && data.length != 0) {
				id = data[0].id;
				for(var i = 1; i < data.length; i++) {
					id = id + "," + data[i];
				}
			};
			window.location.href = "extendedAttriDefinition/leadOutExtendedAttriDef.do?id=" + id;
		}
	});
});
