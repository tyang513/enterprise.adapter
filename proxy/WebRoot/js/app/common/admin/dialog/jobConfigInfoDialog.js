define([ "text!./templates/jobConfigInfoDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.jobConfigInfoDialog", BaseWidget, {
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
				this.ipt_jkey.val(this.options.data.jkey);
				this.ipt_name.val(this.options.data.name);
				this.ipt_classname.val(this.options.data.classname);
				this.ipt_intervall.val(this.options.data.intervall);
				this.ipt_starttime.datebox('setValue',this.formatTimeStamp(this.options.data.starttime));
				this.ipt_endtime.datebox('setValue',this.formatTimeStamp(this.options.data.endtime));
				this.ipt_systemName.combobox('setValue',this.options.data.systemcode);
			}
			
		},
		formatTimeStamp : function(val) {
			if (!val) {
				return "";
			}
			return new Date(val).dateFormat("yy-MM-dd hh:mm");
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
						id : this.id.val(),
						jkey : this.ipt_jkey.val(),
						name : this.ipt_name.val(),
						classname : this.ipt_classname.val(),
						intervall : this.ipt_intervall.val(),
						starttime : this.ipt_starttime.datebox('getValue'),
						endtime : this.ipt_endtime.datebox('getValue'),
						systemcode :	 this.ipt_systemName.combobox('getValue')
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
