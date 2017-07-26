define([ "text!./templates/ruleDefinitionList.html", "app/base/BaseContentList", "app/sentiment/sentimentconfig/searchbar/ruleDefinitionSearchBar", "text!app/sentiment/sentimentconfig/config/ruleDefinitionService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.ruleDefinitionList", BaseContentList, {
		// default options
		options : {
			entity : 'ruleDefinition',
			entityName : '规则定义',
			entityId : 'id',
			autoRender : false,
			openType : 'dialog',
			dialogConfig : {
				width : 820,
				height : 555,
				className : 'app/sentiment/sentimentconfig/dialog/ruleDefinitionDialog'
			},
			formConfig : {
				url : 'app/sentiment/sentimentconfig/ruleDefinitionForm'
			}
		},

		templateString : template,
		
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function(){
			console.dir(["1",this]);
			this.searchBar.ruleDefinitionSearchBar('option', {
				searchBar : this.options.anchor,
				enableFilter : false
			});
			this.searchBar.ruleDefinitionSearchBar("hideEnable");
			this.dataGrid.datagrid(app.buildServiceData(this.options.entity + "/list.do", {
				queryParams : this.searchBar[this.options.entity + "SearchBar"]('value'), // 查询条件
				onClickRow : _.bind(this._onClickRow, this),
				loadMsg : this.options.loadMsg,
				frozenColumns: [[{field: "fk", checkbox: true,radiobox :true }]]
			}));
			
			app.utils.bindDatagrid(this, this.dataGrid);
		},

		// ---------- event handler -------------
		_doAction : function(event, data) {
			var handlerName = "_handle" + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},

		_handleDelete : function(data) {
			this.logger.debug();
			var status = data.row.status;
			if (status == app.constants.LOGIC_UNENABLE) {
				app.messager.confirm("提示","确定要删除所选规则定义？",_.bind(function(r){
					if(r){
						$.ajax(app.buildServiceData("deleteRuleDefinition", {
							data : {
								id : data.row.id
							},
							context : this,
							success : function(data) {
								if(data.success){
									app.messager.info('所选规则定义已删除！');
									this.refresh();
								}else {
									app.messager.warn(data.result);
								}
							}
						}));
					}},this))
				
			} else {
				app.messager.warn("只有未启用的规则定义才可以删除");
				this.refresh();
			}
			
		},
		/*
		 * Status
		 * :0，未启用
			1，启用
			-1，禁用
		 */
		_handleEnable : function(data) {
			this.logger.debug();
			if(data.row.status == app.constants.LOGIC_ENABLE){
				app.messager.confirm("提示","是否确定禁用所选规则定义？",_.bind(function(r){
					if(r){
						$.ajax(app.buildServiceData("updateRuleDefinition", {
							data : {
								id : data.row.id,
								status : data.row.status
							},
							context : this,
							success : function(data) {
								if(data.success){
									app.messager.info('所选规则定义已禁用！');
									this.refresh();
								}else {
									app.messager.warn(data.msg);
								}
							}
						}));
					}},this))
			} else {
				app.messager.confirm("提示","是否确定启用所选规则定义？",_.bind(function(r){
					if(r){
						$.ajax(app.buildServiceData("updateRuleDefinition", {
							data : {
								id : data.row.id,
								status : data.row.status
							},
							context : this,
							success : function(data) {
								if(data.success){
									app.messager.info('所选规则定义已启用！');
									this.refresh();
								}else {
									app.messager.warn(data.msg);
								}
							}
						}));
					}},this))
			}
		},

		_handleCheckRuleConfigDefinition : function(data){
			this._publish("app/openTab", {
				"title" : "规则定义-" + data.row.name,
				"url" : "app/sentiment/sentimentconfig/ruleConfigDefinitionList",
				"ruleDefId" : data.row.id
			});

		},
		
		_handleLeadIn : function() {
			this.logger.debug();
			var dlg = $("<div>").dialogExt({
				data : {
					type : "ruleDefinition"
				},
				title : "规则定义导入",
				width : 390,
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
			window.location.href = "ruleDefinition/leadOutRuleDefinition.do?id=" + id;
		},
		
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');
		}

	});
});
