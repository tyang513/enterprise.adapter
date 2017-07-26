define([ "text!./templates/openApproveTemplateDialog.html", "zf/base/BaseWidget", "easyuiplugins/jquery.edatagrid" ], function(template, BaseWidget) {
	return $.widget("app.openApproveTemplateDialog", BaseWidget, {
		// default options
		options : {},

		templateString : template,

		_create : function() {
			this._super();
			this._initCombobox();
			this._initdatas();
		},

		_onAddRow : function(event) {
			this.logger.debug();
			this.approvalTemplate_TemplateTask.edatagrid('addRow');
		},
		
		_onSaveRow : function(event) {
			this.logger.debug();
			this.approvalTemplate_TemplateTask.edatagrid('saveRow');
			var options = app.utils.parseOptions($(event));//这个event啥都没
			var datas = this.approvalTemplate_TemplateTask.datagrid('getRows');
			if (datas == null || datas.length == 0) {
				app.messager.warn('请添加审批任务配置!');
				return false;
			}
			return true;
		},
		
		_onCancelRow : function(event) {
			this.logger.debug();
			this.approvalTemplate_TemplateTask.edatagrid('cancelRow');
		},
		
		_onDestroyRow : function(event) {
			this.logger.debug();
			app.utils.gridEditRemove(this.approvalTemplate_TemplateTask);
			//this.approvalTemplate_TemplateTask.edatagrid('destroyRow');
		},
		 _approverNameformater : function(value,row,index){

			 //var result = "<a onclick='alert(1)'>aaa</a>"; 能响应onclick事件
			// var result = "<a onclick='_chooseUser()'>aaa</a>";没响应  onclick='_chooseUser' 也没响应
			// var result='<span>批次信息计算发生错误.</span><a href="javascript:void(0);" data-options="data:\''+data.sheetId+'\'" data-attach-event="click:_viewBatchError" >查看</a><br/>';	
			// var result = '<a href="javascript:void(0);" data-attach-event="click:_chooseUser()" >aaa</a>';
			 
			 var result = "<a href='javascript:void(0);' class='easyui-linkbutton' data-options=\"plain:true,index:"
				+ index + "\" onClick=_chooseUser();>"+(!value||value==null?"<font color='blue'>单击选择审批人</font>":row.approvername)+"</a>";

			 return app.utils.bind(result, this) ;
			 //return "<a style='cursor:pointer;text-decoration:underline;' onclick=\"if($.data(this.approvalTemplate_TemplateTask[0], 'edatagrid').options.editIndex=="+index+"){$.data(this.approvalTemplate_TemplateTask[0], 'edatagrid').options.userflag='T';this.templateTask_user_div.dialog('open');this.approvalTemplate_TemplateTask.datagrid('selectRow',"+index+");}\""+">"+(!value||value==null?"<font color='blue'>单击选择审批人</font>":row.approvername)+"</a>";

		 },
		 _chooseUser : function(event) {
			 var options = app.utils.parseOptions($(event));
				var index = options.index;
			
				if($.data((this.approvalTemplate_TemplateTask)[0], 'edatagrid').options.editIndex==index){
					$.data((this.approvalTemplate_TemplateTask)[0], 'edatagrid').options.userflag='T';
					this.approvalTemplate_TemplateTask.datagrid('selectRow',index);
					var dlg = $("<div>").dialogExt({
						title : '选择用户',
						width : 450,
						height : 300,
						closed : false,
						autoClose : false,
						cache : false,
						className : 'app/common/admin/dialog/userSearchDialog',
						modal : true,
						buttons : [ {
							text : '确认',
							eventId : "ok",
							handler:_.bind(function(data){//回调函数返回的data
								dlg.dialogExt('destroy');
								console.dir([data]);
								this.approvalTemplate_TemplateTask.datagrid('getSelected').approverumid=data.umid;
								this.approvalTemplate_TemplateTask.datagrid('getSelected').approvername=data.username;
								//this.approvalTemplate_TemplateTask.edatagrid('saveRow');
								var $tr = this.approvalTemplate_TemplateTask.parent().find(".datagrid-row-editing").last();
	      		            	$tr.find("a").text(data.username);
							},this)
						}, {
							text : '取消',
							eventId : "cancel",
							handler : function() {
								dlg.dialogExt('destroy');
							}
						} ]
						
					});
				}
				
//				return "<a style='cursor:pointer;text-decoration:underline;' onclick=\"" +
//						"if($.data($('#dg_TemplateTask')[0], 'edatagrid').options.editIndex=="+index+")" +
//						"{$.data($('#dg_TemplateTask')[0], 'edatagrid').options.userflag='T';" +
//					"$('#templateTask_user_div').dialog('open');" +
//					"$('#dg_TemplateTask').datagrid('selectRow',"+index+");}" +
//					"\""+">"+(!value||value==null?"<font color='blue'>单击选择审批人</font>":row.approvername)+"</a>";
//				
		 },
		_initdatas : function() {
			this.approvalTemplate_TemplateTask.edatagrid({
				url : '',
				saveUrl : '',
				updateUrl : '',
				destroyUrl : '',
				onCancelEdit : function(index, row) {
				},
				// 表格表单验证
//				onBeforeSave : _.bind(function(index) {
//					if (this.approvalTemplate_TemplateTask.datagrid('getRows').length > 0) {
//						this.approvalTemplate_TemplateTask.datagrid('selectRow', index);
//						var rowData = this.approvalTemplate_TemplateTask.datagrid('getSelected');
//						if (!rowData.approvername == null || rowData.approvername == "") {
//							alert("请选择被审批人。");
//							return false;
//						}
//					}
//					return true;
//				}, this),
				// 保存一行数据之后触发
				onAfterEdit : function(rowIndex, rowData, changes) {
				}
			});
			if (this.options.mode == "edit") {
				 this.approvalTemplateForm_id.val(this.options.data.id);
				 this.approvalTemplateForm_code.val(this.options.data.code);
				this.approvalTemplateForm_name.val(this.options.data.name);
				//this.approvalTemplateForm_system.combobox('setValue', this.options.data.systemName);
				//this.approvalTemplateForm_status.combobox('setValue', this.options.data.status);
				this.approvalTemplateForm_description.val(this.options.data.description);
				var queryParams = {
					'templateid' : this.options.data.id
				};

				this.approvalTemplate_TemplateTask.datagrid(app.buildServiceData("queryApprovalChain", {
					queryParams : queryParams
				}));
			}
			
		},
		_initCombobox : function() {
			this.logger.debug();
			
			app.utils.initComboboxByDict(this.approvalTemplateForm_status,'oaTemplateStatus', {
				value : this.options.data ? this.options.data.status : undefined,
				isCheckAll : false,
				cached : true
			});
			this.approvalTemplateForm_system.combobox({
				url : 'businessSystem/findSubSystem.do',
				panelHeight : 'auto',
				valueField : 'code',
				textField : 'name',
				onLoadSuccess : _.bind(function(){
					this.approvalTemplateForm_system.combobox("setValue",this.options.data ? this.options.data.systemcode : undefined);
				},this)
			});

		},
		_submitApprovalTemplateForm : function(callback){
			
			//获取所有表格数据 
			var datas = this.approvalTemplate_TemplateTask.datagrid('getRows');
			if (datas == null || datas.length == 0) {
				app.messager.warn('请添加审批任务配置!');
				return;
			}
			for (var i=0;i<datas.length;i++){
				delete datas[i]['isNewRecord'];
				if(datas[i]['taskcode']==null){
					datas.splice(i,1);
				}
			}
			var url="";
			if(this.options.mode === "edit"){
				url="approvalTemplate/updateApprovalTemplate.do";
			}else{
				url="approvalTemplate/saveApprovalTemplate.do";
			}
			var str = JSON.stringify(datas);
			this.approvalChainJsonData.val(str);
			this.approvalTemplateForm.form('submit',{
				url:url,
				dataType:'json',
				beforeSubmit : _.bind(function() {
					return this.approvalTemplateForm.form('validate');
				}, this),
		    	success: function(data){  
		    		var obj = eval('(' + data + ')');
					if (obj.statusCode == 200) {
						app.messager.info(obj.message);
						
					}else{
						app.messager.warn(obj.message);
					}
					callback(data);
		    	}   
			});
		
		},
		_confirmInput : function() {
			var flag = true;
				flag = this._onSaveRow();
			return flag;
		},
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				if (this._confirmInput()) {
					if(this.approvalTemplateForm_status.combobox("getValue")=="3"||this.approvalTemplateForm_status.combobox("getValue")==3){	
						  app.messager.confirm("确认信息","审批模版状态修改为停用,会将销售人员的相关审批任务重置,是否确定修改?",_.bind(function(r){
								if(r){
									this._submitApprovalTemplateForm(callback);
								}
							},this));
						}else{
							this._submitApprovalTemplateForm(callback);
					}
				}
			} else if (eventId === "cancel") {
				if (callback) {
					callback();
				}
			}
		}

	});
});
