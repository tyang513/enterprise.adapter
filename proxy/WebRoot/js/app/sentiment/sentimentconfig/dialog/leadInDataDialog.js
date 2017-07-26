define([ "text!./templates/leadInDataDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.leadInDataDialog", BaseDialog, {
		// default options
		options : {
		},

		templateString : template,
		
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.logicCombobox, 'LogicStatus',{
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.logicCombobox1, 'LogicStatus',{
				isCheckAll : false,
				cached : true
			});
		},
		
		_initData : function() {
			this._super();
			
			if(this.options.type === "subjectum") {
				this.subjectumInfo.show();
				this.isDataDiv.show();
			} else if(this.options.type === "ruleDefinition") {
				this.ruleDefinitionInfo.show();
				this.isDataDiv.show();
			} else if(this.options.type === "extendedAttriDefinition") {
				this.extendedAtrriDefInfo.show();
				this.isDataDiv.show();
			} else if(this.options.type === "czscSubjectum") {
				this.czscSubjectumInfo.show();
				this.isCzscDataDiv.show();
			} else if(this.options.type === "merchantData") {
				this.subjectumTemplateDiv.show();
				this.templateSubjectumInfo.show();
			}
		},
		
		_saveData : function(callback, errorback) {
			//判断文件是否被移除
			var fileSize=app.utils.getFileSize(this.dataFile);
			if(fileSize==-1) {
				app.messager.warn("待导入的文件不存在!");
				return;
			} else if(fileSize==0) {
				app.messager.warn("待导入的文件内容为空!");
				return;
			}
			var re=new RegExp("(.json|.JSON)$");
			var serviceUrl = 'subjectum/leadInData.do';
			if(this.options.type === "czscSubjectum"){
				
				this.logicCombobox.combobox('disableValidation');
				serviceUrl = 'subjectum/leadInCzscSubjectumData.do';
				
				re=new RegExp("(.csv|.CSV)$");
				if(!re.test(this.dataFile.val())) {
					app.messager.warn("待导入的文件只能为csv类型!");
					return;
				}
			} else if(this.options.type === "merchantData") {
				serviceUrl = 'subjectum/importMerchantData.do';
				
				re=new RegExp("(.xls|.XLS|.xlsx|.XLSX)$");
				if(!re.test(this.dataFile.val())) {
					app.messager.warn("待导入的文件只能为Excel文件!");
					return;
				}
			} else{
				if(!re.test(this.dataFile.val())) {
					app.messager.warn("待导入的文件只能为json类型!");
					return;
				}
			}
			
			// 默认全部导入
			
			if(this.options.type === "subjectum") {
				this.logicCombobox1.combobox('disableValidation');
				serviceUrl = 'subjectum/leadInSubjectum.do';
			} else if(this.options.type === "ruleDefinition") {
				this.logicCombobox1.combobox('disableValidation');
				serviceUrl = 'ruleDefinition/leadInRuleDefinition.do';
			} else if(this.options.type === "extendedAttriDefinition") {
				serviceUrl = 'extendedAttriDefinition/leadInExtendedAttriDef.do';
				this.logicCombobox1.combobox('disableValidation');
			} else if(serviceUrl !== 'subjectum/leadInData.do') {
				this.logicCombobox.combobox('disableValidation');
				this.logicCombobox1.combobox('disableValidation');
			}
			
			this.editForm.ajaxForm(app.buildServiceData(serviceUrl, {
				beforeSubmit : _.bind(function() {
					return this.editForm.form('validate');
				}, this),
				context : this,
				success : function(data) {
					if (data) {
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