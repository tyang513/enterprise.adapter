define([ "text!./templates/dicItemDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.dicItemDialog", BaseWidget, {
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
			this._initdatas();
		},
		_initdatas : function() {
			if(this.options.mode =="add"){
				this.dicid.val(this.options.dicid);
				this.parentid.val(this.options.parentid);
			}else{
				this.id.val(this.options.id);
				this.dicid.val(this.options.dicid);
				this.ipt_dicItemKey.val(this.options.dicitemkey);
				this.ipt_dicItemValue.val(this.options.dicitemvalue);
			}
			
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
				serviceName = "adddicItem";
			}else{
				serviceName = "updatedicItem";
			}
			if(this.ipt_dicItemKey.val()=="" ){
				app.messager.warn("字典项代码不能为空！");
				ret = false;
			}
			if(this.ipt_dicItemValue.val()=="" ){
				app.messager.warn("字典项名称不能为空！");
				ret = false;
			}
			if(ret){
				$.ajax(app.buildServiceData(serviceName, {
					data : {
						id : this.id.val(),
						dicid : this.dicid.val(),
						dicitemkey : this.ipt_dicItemKey.val(),
						dicitemvalue : this.ipt_dicItemValue.val(),
						parentid : this.parentid.val()
					},
					context : this,
					success : function(data) {
						if (data.message === "OK") {
							if(this.options.mode=="add" ){
								app.messager.info("数据字典项新增成功！");
							}else{
								app.messager.info("数据字典项修改成功！");
							}
							callback && callback();
						}else{
							app.messager.info(data.message);
						}
					}
				}));
			}
			return ret;
		},
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				this._confirmInput(callback);
			} else if (eventId === "cancel") {
				callback();
			}
		}
	});
});
