define([ "text!./templates/categoryHierarchy.html", "zf/base/BaseWidget", "text!app/sentiment/sentimentconfig/config/categoryService.json", "ztree"
        ], function(template, BaseWidget, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.categoryHierarchy", BaseWidget, {

		options : {
			entity : 'template',
			entityName : '模板',
			openType : 'dialog',
			autoRender : true,
			enableFilter : true,
		},

		templateString : template,

		_create : function() {
			this._super();
			this._dlg = null;
			this._changes = [];
			this._changesObj = {};
			this.subjectumDefaultDiv.hide();
			this._initData();
			 
		},

		_initData : function() {
			this._setting = {
				edit : {
					enable : true,
					showRemoveBtn : false,
					showRenameBtn : false
				},
				data : {
					simpleData : {
						enable : true,
						pIdKey : 'parentId'
					}
				},
				callback : {
					beforeDrag : _.bind(this._beforeDrag, this),
					beforeDrop : _.bind(this._beforeDrop, this),
					beforeClick : _.bind(this._treeClick, this),
					onRightClick : _.bind(this._onRightClick, this)
				}
			};

			this.menu = $("#menu_" + this.uuid);
			this.menu.menu({
			    onClick: _.bind(this._onClickMenu, this)
			});
			this.refresh();
			
			this.treeDom = $("#ztree_" + this.uuid);
		},
		refresh : function() {
			$.ajax(app.buildServiceData("category/queryCategory.do", {
				data : {},
				context : this,
				success : function(data) {
					$.fn.zTree.init(this.treeDom, this._setting, data);
					
					var zTree = $.fn.zTree.getZTreeObj("ztree_" + this.uuid);
					
					this.treeObj = zTree;
					
					zTree.expandAll(true);
					zTree.setting.edit.drag.prev = false;
					zTree.setting.edit.drag.inner = true;
					zTree.setting.edit.drag.next = false;
				},
				error : function(error) {
					app.messager.error("查询部门层级树异常");
				}
			}));
			this.logger.debug();
			this.dataGrid.treegrid('reload');
			this.dataGrid.treegrid('clearSelections');
		},
		render : function() {
			//this._initSearchBar();
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
		/*_initSearchBar : function() {
			this.searchBar[this.options.entity + "SearchBar"]({
				autoRender : this.options.autoRender,
				enableFilter : this.options.enableFilter
			});
			this.searchBar[this.options.entity + "SearchBar"]('render');
		},*/
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

		_datagridReload : function(treeNode) {
			
			if (treeNode) {
				var parentNode = this.treeObj.getNodeByTId(treeNode.parentTId);
				this._selectedCategory = {
					id : treeNode.id,
					code : treeNode.code,
					name : treeNode.name,
					parentName : parentNode.name,
					parentTId : treeNode.parentTId
				};
			}
			this.subjectumDefaultDiv.show();
		},
		_showTemplate :function(categoryId){
			this.dataGrid.treegrid(app.buildServiceData("template/queryTemplateByCategoryId.do", {
				queryParams :{
					categoryId : categoryId
				},
				//onClickRow : _.bind(this._onClickRow, this),
			}));
			app.utils.bindDatagrid(this, this.dataGrid); 
		},
		_showStmtCrawlKeyWords :function(categoryId){
			this.keyWordsDataGrid.datagrid(app.buildServiceData("stmtCrawlKeyWords/findByCategoryId.do", {
				queryParams :{
					categoryId : categoryId
				},
				//onClickRow : _.bind(this._onClickRow, this)
		}));
			app.utils.bindDatagrid(this, this.keyWordsDataGrid); 
		},
		_treeClick : function(treeId, treeNode, clickFlag) {
			this.categoryId=treeNode.id;
			if(treeNode.parentId===null || treeNode.parentId ===0) {
				this.subjectumDefaultDiv.show();
//				this.name.text(treeNode.name);
//				this.code.text(treeNode.code);
//				this.description.text(treeNode.description);
				this._showTemplate(this.categoryId);
				this._showStmtCrawlKeyWords(this.categoryId);
				return;
			} else {
				this.subjectumDefaultDiv.show();
				this._datagridReload(treeNode);
//				this.name.text(treeNode.name);
//				this.code.text(treeNode.code);
//				this.description.text(treeNode.description);
				app.utils.bindDatagrid(this, this.dataGrid);
				this._showTemplate(this.categoryId);
				this._showStmtCrawlKeyWords(this.categoryId);
		}
			
		},
		/*_onClickRow : function(rowIndex, rowData) {
			this.searchBar[this.options.entity + "SearchBar"]('onGridClickRow', rowIndex, rowData);
		},*/
		_handleSearch : function(data) {
			this.logger.debug();
			this.dataGrid.treegrid('options').queryParams = this.searchBar[this.options.entity + "SearchBar"]('value');
			this.dataGrid.treegrid('load');
			this.dataGrid.treegrid('clearSelections');
		},
		
		_onRightClick : function(event, treeId, treeNode) {
			
			if(treeNode == null) return;
			this.treeObj.selectNode(treeNode); // 选中事件
			event.preventDefault();
			this._rTreeNode = treeNode;
			this.menu.menu('show', {
				left : event.clientX,
				top : event.clientY
			});
			if(treeNode.parentId === null) {
				var itemEl = this.menu.menu('findItem', '新建同级类别');
				this.menu.menu('disableItem',itemEl.target);
				var itemDl = this.menu.menu('findItem', '删除类别');
				this.menu.menu('disableItem',itemDl.target);
			} else {
				var itemEl = this.menu.menu('findItem', '新建同级类别');
				this.menu.menu('enableItem',itemEl.target);
				if(!treeNode.isParent){
					var itemDl = this.menu.menu('findItem', '删除类别');
					this.menu.menu('enableItem',itemDl.target);
				}else{
					var itemDl = this.menu.menu('findItem', '删除类别');
					this.menu.menu('disableItem',itemDl.target);
				}
			}
		},

		_onClickMenu : function(item) {
			this.logger.debug();
			var options = app.utils.parseOptions($(item.target));
			if(options.action === "addCategory") {
				this._onAddCategory(this._rTreeNode, true);
			} else if(options.action === "addSubCategory") {
				this._onAddCategory(this._rTreeNode, false);
			} else if(options.action === "editCategory") {
				this._onEditCategory(this._rTreeNode, false);
			} else if(options.action === "deleteCategory") {
				this._onDeleteCategory(this._rTreeNode, false);
			}
		},
		
		_onAddCategory : function(node, currentLevel){
			this.logger.debug();
			this._openCategoryDialog({
				action : app.constants.ACT_CREATE,
				item : {
					parentId : currentLevel ? node.parentId : node.id
				}
			});
		},
		
		_onEditCategory : function(node, currentLevel){
			this.logger.debug();
			this._openCategoryDialog({
				action : app.constants.ACT_EDIT,
				item : {
					id : node.id,
					code : node.code,
					name : node.name,
					parentId : node.parentId,
					description : node.description,
					ctime : node.ctime,
					mtime : node.mtime
				}
			});
		},
		
		_onDeleteCategory : function(node, currentLevel){
			this.logger.debug();
			if (node == null) {
				app.messager.warn("请选择要删除的商户类别，再操作!");
				return;
			}
			
			var message = "是否要";
			message += "删除商户类别 " + node.name + "？";
			
			var data = {id : node.id};
			this._onChangeData(data,message,"category/deleteAllById.do");
		},
		
		_onChangeData : function(data,message,entityURL) {
			app.messager.confirm("提示", message, _.bind(function(b) {
				if (b) {
					$.ajax(app.buildServiceData(entityURL, {
						data : data,
						context : this,
						success : function(data) {
							if (data) {
								if (data.success) {
									app.messager.info(data.msg + "操作成功");
									this.subjectumDefaultDiv.hide();
									this.refresh();
								} else {
									app.messager.warn(data.msg + "操作失败");
								}
							}
						},
						error : function(error) {
							app.messager.error("修改异常");
						}
					}));
				}
			}, this));
		},
		
		_openCategoryDialog : function(dataInfo) {
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : dataInfo.action === app.constants.ACT_CREATE ? '新增类别' : '修改类别',
				width : 420,
				height : 265,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/sentimentconfig/dialog/categoryDialog',
				modal : true,
				data : dataInfo,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						dlg.dialogExt('destroy');
						if (data) {
							if (data.success) {
								app.messager.info(data.msg + "操作成功");
								this.refresh();
								this._datagridReload(this._selectedCategory);
							} else {
								app.messager.warn(data.msg + "操作失败");
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
		_beforeDrag : function(treeId, treeNodes) {
			this.logger.debug();
			console.dir([ treeId, treeNodes ]);
			for (var i = 0, l = treeNodes.length; i < l; i++) {
				if (treeNodes[i].drag === false) {
					return false;
				}
			}
			return true;
		},

		_beforeDrop : function(treeId, treeNodes, targetNode, moveType) {
			this.logger.debug();
			var canDrop = targetNode ? (targetNode.drop !== false && moveType === 'inner') : true;
			if (targetNode && canDrop && targetNode.groupid) {
				for (var i = 0; i < treeNodes.length; i++) {
					var treeNode = treeNodes[i];
					var changeObj = new Object();
					if (treeNode.groupid) {
						changeObj.groupid = treeNode.groupid;
						changeObj.superiorid = targetNode.groupid;
					} else if (treeNode.code) {
						changeObj.relationid = treeNode.relationid;
						changeObj.code = treeNode.code;
						changeObj.superiorid = targetNode.groupid;
					}
					if (this._changesObj[treeNode.id]) {
						this._changes[this._changesObj[treeNode.id]] = changeObj;
					} else {
						this._changesObj[treeNode.id] = this._changes.length;
						this._changes.push(changeObj);
					}
				}
			}
			return canDrop;
		},
		_destroy : function() {
			delete this._changes;
			this._super();
		},
		
		_onAddTemplate :function(){
			var dlg = $("<div>").dialogExt({
				title : "新建模板",
				width : 750,
				height : 350,
				closed : false,
				autoClose : false,
				data :{
					categoryId :this.categoryId,
					//flag : true
				},
				cache : false,
				className : 'app/sentiment/sentimentconfig/dialog/templateSearchDialog',
				modal : true,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						if (data) {
								dlg.dialogExt('destroy');
								this._showTemplate(this.categoryId);
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
		_onDeleteTemplate :function(){
			 var data = this.dataGrid.treegrid('getSelected');
			   if (data == null) {
			    app.messager.warn("请选择一行数据");
			    return ;
			   }
			app.messager.confirm(app.constants.CONFIRM_TITLE, app.constants.CONFIRM_DELETE + this.options.entityName + "?", _.bind(function(r) {
				if (r) {
					$.ajax(app.buildServiceData("templateCategoryRel/deleteById.do", {
						data : {
							id : data.templateCategoryRelId
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
							this._showTemplate(this.categoryId);
						}
					}));
				}
			}, this));
		}
	});
});
