define([ "text!./templates/ruleConfigDefinitionList.html", "app/base/BaseContentList", "app/sentiment/sentimentconfig/searchbar/ruleConfigDefinitionSearchBar", "text!app/sentiment/sentimentconfig/config/ruleConfigDefinitionService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.ruleConfigDefinitionList", BaseContentList, {
		// default options
		options : {
			entity : 'ruleConfigDefinition',
			entityName : '规则配置定义',
			entityId : 'id',
			openType : 'dialog',
			dialogConfig : {
				width : 435,
				height : 330,
				className : 'app/sentiment/sentimentconfig/dialog/ruleConfigDefinitionDialog'
			},
			formConfig : {
				url : 'app/sentiment/sentimentconfig/ruleConfigDefinitionForm'
			}
		},

		templateString : template,
		_create : function() {
			this._super();
			this._initData();
		},
		_initData : function() {
			this.logger.debug();
			this.searchBar.ruleConfigDefinitionSearchBar('option', {
				searchBar : this.options.anchor,
				enableFilter : false
			});
			this.searchBar.ruleConfigDefinitionSearchBar("hideEnable");
			this.dataGrid.datagrid(app.buildServiceData("getRuleConfigDefinitionByRuleId", {
				queryParams : {'ruleDefId' : this.options.ruleDefId}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				frozenColumns: [[{field: "fk", checkbox: true,radiobox :true }]]
			}));
		},
		// ---------- event handler -------------
		_doAction : function(event, data) {
			var handlerName = "_handle" + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},
		
		/**
		 * 新增规则配置定义
		 */

		_handleCreate : function(data) {
			this.logger.debug();
			this._openRuleConfigDialog(this.options.ruleDefId, null, "add");
		},
		
		_handleEdit : function(data) {
			this.logger.debug();
			var rowdata = data.row;
			this._openRuleConfigDialog(this.options.ruleDefId, rowdata, "edit");
		},

		_openRuleConfigDialog : function(ruleDefId, rowdata, mode) {
			var title = "";
			if (mode == "add") {
				title = "新增规则配置定义";
			} else {
				title = "编辑规则配置定义";
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 435,
				height : 330,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/sentimentconfig/dialog/ruleConfigDefinitionDialog',
				modal : true,
				data : {
					data : rowdata,
					mode : mode,
					ruleDefId : ruleDefId
				},
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						dlg.dialogExt('destroy');
						this.refresh();

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
		_handleSearch : function(data) {
			this.logger.debug();
			var queryParams = this.searchBar.ruleConfigDefinitionSearchBar('value');
			queryParams.ruleDefId = this.options.ruleDefId;
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.refresh();
		},
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');
		}
		
		
	});
});
