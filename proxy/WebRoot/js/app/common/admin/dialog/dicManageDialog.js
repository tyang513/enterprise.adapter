define([ "text!./templates/dicManageDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.dicManageDialog", BaseWidget, {
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
			
			}else{
				this.id.val(this.options.data.id);
				this.ipt_name.val(this.options.data.name);
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
			//新增
			var serviceName  = "";
			if(this.options.mode=="add" ){
				serviceName = "adddic";
			}else{
				serviceName = "updatedic";
			}
			if(this.ipt_name.val()=="" ){
				app.messager.warn("字典英文名不能为空！");
				ret = false;
			}
			if(this.ipt_description.val()=="" ){
				app.messager.warn("字典中文描述不能为空！");
				ret = false;
			}
			if(this.ipt_systemName.combobox('getValue')=="" ){
				app.messager.warn("所属子系统不能为空！");
				ret = false;
			}
			if(ret){
				$.ajax(app.buildServiceData(serviceName, {
					data : {
						id : this.id.val(),
						name : this.ipt_name.val(),
						description : this.ipt_description.val(),
						systemName :	 this.ipt_systemName.combobox('getValue')
					},
					context : this,
					success : function(data) {
						if (data.message === "OK") {
							if(this.options.mode=="add" ){
								app.messager.info("数据字典新增成功！");
							}else{
								app.messager.info("数据字典修改成功！");
							}
							//callback && callback();
							callback;
							return true;
						}else{
							app.messager.warn(data.message);
							return false;
						}
					}
				}));
			}
			return ret;
		},
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				var  ret=this._confirmInput(callback);
				if(ret){
					callback();
				}
			} else if (eventId === "cancel") {
				callback();
			}
		}
	});
});
