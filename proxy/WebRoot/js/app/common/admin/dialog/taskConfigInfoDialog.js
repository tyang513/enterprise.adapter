define([ "text!./templates/taskConfigInfoDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.taskConfigInfoDialog", BaseWidget, {
		// default options
		options : {
			ajaxLock : true,
			lockMsg : '数据处理中，请稍候  ...'
		},

		templateString : template,

		_create : function() {
			this._super();
			this.initLoad();
		},
		initLoad : function(){
			this._initCombobox();
			this._initdatas();
		},
		_initdatas : function() {
			if(this.options.mode =="edit"){
				this.id.val(this.options.data.id);
				this.ipt_code.val(this.options.data.code);
				this.ipt_name.val(this.options.data.name);
				this.ipt_timeout.numberbox('setValue',this.options.data.timeout);
				this.ipt_remindinterval.numberbox('setValue',this.options.data.remindinterval);
//				this.ipt_pageurl.val(this.options.data.pageurl);
				this.ipt_timeoutremindflag.combobox('setValue',this.options.data.timeoutremindflag);
				this.ipt_processName.combobox('setValue',this.options.data.processname);
				this.ipt_roleName.combobox('setValue',this.options.data.rolecode);
				this.ipt_taskstartemailtemplateid.val(this.options.data.taskstartemailtemplateid);
				this.ipt_taskendemailtemplateid.val(this.options.data.taskendemailtemplateid);
				this.ipt_taskdescription.val(this.options.data.taskdescription);
			}
			
		},
		
		/**
		 * 初始化所有页面可见的下拉框
		 */
		_initCombobox : function() {
			//角色
			
			this.ipt_roleName.combobox({
				url : 'TaskConfig/queryFinRoleToSelect.do',
				panelHeight : 'auto',
				valueField : 'rolename',
				textField : 'roledesc',
				onSelect : _.bind(function (record){
				},this)
				/*,
				loader : _.bind(function (param,success,error){
					this.roleLoader(param,success,error);
				},this)*/
			});	
			
			//流程设置
			this.ipt_processName.combobox({
				url : 'ProcessConfig/getAllProcessConfig2Select.do',
				panelHeight : 'auto',
				valueField : 'id',
				textField : 'name',
				onSelect : _.bind(function (record){
				}
			,this)
			});	
		},
		_selectRole : function() {
			this._openRoleSearchDialog();
		},
		_selectMailTemplate_Start : function() {
			this._openMailTemplateSearchDialog("start");
		},
		_selectMailTemplate_End : function() {
			this._openMailTemplateSearchDialog("end");
		},
		validate : function() {
			var ret = true;

			return ret;
		},
		_confirmInput:function(callback){
			var ret = true;
			if(this.ipt_code.val()=="" ){
				app.messager.warn("任务英文名不能为空！");
				ret = false;
				return ret;
			}
			if(this.ipt_name.val()=="" || this.ipt_name.val()==null){
				app.messager.warn("任务中文名不能为空！");
				ret = false;
				return ret;

			}
			if(this.ipt_roleName.combobox('getValue')=="" || this.ipt_roleName.combobox('getValue')==null){
				app.messager.warn("角色设置不能为空！");
				ret = false;
				return ret;

			}
			if(this.ipt_taskstartemailtemplateid.val()=="" || this.ipt_taskstartemailtemplateid.val()==null){
				app.messager.warn("任务开始邮件设置不能为空！");
				ret = false;
				return ret;

			}
			if(this.ipt_taskendemailtemplateid.val()=="" || this.ipt_taskendemailtemplateid.val()==null){
				app.messager.warn("务结束邮件设置不能为空！");
				ret = false;
				return ret;

			}
			if(ret){
				//新增
				var serviceName  = "";
				var codeVal = "";
				if(this.options.mode=="add" ){
					serviceName = "addTaskConfig";
				}else{
					serviceName = "updateTaskConfig";
				}
				
				$.ajax(app.buildServiceData(serviceName, {
					data : {
						id : this.id.val(),
						code : this.ipt_code.val(),
						name : this.ipt_name.val(),
						timeout : this.ipt_timeout.val(),
						remindinterval : this.ipt_remindinterval.val(),
//						pageurl : this.ipt_pageurl.val(),
						timeoutremindflag :	 this.ipt_timeoutremindflag.combobox('getValue'),
						processconfigid :	 this.ipt_processName.combobox('getValue'),
						rolecode : this.ipt_roleName.combobox('getValue'),
						taskstartemailtemplateid : this.ipt_taskstartemailtemplateid.val(),
						taskendemailtemplateid : this.ipt_taskendemailtemplateid.val(),
						taskdescription : this.ipt_taskdescription.val()
					},
					context : this,
					success : function(data) {
						if (data.message === "OK") {
							if(this.options.mode=="add" ){
								app.messager.info("任务设置新增成功！");
							}else{
								app.messager.info("任务设置修改成功！");
							}
							//callback && callback();
							callback;
							return true;
						}else{
							app.messager.info(data.message);
							return false;
						}
					}
				}));
			}
			return ret;
		},
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				var ret = this._confirmInput(callback);
				if(ret){
					callback();
				}
			} else if (eventId === "cancel") {
				callback();
			}
		},
		_openMailTemplateSearchDialog : function(mode) {
			this.logger.debug();
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '选择邮件模版',
				width : 800,
				height : 450,
				closed : false,
				cache : false,
				className : 'app/common/admin/dialog/mailTemplateSearchDialog',
				modal : true,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						if(mode=="start"){
							this.ipt_taskstartemailtemplateid.val(data.id);
						}else{
							this.ipt_taskendemailtemplateid.val(data.id);
						}
						//this._doDistribute(rowdata,data.umid,data.username);
						//this.refresh();
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
					}
				} ]
			});
			
		},
		_openRoleSearchDialog : function() {
			this.logger.debug();
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '选择角色',
				width : 400,
				height : 350,
				closed : false,
				cache : false,
				className : 'app/common/admin/dialog/roleSearchDialog',
				modal : true,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						this.ipt_roleName.val(data.rolename);
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
					}
				} ]
			});
			
		}
		/*,
		roleLoader : function(param,success,error) {
			 var q = param.q || '';
			     if (q.length < 1){return false}
			     $.ajax({
			    	 type:"POST",
			         url: 'TaskConfig/queryFinRoleToSelect.do',
			         contentType:"application/x-www-form-urlencoded; charset=utf-8",
			         data: {
			        	 "roleDesc": q
			         },
			         success: function(data){
			        	 var obj = eval('(' + data + ')');  
			        	 var items = $.map(obj, function(item){
			                 return {
			                     rolename: item.rolename,
			                     roledesc: item.roledesc
			                 };
			             }); 
			        	success(items);
			         },
			         error: function(){
			             error.apply(this, arguments);
			         }
			     });
		}*/
	});
});
