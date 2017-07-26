define([ "text!./templates/ruleDefinitionDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.ruleDefinitionDialog", BaseDialog, {
		// default options
		options : {
			entity : 'ruleDefinition'
		},

		templateString : template,
		
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.businessTypeCombobox, 'RuleBusinessType',{
				value : (this.options.item && this.options.item.businessType !== undefined) ? this.options.item.businessType : undefined,
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.statusCombobox, 'Status',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : '0',
				isCheckAll : false,
				cached : false
			});
			if(this.options.action === "view"){
				this.businessTypeCombobox.combobox("disable");
				this.statusCombobox.combobox("disable");
				
			}
		},
		
		_initData : function() {
			this._super();
			if (this.options.action == "edit") {
				this.attachmentHref.attr("href", "sysAttachment/downloadAttachmentById.do?id="+this.options.item.attachmentId);
				this.attachmentHref.html(this.options.item.attachmentName);
			}
		},
		
		_validateNameRuleClassName : function() {
//			var name = this.name.val();
//			var ruleClassName = this.ruleClassName.val();
//			var pattern = /[\s\@\#\$\%\^\&\*\(\)\{\}\:\"\<\>\?\[\]]/;  
//			if(pattern.test(ruleClassName) == true){ 
//				app.messager.warn("规则实现类的名称不能有特殊字符");
//				return false;
//			} 
//			if(pattern.test(name) == true){  
//				app.messager.warn("规则名称不能有特殊字符");
//				return false;
//			}
//			var businessTypeStr = "(爬虫|解析)";
//						var pattern2 = new RegExp("^((通用)\\+" + businessTypeStr  +"\\+(.+)"+ ")$");
//						if(pattern2.test(name) == false) {
//							app.messager.warn("规则名称格式不正确，请参考页面提示规则");
//							return false;
//						}
//			var businessTypeComboboxText =  this.businessTypeCombobox.combo("getText");
//			    var result =name.split('+');
//				if(businessTypeComboboxText.indexOf(result[1]) == -1) {
//					app.messager.warn("规则名称的业务类型和下拉框选择的业务类型不一致");
//					return false;
//				}
			return true;
		},
		_openFileUploadDialog : function() {

			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : "批量上传附件",
				autoClose : false,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						dlg.dialogExt('destroy');
						this.oauAttachment.oauAttachment("attachmentQyery", {
							code : this.code,
							hidden : false
						});
					}, this)
				} ],
				width : 500,
				height : 450,
				closed : false,
				cache : false,
				className : 'app/finance/common/uploader',
				data : {
					autoUpload : false,
					url : "attachment/upload.do",
					formData : {
						relateId : this.code,
						relateType : "1",
						type : 7
					}
				},
				modal : true
			});

		},
		
		_saveData : function(callback, errorback) {
			
			//判断文件是否被移除
			var fileSize=app.utils.getFileSize(this.attachmentFile);
			if(fileSize==0) {
				app.messager.warn("选择附件不存在");
				return;
			}
			
			if (!this._validateNameRuleClassName()) {
				return false;
			}
			
			if (!this.options.entity)
				return;
			
			var serviceName;
			if (this.options.action == "edit") {
				serviceName = '/update.do';
			} else {
//				if(this.attachmentFile.val()=="") {
//					app.messager.warn("请选择规则附件");
//					return;
//				}
				serviceName = '/save.do';
			}

			this.editForm.ajaxForm(app.buildServiceData(this.options.entity + serviceName, {
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