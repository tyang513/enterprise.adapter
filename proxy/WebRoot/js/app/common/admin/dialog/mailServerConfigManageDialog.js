define([ "text!./templates/mailServerConfigManageDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.mailServerConfigManageDialog", BaseWidget, {
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
			this.id.val(this.options.data.id);
			this.ipt_code.text(this.options.data.code);
			this.ipt_name.val(this.options.data.name);
			this.ipt_mailhost.val(this.options.data.mailhost);
			this.ipt_smtpport.val(this.options.data.smtpport);
			if(this.options.data.usessl =="true" ){
				this.ipt_usessl.combobox('select',"true");
			}else{
				this.ipt_usessl.combobox('select',"false");
			}
			this.ipt_user.val(this.options.data.user);
			
			this.ipt_systemName.combobox('setValue',this.options.data.systemcode);

		},
		/**
		 * 初始化所有页面可见的下拉框
		 */
		_initCombobox : function() {
			//参数所属子系统
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
			
			if(!(this.ipt_Pw.val()==this.ipt_Pw_Confirm.val())){
				app.messager.warn("输入的密码与确认密码应一致！");
				ret = false;
				
			}
			if(ret){
				$.ajax({
					type : 'POST',
					url : 'mail/updateMailServer.do',
					dataType : 'json',
					data : {
						id : this.id.val(),
						code : this.ipt_code.text(),
						name : this.ipt_name.val(),
						mailhost : this.ipt_mailhost.val(),
						smtpport : this.ipt_smtpport.val(),
						usessl : this.ipt_usessl.combobox('getValue'),
						user : this.ipt_user.val(),
						password : this.ipt_Pw_Org.val(),
						newPassword : this.ipt_Pw.val(),
						pwconfirm : this.ipt_Pw_Confirm.val(),
						systemcode :	 this.ipt_systemName.combobox('getValue')
					},
					context : this
				}).done(function(data) {
					if (data.message === "OK") {
						//this.createmailServerConfig(callback);
						app.messager.info("邮件配置修改成功！");
						callback && callback();
						//callback;
						return true;
					}else{
						app.messager.warn(data.message);
						//callback && callback();

						return false;
					}
				}).fail(function(error) {
					app.messager.error('邮件配置保存发生异常.');
					return false;
				});
			}
			
			return ret;
		},
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				if(this._confirmInput(callback)=="true"){
					callback();
				}
			} else if (eventId === "cancel") {
				callback();
			}
		}
	});
});
