define([ "text!./templates/regionList.html", "app/base/BaseContentList", "app/sentiment/sentimentconfig/searchbar/regionSearchBar", "text!app/sentiment/sentimentconfig/config/regionService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.regionList", BaseContentList, {
		// default options
		options : {
			entity : 'region',
			entityName : '地域',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 435,
				height : 230,
				className : 'app/sentiment/sentimentconfig/dialog/regionDialog'
			},
			formConfig : {
				url : 'app/sentiment/sentimentconfig/regionForm'
			},
			autoRender : true,
			enableFilter : true,
			loadMsg : "数据加载中请稍后……"
		},
		templateString : template,
		_create : function() {
			this._super();
			this._dlg = null;
			if (this.options.autoRender) {
				this._initData();
			}
		},
		refresh : function() {
			this.logger.debug();
			this.dataGrid.treegrid('reload');
			this.dataGrid.treegrid('clearSelections');
		},

		render : function() {
			this._initSearchBar();
			this._initData();
		},

		_buildOpenConfig : function() {
			var config = {};
			if (this.options.openType === 'dialog') {
				config = this.options.dialogConfig;
			} else if (this.options.openType === 'form') {
				config = this.options.formConfig;
			}
			return config;
		},

		// ---------- event handler -------------
		_doAction : function(event, data) {
			this.logger.debug();
			console.dir([ event, data ]);
			var handlerName = app.constants.ACTION_HANDLER_PREFIX + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},

		_onClickRow : function(rowIndex, rowData) {
			this.searchBar[this.options.entity + "SearchBar"]('onGridClickRow', rowIndex, rowData);
		},
		// ---------------functions------------------
		_initSearchBar : function() {
			this.searchBar[this.options.entity + "SearchBar"]({
				autoRender : this.options.autoRender,
				enableFilter : this.options.enableFilter
			});
			this.searchBar[this.options.entity + "SearchBar"]('render');
		},

		_initData : function() {
			this.logger.debug();
			this.dataGrid.treegrid(app.buildServiceData("region/treeList.do", {
				queryParams : this.searchBar[this.options.entity + "SearchBar"]('value'), // 查询条件
				onClickRow : _.bind(this._onClickRow, this),
				loadMsg : this.options.loadMsg,
//				onContextMenu: this.onContextMenu
			}));
			app.utils.bindDatagrid(this, this.dataGrid);
		},

		_handleCreate : function(data) {
			var obj={};
			if(data.row){
				var entity=data.row;
				obj.parentId=entity.id;
			}
				//得到根节点

				$.ajax(app.buildServiceData("region/getRootNode.do", {
					data : obj ,
					context : this,
					async:false,
					success : function(data) {
						obj.parentId=data.id;
					},
					error : function(error) {
						app.messager.error("获取根节点异常");
					}
				}));
			
			this.logger.debug();
			this._openEntity({
				item:obj,
				action : app.constants.ACT_CREATE
			});
		},

		_handleView : function(data) {
			this.logger.debug();
			if(this.dataGrid.datagrid("getChecked").length < 1) {
				app.messager.warn("只能选择一条数据");
				return;
			}
			this._openEntity({
				item : data.row,
				action : app.constants.ACT_VIEW
			});
		},

		_handleDelete : function(data) {
			
			var row = this.dataGrid.datagrid("getSelected");
			var ids = [];
			ids.push(row[this.options.entityId]);
			//如果有子节点不能删除
			if(row.children!=null&&row.children.length>0){
				app.messager.warn('该节点存在子节点不能删除，请先删除子节点！');
				return;
			}
			//如果有子节点不能删除
			if(row&&row.resourceCode=='root'){
				app.messager.warn('根节点不能删除！');
				return;
			}
			this.logger.debug();
			app.messager.confirm(app.constants.CONFIRM_TITLE, app.constants.CONFIRM_DELETE + this.options.entityName + "?", _.bind(function(r) {
				if (r) {
					$.ajax(app.buildServiceData(this.options.entity + "/deleteById.do", {
						data : {
							id : ids.toString() //data.row[this.options.entityId]
						},
						context : this,
						success : function(data) {
							if (data) {
								var action = "删除操作执行";
								if (data.success) {
									app.messager.info(data.msg || action + "成功!");
								} else {
									app.messager.warn(data.msg || action + "失败!");
								}
							}
							this.refresh();
						}
					}));
				}
			}, this));
		},

		_handleSearch : function(data) {
			this.logger.debug();
			this.dataGrid.treegrid('options').queryParams = this.searchBar[this.options.entity + "SearchBar"]('value');
			this.dataGrid.treegrid('load');
			this.dataGrid.treegrid('clearSelections');
		},
		
		_handleCollapse:function(){
			this.dataGrid.treegrid('collapseAll')
		},

		_getOpenEntityTitle : function(data) {
			var title = this.options.entityName;
			if (data.action === app.constants.ACT_CREATE) {
				title = '新建' + this.options.entityName;
			} else if (data.action === app.constants.ACT_VIEW) {
				title = '查看' + this.options.entityName;
			}
			return title;
		},

		_openEntity : function(data) {
			if (this.options.openType === 'dialog') {
				this._openDialog(data);
			} else if (this.options.openType === 'form') {
				this._openForm(data);
			} else {
				this.logger.error("未知实体对象的打开类型:" + this.options.openType);
			}
		},

		_openDialog : function(data) {
			this.logger.debug();
			var title = this._getOpenEntityTitle(data);
			var buttons = [ {
				text : app.constants.BTN_OK_LABEL,
				eventId : app.constants.BTN_OK,
				handler : _.bind(function(data) {
					this._dlg.dialogExt('destroy');
					this._dlg = null;
					this.refresh();
				}, this)
			}, {
				text : app.constants.BTN_CANCEL_LABEL,
				eventId : app.constants.BTN_CANCEL,
				handler : _.bind(function() {
					this._dlg.dialogExt('destroy');
					this._dlg = null;
				}, this)
			} ];

			if (data.action === app.constants.ACT_VIEW) {
				buttons = buttons.splice(1, 1);
			}

			var dialogInfo = {
				title : title,
				closed : false,
				autoClose : false,
				cache : false,
				modal : true,
				data : data,
				buttons : buttons
			};

			dialogInfo = $.extend(dialogInfo, this._buildOpenConfig(data.action));
			// 打开对话框
			this._dlg = $("<div>").dialogExt(dialogInfo);
		},

		_openForm : function(data) {
			this.logger.debug();

			data.title = this._getOpenEntityTitle(data);
			data = $.extend(data, this._buildOpenConfig(data.action));

			this._publish("app/openTab", data);
		},

		_destroy : function() {
			this._dlg = null;
			this._super();
		},
		
		_handleDisable : function(data){			
			this.logger.debug();
			var status;
			if(data.row.status==app.constants.SENTIMENT_ENABLE ){
				status = app.constants.SENTIMENT_DISABLE;
				app.messager.confirm("提示","是否将地域的状态改为禁用？",_.bind(function(r){
					if(r){
						this._enableOrDisableRegion(status,data);
					}},this));
				
			}else if(data.row.status==app.constants.SENTIMENT_DISABLE){
				app.messager.warn("该地域已禁用");
			}else if(data.row.status == app.constants.SENTIMENT_UNENABLE){
				app.messager.warn("该地域的状态为未启用，不能改为禁用");
			}
		},
        _handleEnable : function(data){
        	this.logger.debug();
        	var status;
			if(	data.row.status==app.constants.SENTIMENT_UNENABLE || data.row.status==app.constants.SENTIMENT_DISABLE ){
				status =app.constants.SENTIMENT_ENABLE;
				this._enableOrDisableRegion(status,data);
			}else{
				app.messager.warn("该地域已启用");
			}
		},
		_enableOrDisableRegion :function(status,data){
			$.ajax(app.buildServiceData("region/enableOrDisableRegion.do", {
				data : {
					id: data.row.id,
					status: status
				},
				context : this,
				global : true,
				success : function(data) {
					this._initData();
				}
			}));
		}
		
	});
});
