define([ "text!./templates/jobConfigErrorInfoDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.jobConfigErrorInfoDialog", BaseWidget, {
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
			//this._initCombobox();
			this._initdatas();
		},
		_initdatas : function() {
			if(this.options.mode =="edit"){
				this.ipt_id.val(this.options.data.id);
				this.ipt_jobkey.val(this.options.data.jobkey);
				this.ipt_createtime.val(this.formatTimeStamp(this.options.data.createtime));
				this.ipt_jobinputid.val(this.options.data.jobinputid);
				this.ipt_errorinfo.val(this.options.data.errorinfo);
			}
			
		},
		formatTimeStamp : function(val) {
			if (!val) {
				return "";
			}
			return new Date(val).dateFormat("yy-MM-dd hh:mm");
		},
		validate : function() {
			var ret = true;

			return ret;
		},
		_confirmInput:function(callback){
			var ret = true;
			if(this.ipt_jkey.val()=="" ){
				app.messager.warn("定时任务英文代码不能为空！");
				ret = false;
			}
			if(this.ipt_name.val()=="" || this.ipt_name.val()==null){
				app.messager.warn("定时任务中文名称不能为空！");
				ret = false;
			}
		
			if(ret){
				//新增
				var serviceName  = "";
				var codeVal = "";
				if(this.options.mode=="add" ){
					serviceName = "addjobConfig";
				}else{
					serviceName = "updatejobConfig";
				}
				
				$.ajax(app.buildServiceData(serviceName, {
					data : {
						id : this.ipt_id.val(),
						jobkey : this.ipt_jobkey.val(),
						createtime : this.ipt_createtime.val(),
						jobinputid : this.ipt_jobinputid.val(),
						errorinfo : this.ipt_errorinfo.val()
					},
					context : this,
					success : function(data) {
						if (data.message === "OK") {
							if(this.options.mode=="add" ){
								app.messager.info("定时任务设置新增成功！");
							}else{
								app.messager.info("定时任务设置修改成功！");
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
				callback();

//				var ret = this._confirmInput(callback);
//				if(ret){
//					callback();
//				}
			} else if (eventId === "cancel") {
				callback();
			}
		}
	});
});
