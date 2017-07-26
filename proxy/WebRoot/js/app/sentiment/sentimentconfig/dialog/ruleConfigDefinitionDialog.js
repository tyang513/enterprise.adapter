define([ "text!./templates/ruleConfigDefinitionDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.ruleConfigDefinitionDialog", BaseDialog, {
		// default options
		options : {
			//entity : 'ruleConfigDefinition'
		},

		templateString : template,
		
		_create : function() {
			this._super();
			this._initdata();
		},
		
		_initdata : function() {
			this.ruleDefId.val(this.options.ruleDefId);
			if (this.options.mode == "edit"){
				this.editForm.form("load", this.options.data);
			}
		},
		
		_updateRuleConfig : function(callback) { 
			if(this.code.val().trim() == "null") {
				app.messager.warn("不能输入null");
				return false;
			}
			
			if(this._validate() == false) {
				return false;
			}
			
			var serverName = "";
			if (this.options.mode == "add") {
				serverName = "addRuleConfig";
			} else {
				serverName = "updateRuleConfig";
			}
		
			this.editForm.ajaxForm(app.buildServiceData(serverName, {
				beforeSubmit : _.bind(function() {
					return this.editForm.form('validate');
				}, this),
				context : this,
				success : function(data) {
					if (data.success){
						app.messager.info(data.msg);
						callback && callback(data);// 新增或修改成功了，就调用回调函数，去关闭窗口，否则，不调用回调函数
					} else {
						app.messager.warn(data.msg);
					}
				},
				error : function(data) {
					callback && callback();
				}
			}));
				this.editForm.submit();
		},
		
		_validate: function(){
			var code = this.code.val();
			var content = this.content.val();
			if (code) {
				if(code.length>40) {
					app.messager.warn("配置代码最长40字符");
					return false;
				}
			} else {
				app.messager.warn("配置代码必须输入");
				return false;
			}
			if (content) {
				if(content.length>500) {
					app.messager.warn("配置内容最长500字符");
					return false;
				}
			} 
//			else {
//				app.messager.warn("配置内容必须输入");
//				return false;
//			}
			return true;
		},
		
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				this._updateRuleConfig(callback);
			} else if (eventId === "cancel") {
				if (callback) {
					callback();
				}
			}
		}
		
	});
});