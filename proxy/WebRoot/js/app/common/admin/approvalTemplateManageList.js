define([ "text!./templates/approvalTemplateManageList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/approvalTemplateManageListSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.approvalTemplateManageList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this.dicts = {};
			this._initData();
		},
		_initData : function() {
			this.logger.debug();

			var dicLoaded = function(data) {
				this.dicts = data;				
				this.dataGrid.datagrid(app.buildServiceData("queryApprovalTemplate", {
					queryParams : {}, // 查询条件
					loadMsg : '数据加载中请稍后……'
				}));
			};
			// 缓存字典数据
			app.utils.getDicts('oaTemplateStatus', _.bind(dicLoaded, this));
		},
		
		_getLabel : function(dictName, value){
			var label = null;
			var dict = this.dicts[dictName];
				for(var i=0; i<dict.length; i++){
					if(parseInt(dict[i].dicitemkey) === parseInt(value)){
						label = dict[i].dicitemvalue;
						break;
					}
				}
			return label;
		},

		_formaterStatus : function(value, row, index) {
			return this._getLabel('oaTemplateStatus', value);
		},
		_handleAdd : function(data) {
			this.logger.debug();
			this._openApproveTemplateDialog(null,"add");
		},
		_handleEdit : function(data) {
			this.logger.debug();
			var rowdata = data.row;
			this._openApproveTemplateDialog(rowdata,"edit");
		},
		_openApproveTemplateDialog : function(rowdata,mode) {
			var title = "";
			if(mode=="add"){
				title = "新增OA审批模板";
			}else{
				title = "修改OA审批模板";
				if(rowdata.status==3){
					app.messager.alert('', '已停用审批模版不能再次修改!');
					return;
				}
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 650,
				height : 400,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/openApproveTemplateDialog',
				modal : true,
				data : {
					data : rowdata,
					mode : mode
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
		
		// ---------- event handler -------------
		_doAction : function(event, data) {
			this.logger.debug();
			console.dir([ event, data ]);
			var handlerName = "_handle" + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');
		},
		_handleSearch : function(data) {
			this.logger.debug();
			var queryParams = this.searchBar.approvalTemplateManageListSearchBar('value');
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('load');
		},
		_handleStart : function(data) {
			this.logger.debug();
			app.messager.confirm("确认信息","确定启用模版?",_.bind(function (r){
				if(r){
					$.ajax(app.buildServiceData("updateApprovalTemplate", {
						data : {
							id : data.row.id,
							status : 2
						},
						type : 'POST',
						context : this,
						success : function(data) {
							if (data.statusCode == 200) {
								app.messager.info('启用模版成功！');
								this.refresh();
							}else{
								app.messager.warn('启用模版失败！');
							}
						}
					}));
				}
			},this));
		},
		_handleStop : function(data) {
			this.logger.debug();
			app.messager.confirm("确认信息","确定停用模版?",_.bind(function (r){
				if(r){
					$.ajax(app.buildServiceData("updateApprovalTemplate", {
						data : {
							id : data.row.id,
							status : 3,
							code : data.row.code
						},
						type : 'POST',
						context : this,
						success : function(data) {
							if (data.statusCode == 200) {
								app.messager.info('停用模版成功！');
								this.refresh();
							}else{
								app.messager.warn('停用模版失败！');
							}
						}
					}));
				}
			},this));
		},
		_handleDelete : function(data) {
			this.logger.debug();
			if(data.row.status!=1){
				app.messager.alert("",'只能删除未生效的审批链!')
			}else{
				app.messager.confirm("确认信息","确定删除模版?",_.bind(function (r){
					if(r){
						$.ajax(app.buildServiceData("deleteApprovalTemplate", {
							data : {
								id : data.row.id
							},
							type : 'POST',
							context : this,
							success : function(data) {
								if (data.statusCode == 200) {
									app.messager.info(data.message);
									this.refresh();
								}else{
									app.messager.warn(data.message);
								}
							}
						}));
					}
				},this));
			}
			
		}
	});
});
