define([ "text!./templates/extendedAttriDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.extendedAttriDialog", BaseDialog, {
		// default options
		options : {
			entity : 'extendedAttri'
		},

		templateString : template,
		
		_initCombobox : function() {
			
			var extendNameInput = this.extendNameInput;
			var extendValueTextInput = this.extendValueTextInput;
			var extendValueNumberInput = this.extendValueNumberInput;
			var extendValueTextAreaInput = this.extendValueTextAreaInput;
			var extendValueComboInput = this.extendValueComboInput;
			
			var extendCodeInput = this.extendCodeInput;
			var extendAttrIdInput = this.extendAttrIdInput;
			var extendValueTextDiv = this.extendValueTextDiv;
			var extendValueNumberDiv = this.extendValueNumberDiv;
			var extendValueTextAreaDiv = this.extendValueTextAreaDiv;
			var extendValueTextComboDiv = this.extendValueTextComboDiv;
			
			
			//扩展属性定义表查找扩展属性定义  0 字符串   1数字   2数据字典   3文本
			
			this.extendNameCombobox.combobox({
			    mode: 'remote',
			    url: 'extendedAttriDefinition/queryExtendAttriDefinitionList.do',
			    valueField: 'id',
			    textField: 'name',
			    value:(this.options.item && this.options.item.id !== undefined) ? this.options.item.id : undefined,
			    fitColumns:true,
			    selected : false,
			    panelHeight : 'auto',
			    onSelect : _.bind(function(data) {
			    	extendCodeInput.val(data.code);
			    	extendNameInput.val(data.name);
			    	extendAttrIdInput.val(data.id);
			    	if (data.type=='1'){
			    		extendValueNumberDiv.show();
			    		
			    		this.letExtendValue = extendValueNumberInput;
			    		
			    		extendValueTextDiv.hide();
			    		extendValueTextAreaDiv.hide();
			    		extendValueTextComboDiv.hide();
						extendValueComboInput.combobox("disableValidation");
						extendValueTextInput.validatebox("disableValidation");
						extendValueTextAreaInput.validatebox("disableValidation");
			    	} else if (data.type == '0') {
			    		extendValueNumberDiv.hide();
			    		extendValueTextDiv.show();
			    		
			    		this.letExtendValue = extendValueTextInput;
			    		
			    		extendValueTextAreaDiv.hide();
			    		extendValueTextComboDiv.hide();
			    		extendValueNumberInput.numberbox("disableValidation");
						extendValueComboInput.combobox("disableValidation");
						extendValueTextAreaInput.validatebox("disableValidation");
			    	} else if (data.type == '3') {
			    		extendValueNumberDiv.hide();
			    		extendValueTextDiv.hide();
			    		extendValueTextAreaDiv.show();
			    		
			    		this.letExtendValue = extendValueTextAreaInput;
			    		
			    		extendValueTextComboDiv.hide();
			    		extendValueNumberInput.numberbox("disableValidation");
						extendValueComboInput.combobox("disableValidation");
						extendValueTextInput.validatebox("disableValidation");
						extendValueTextAreaInput.validatebox("enableValidation");  
			    	} else if (data.type == '2') {
			    		extendValueNumberDiv.hide();
			    		extendValueTextDiv.hide();
			    		extendValueTextAreaDiv.hide();
			    		extendValueTextComboDiv.show();
			    		
			    		this.letExtendValue = extendValueComboInput;
			    		
			    		extendValueNumberInput.numberbox("disableValidation");
						extendValueTextInput.validatebox("disableValidation");
						extendValueTextAreaInput.validatebox("disableValidation");
			    		extendValueComboInput.combobox({
			    			mode : 'remote',
			 			    url : 'extendedAttriDefinition/queryExtendValue.do?name=' + data.defaultValue,
			 			    valueField : 'dicitemkey',
			 			    textField : 'dicitemvalue',
			 			    value : (this.options.item && this.options.item.dicitemkey !== undefined) ? this.options.item.dicitemkey : undefined,
			 			    fitColumns : true,
			 			    selected : false,
			 			    panelHeight : 'auto',
			 			    onSelect : _.bind(function(data) {
			 			    	extendValueComboInput.val(data.dicitemvalue);
			 			    },this)
			 			    
			    		});
			    		
			    	}
			       }, this)
			});
		},
		
		_initData : function() {
			this._super();
			if (this.options.action === app.constants.ACT_CREATE) {
				this.templateIdInput.val(this.options.tempId);
			} else if(this.options.action === app.constants.ACT_EDIT) {
				this.templateIdInput.val(this.options.item.templateId);
				this.idInput.val(this.options.item.id);
				this.extendAttrIdInput.val(this.options.item.extendAttrId);
				this.extendNameInput.val(this.options.item.extendName);
				this.extendCodeInput.text(this.options.item.extendCode);
				this.extendValueInput.text(this.options.item.extendValue);
			}
		},
		
		_saveData : function(callback, errorback) {
			if(this.extendNameCombobox.combo("getValue") == 1) {
				if(this.extendValueTextAreaInput.val().length > 512) {
					app.messager.warn("属性值最多512个字符");
					return;
				}
			}
			
			console.dir(["extendValue",this.letExtendValue]);
			if(this.letExtendValue.attr("class").indexOf("combobox") != -1){
				this.extendValueHide.val(this.letExtendValue.combobox("getValue"));
			}else{
				this.extendValueHide.val(this.letExtendValue.val());
			}
			
			
//			else {
//				this.extendValueTextAreaInput.validatebox({ required: false});
//				this.extendValueTextAreaInput.val("");
//			}
			
			this.editForm.ajaxForm(app.buildServiceData(this.options.entity + "/save.do", {
				beforeSubmit : _.bind(function() {
					return this.editForm.form('validate');
				}, this),
				context : this,
				success : function(data) {
					if (data) {
						var action = "";
						if (!data.msg) {
							if (this.options.action === app.constants.ACT_CREATE) {
								action = "新建操作执行";
							} else if (this.options.action === app.constants.ACT_EDIT) {
								action = "修改操作执行";
							}
						}
						if (data.success) {
							app.messager.info(data.msg || action + "成功!");
						} else {
							app.messager.warn(data.msg || action + "失败!");
						}
					}
					callback && callback(data);
				},
				error : errorback
			}));
			this.editForm.submit();
		}
		
		
	});
});