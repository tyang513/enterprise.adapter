define([ "text!./templates/reportConfigDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.reportConfigDialog", BaseDialog, {
		// default options
		options : {
			entity : 'reportConfig'
		},

		templateString : template,
		
		_initData : function() {
			if (this.options.action === app.constants.ACT_VIEW) {
			this.fileTypeCombobox.combobox({ disabled: true });
			this.emailTypeCombobox.combobox({ disabled: true });
			this.isUseDynamicFileNameCombobox.combobox({ disabled: true });
			this.editForm.find("input").attr("readonly", true);
			this.editForm.find("textarea").attr("readonly", true);
			}
			this._super();
			this._initCombobox();
		},
		
		_initCombobox : function() {
			
			app.utils.initComboboxByDict(this.fileTypeCombobox, 'FileType',{
				value : (this.options.item && this.options.item.fileType !== undefined) ? this.options.item.fileType : undefined,
				isCheckAll : false,
				cached : true,
				onLoadSuccess : _.bind(function() {
					this._controlColumn();
				},this),
				onSelect : _.bind(function(record) {
					this._controlColumn();
				},this)
			});
			
			app.utils.initComboboxByDict(this.emailTypeCombobox, 'EmailType',{
				value : (this.options.item && this.options.item.emailType !== undefined) ? this.options.item.emailType : undefined,
				isCheckAll : false,
				cached : true,
				onLoadSuccess : _.bind(function() {
					if(this.fileTypeCombobox.combobox("getValue") !== "5") {
						if(this.emailTypeCombobox.combobox("getValue") === "2") {
							this.emailContentValidate.val("");
							this.emailContentValidate.parent().hide();
							this.emailContentValidate.validatebox("disableValidation");
						} else {
							this.emailContentValidate.parent().show();
							this.emailContentValidate.validatebox("enableValidation");
						}
					}
				},this),
				onSelect : _.bind(function(record) {
					if(this.fileTypeCombobox.combobox("getValue") !== "5") {
						if(this.emailTypeCombobox.combobox("getValue") === "2") {
							this.emailContentValidate.val("");
							this.emailContentValidate.parent().hide();
							this.emailContentValidate.validatebox("disableValidation");
						} else {
							this.emailContentValidate.parent().show();
							this.emailContentValidate.validatebox("enableValidation");
						}
					}
				},this)
			});
			
			app.utils.initComboboxByDict(this.isUseDynamicFileNameCombobox, 'LogicStatus',{
				value : (this.options.item && this.options.item.isUseDynamicFileName !== undefined) ? this.options.item.isUseDynamicFileName : undefined,
				isCheckAll : false,
				cached : true
			});
		},
		
		_controlColumn : function() {
			if(this.fileTypeCombobox.combobox("getValue") === "5") { // DB 方式不需要生成文件、发送邮件 
				this.emailTypeCombobox.combobox("clear");
				this.emailTypeCombobox.parent().hide();
				this.emailTypeCombobox.combobox("disableValidation");
				
				this.emailContentValidate.val("");
				this.emailContentValidate.parent().hide();
				this.emailContentValidate.validatebox("disableValidation");
				
				this.emailTemplateCodeValidate.val("");
				this.emailTemplateCodeValidate.parent().hide();
				this.emailTemplateCodeValidate.validatebox("disableValidation");
				
				this.isUseDynamicFileNameCombobox.combobox("setValue", "2"); // 设置为否
				this.isUseDynamicFileNameCombobox.combobox("setText", "否"); // 设置为否
				this.isUseDynamicFileNameCombobox.combobox("readonly",true);
			} else {
				this.emailTypeCombobox.parent().show();
				this.emailTypeCombobox.combobox("enableValidation");
				if(this.emailTypeCombobox.combobox("getValue") !== "2") {
					this.emailContentValidate.parent().show();
					this.emailContentValidate.validatebox("enableValidation");
				}
				
				this.emailTemplateCodeValidate.parent().show();
				this.emailTemplateCodeValidate.validatebox("enableValidation");
				
				this.isUseDynamicFileNameCombobox.combobox("readonly", false);
			}
		},
		
		_saveData : function(callback, errorback) {
			if (!this.options.entity)
				return;

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