define([ "text!./templates/mailTemplateManageDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.mailTemplateManageDialog", BaseWidget, {
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
			if(this.options.mode =="add"){
				this.ipt_code_Div.show();
				this.ipt_code_Level_Div.hide();
			}else{
				this.ipt_code_Div.hide();
				this.ipt_code_Level_Div.show();
				
				this.id.val(this.options.data.id);
				this.ipt_code_Level.text(this.options.data.code);
				this.ipt_title.val(this.options.data.title);
				this.ipt_content.val(this.options.data.content);
				
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
				},this),
				onLoadSuccess : _.bind(function() {
					this.ipt_systemName.combobox('setValue',this.options.data.systemcode);
					this.ipt_systemName.combobox('setText',this.options.data.systemName);
				},this)
			});	
		},
		validate : function() {
			var ret = true;

			return ret;
		},
		_confirmInput:function(callback){
			var ret = true;
			if(this.options.mode=="add" ){
				if(this.ipt_code.val()=="" ){
					app.messager.warn("编码不能为空！");
					ret = false;
				}
			}else{
				if(this.ipt_code_Level.text()=="" || this.ipt_code_Level.text()==null){
					app.messager.warn("编码不能为空！");
					ret = false;
				}
			}
			if(this.ipt_title.val()=="" ){
				app.messager.warn("标题不能为空！");
				ret = false;
			}
			if(this.ipt_content.val()=="" ){
				app.messager.warn("模板内容不能为空！");
				ret = false;
			}
			if(this.ipt_systemName.combobox('getValue')=="" ){
				app.messager.warn("所属子系统不能为空！");
				ret = false;
			}
			if(ret){
				//新增
				var serviceName  = "";
				var codeVal = "";
				if(this.options.mode=="add" ){
					serviceName = "addMailTemplate";
					codeVal = this.ipt_code.val();
				}else{
					serviceName = "updateMailTemplate";
					codeVal = this.ipt_code_Level.text();
				}
				
				$.ajax(app.buildServiceData(serviceName, {
					data : {
						id : this.id.val(),
						code : codeVal,
						title : this.ipt_title.val(),
						content : this.ipt_content.val(),
						systemcode :	 this.ipt_systemName.combobox('getValue')
					},
					context : this,
					success : function(data) {
						if (data.message === "OK") {
							if(this.options.mode=="add" ){
								app.messager.info("模板新增成功！");
							}else{
								app.messager.info("模板修改成功！");
							}
							//callback && callback();
							//callback;
							ret =true;
							return ret;
						}else{
							app.messager.warn(data.message);
							ret =false;
							return ret;
						}
					}
				}));
			}
			return ret;
		},
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				if(this._confirmInput(callback)){
					callback();
				}
			} else if (eventId === "cancel") {
				if (callback) {
					callback();
				}
			}
		}
	});
});
