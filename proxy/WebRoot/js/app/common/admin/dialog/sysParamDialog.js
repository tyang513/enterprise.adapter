define([ "text!./templates/sysParamDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.sysParamDialog", BaseWidget, {
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
			this.sysParam_Id.val(this.options.data.id);
			this.ipt_paramkey.text(this.options.data.paramkey);
			this.ipt_paramvalue.val(this.options.data.paramvalue);
			this.ipt_description.val(this.options.data.description);
			this.ipt_pw.text(this._formatIsPassword(this.options.data.pw));
			
			if(this.options.data.pw =="Y" || this.options.data.pw =="1"){
				this.ipt_paramvalue_Org_div.show();
				this.ipt_paramvalue_Confirm_div.show();
			}else{
				this.ipt_paramvalue_Org_div.hide();
				this.ipt_paramvalue_Confirm_div.hide();
			}
		},
		_formatIsPassword : function(val) {
			if (val) {
				 if(val=="Y"){
       			 return "是";
       		 }else if(val=="1"){
       			 return "是";
       		 }else if(val=="N"){
       			 return "否";
       	     }else{
       	    	 return "否";
       	     }
			} else {
				return "否";
			}
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
				},this),
				onLoadSuccess : _.bind(function() {
					this.ipt_systemName.combobox('setValue',this.options.data.systemcode);
					this.ipt_systemName.combobox('setText',this.options.data.systemName);
				},this)
			});	
		},
		createsysParam:function(callback){
			this.createsysParamForm.ajaxForm(
				app.buildServiceData("createsysParam", {
				beforeSubmit : _.bind(function() {
					return this.createsysParamForm.form('validate');
				}, this),
				context : this,
				success : function(data){
					if(data.message==="OK"){
						app.messager.info("参数修改成功！");
					}
					callback && callback();
				},
				error : function(data){
					app.messager.alert('info',data.message,'info');
					callback && callback();
				}
			}));
			this.createsysParamForm.submit();

		},
		validate : function() {
			var ret = true;

			return ret;
		},
		_confirmInput:function(callback){
			var ret = true;
			if(this.options.data.pw =="Y" || this.options.data.pw =="1"){
				if(this.ipt_paramvalue.val()=="" ){
					app.messager.warn("输入的参数值不能为空！");
					ret = false;
				}
				if(!(this.ipt_paramvalue.val()==this.ipt_paramvalue_Confirm.val())){
					app.messager.warn("输入的参数值与确认参数值应一致！");
					ret = false;
				}
			}
			if(ret){
				$.ajax({
					type : 'POST',
					url : 'param/updatesysParam.do',
					dataType : 'json',
					data : {
						sysParamId : this.sysParam_Id.val(),
						paramkey : this.ipt_paramkey.text(),
						paramvalue : this.ipt_paramvalue.val(),
						description : this.ipt_description.val(),
						pw : this.ipt_pw.text(),
						systemcode :	 this.ipt_systemName.combobox('getValue'),
						paramvalueOld : this.ipt_paramvalue_Org.val()
					},
					context : this
				}).done(function(data) {
					if (data.message === "OK") {
						//this.createsysParam(callback);
						app.messager.info("参数修改成功！");
						callback && callback();
						return true;
					}else{
						app.messager.warn(data.message);
						//callback && callback();

						return false;
					}
				}).fail(function(error) {
					app.messager.error('系统参数保存发生异常.');
					return false;
				});
			}
			
			return true;
		},
		execute : function(eventId, callback) {
			if (eventId === "ok") {
//				if(this._confirmInput()){
//					this.createsysParam(callback);
//				}
				this._confirmInput(callback);
			} else if (eventId === "cancel") {
				callback();
			}
		}
	});
});
