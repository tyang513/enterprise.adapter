define([ "text!./templates/processConfigInfoDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.processConfigInfoDialog", BaseWidget, {
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
				this.ipt_version.val(this.options.data.version);
				this.ipt_processdefinitionviewurl.val(this.options.data.processdefinitionviewurl);
				this.ipt_defaulttasktimeout.numberbox('setValue',this.options.data.defaulttasktimeout);
				this.ipt_defaultremindinterval.numberbox('setValue',this.options.data.defaultremindinterval);
				this.ipt_description.val(this.options.data.description);
				this.ipt_systemName.combobox('setValue',this.options.data.systemcode);
			}
			
		},
		
		/**
		 * 初始化所有页面可见的下拉框
		 */
		_initCombobox : function() {
			//模板所属子系统
			this.ipt_systemName.combobox({
				url : 'businessSystem/getAll.do',
				panelHeight : 'auto',
				valueField : 'code',
				textField : 'name',
				onSelect : _.bind(function (record){
				}
			,this)
			});	
		},
		validate : function() {
			var ret = true;

			return ret;
		},
		_confirmInput:function(callback){
			var ret = true;
			if(this.ipt_code.val()=="" ){
				app.messager.warn("流程英文名不能为空！");
				ret = false;
			}
			if(this.ipt_name.val()=="" || this.ipt_name.val()==null){
				app.messager.warn("流程中文名不能为空！");
				ret = false;
			}
		
			if(ret){
				//新增
				var serviceName  = "";
				var codeVal = "";
				if(this.options.mode=="add" ){
					serviceName = "addProcessConfig";
				}else{
					serviceName = "updateProcessConfig";
				}
				
				$.ajax(app.buildServiceData(serviceName, {
					data : {
						id : this.id.val(),
						code : this.ipt_code.val(),
						name : this.ipt_name.val(),
						version : this.ipt_version.val(),
						processdefinitionviewurl : this.ipt_processdefinitionviewurl.val(),
						defaulttasktimeout : this.ipt_defaulttasktimeout.val(),
						defaultremindinterval : this.ipt_defaultremindinterval.val(),
						description : this.ipt_description.val(),
						systemcode :	 this.ipt_systemName.combobox('getValue')
						
					},
					context : this,
					success : function(data) {
						if (data.message === "OK") {
							if(this.options.mode=="add" ){
								app.messager.info("流程设置新增成功！");
							}else{
								app.messager.info("流程设置修改成功！");
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
		}
	});
});
