define([ "text!./templates/templateRuleDialog.html", "zf/base/BaseWidget", "easyuiplugins/jquery.edatagrid"  ], function(template, BaseWidget) {
	return $.widget("app.templateRuleDialog", BaseWidget, {
		// default options
		options : { 
		},

		_create : function() {
			this._super();
			this._initCombobox();
			this._initData();
		},
		
		_onAddRow : function(event) {
			this.logger.debug();
			this.approvalTemplate_TemplateTask.edatagrid('addRow');
		},
		
		_onSaveRow : function(event) {
			this.logger.debug();
			this.approvalTemplate_TemplateTask.edatagrid('saveRow');
			var options = app.utils.parseOptions($(event));//这个event啥都没
			var datas = this.approvalTemplate_TemplateTask.datagrid('getRows');
//			if (datas == null || datas.length == 0) {
//				app.messager.warn('请添加配置!');
//				return false;
//			}
			return true;
		},
		
		_onCancelRow : function(event) {
			this.logger.debug();
			this.approvalTemplate_TemplateTask.edatagrid('cancelRow');
		},
		
		_onDestroyRow : function(event) {
			this.logger.debug();
			app.utils.gridEditRemove(this.approvalTemplate_TemplateTask);
			//this.approvalTemplate_TemplateTask.edatagrid('destroyRow');
		},
		
		templateString : template,
		
		_initCombobox : function() {
			this._ruleConfigs = [];
			
			var businessTypeInput = this.businessTypeInput;
			var ruleClassNameInput =this.ruleClassNameInput;
			var ruleDefinitionIdInput = this.ruleDefinitionIdInput;
			// 检索状态为启用的规则定义
			this.nameCombobox.combobox({
			    mode: 'remote',
//			    url: "ruleDefinition/queryRuleDefinitionListNoPage.do?status=" + app.constants.LOGIC_ENABLE+"&categoryCode="+this.options.categoryCode + "&categoryName=" + this.options.categoryName,
			    url: "ruleDefinition/queryRuleDefinitionListNoPage.do",
			    valueField: 'id',
			    textField: 'name',
			    value:(this.options.item && this.options.item.id !== undefined) ? this.options.item.id : undefined,
			    fitColumns:true,
			    selected : false,
			    onBeforeLoad : _.bind(function(param) {
			    	param.status = app.constants.LOGIC_ENABLE;
			    	param.categoryCode = this.options.categoryCode;
			    	param.name = this.options.categoryName;
			    },this),
			    onSelect : _.bind(function(data) {
			    	businessTypeInput.val(app.formatter.formatBusinessType(data.businessType));
			    	ruleClassNameInput.val(data.ruleClassName);
					ruleDefinitionIdInput.val(data.id);
					
					$.ajax(app.buildServiceData("ruleConfigDefinition/queryRuleConfigDefinitionList.do", {
						data : {
							ruleDefId : data.id
						},
						context : this,
						success : function(data) {
							this._ruleConfigs = data.rows;
							this._renderRuleConfigs();
						},
						error : function(error) {
							app.messager.error("修改异常");
						}
					}));
			    }, this)
			});
		},
		
		_deleteRows: function(){
			var grid = this.approvalTemplate_TemplateTask;
			var rows = grid.edatagrid("getRows");
			if(rows && rows.length>0) {
				while(rows.length>0) {
					grid.edatagrid("deleteRow", 0);
				}
			}
		},
		
		_renderRuleConfigs: function(){
			var grid = this.approvalTemplate_TemplateTask;
			
			//清空grid
			this._deleteRows();
			
			//加载grid内容
			if(this._ruleConfigs && this._ruleConfigs.length>0) { 
				for(var i=0; i<this._ruleConfigs.length; i++) { 
					grid.edatagrid("appendRow", this._ruleConfigs[i]);
				}
			}
		},
		_fmt: function(value, row, index) {
			return "<div style='word-break:break-all;white-space:normal;text-align:left;'>"+value +"</div>" ;
		},
		_formatConvertClassName :function(value, row, index){
			return "<div style='word-break:break-all;white-space:normal;text-align:left;'>"+value !=null || value !="" ? value:""+ "</div>" ;
		},
		
		_initData : function() {
			this.approvalTemplate_TemplateTask.edatagrid({
				url : '',
				saveUrl : '',
				updateUrl : '',
				destroyUrl : '',
				onCancelEdit : function(index, row) {
				}, 
				onAfterEdit : function(rowIndex, rowData, changes) {
				},
			});
			
			
			if (this.options.action === app.constants.ACT_CREATE) {
				this.tempIdInput.val(this.options.tempId);
				this.addRule.show();
				this.editRule.hide();
			} else if(this.options.action === app.constants.ACT_EDIT) {
				this.tempIdInput.val(this.options.item.tempId);
				this.idInput.val(this.options.item.id);
				this.ruleDefinitionIdInput.val(this.options.item.ruleDefinitionId);
				this.nameInput1.text(this.options.item.name);
				this.businessTypeInput1.text(app.formatter.formatBusinessType(this.options.item.businessType));
				this.ruleClassNameInput1.text(this.options.item.ruleClassName);
				this.description.val(this.options.item.description);
				this.addRule.hide();
				this.editRule.show();
				this._viewRuleConfig();
				 
				
				$.ajax(app.buildServiceData("ruleConfig/list.do", {
					data : {
						'templateRuleId' : this.options.item.id
					},
					context : this,
					success : function(data) {
						this._ruleConfigs = data.rows;
						this._renderRuleConfigs();
					},
					error : function(error) {
						app.messager.error("修改异常");
					}
				}));
				
			}
			 
		},
		
//		_renderRuleConfigs : function(){
//			this.attrTable.empty();
//			var contentHtml = [];
//			for(var i = 0; i < this._ruleConfigs.length; i++) {
//				this._ruleConfigs[i].index = i + 1;
//				var templateDiv = "<tr><td width=\"120\" align=\"right\"><input type=\"hidden\" name=\"code${index}\" readonly=\"readonly\" value=\"${code}\" >"
//				 		+ "<label>Code[${code}]：</label></td>"
//						+ "<td><input name=\"content${index}\" value=\"${content}\" "
//						+ "type=\"text\" maxlength=\"40\" class=\"easyui-validatebox\" data-options=\"required:true\" missingMessage=\"请填写规则配置内容\">"
//						+ "&nbsp;<span class=\"required\">*</span></td></tr>";
//				contentHtml.push(app.utils.parseTemplate(templateDiv, this._ruleConfigs[i]));
//			}
//			$(contentHtml.join('')).appendTo(this.attrTable);
//			this._parse(this.attrTable);
//			
//		},
		
		_viewRuleConfig : function() {
			$.ajax(app.buildServiceData("ruleConfig/queryRuleConfigList.do", {
				data : {
					templateRuleId : this.options.item.id
				},
				context : this,
				success : function(data) {
					this._ruleConfigs = data;
					this._renderRuleConfigs();
				},
				error : function(error) {
					app.messager.error("修改异常");
				}
			}));
		},
		
//		_saveData : function(callback, errorback) {
//			if (!this.options.entity)
//				return;			
//			
//			var _formData = this.editForm.serializeObject();
//			var templateRule = _.clone(_formData);
//			var formData = {
//				ruleConfigs : [],
//				entity : templateRule
//			};
//			
//			var index;
//			if(this._ruleConfigs.length > 0){
//				for(var i=0; i<this._ruleConfigs.length; i++){
//					index = i + 1;
//					if(templateRule["content" + index] === "null" || templateRule["content" + index] === "") {
//						app.messager.warn(templateRule["code" + index] + "的内容不能为空");
//						return;
//					}
//					delete templateRule["code" + index];
//					delete templateRule["content" + index];
//					formData.ruleConfigs.push({
//						id : this.options.action === app.constants.ACT_CREATE ? undefined : this._ruleConfigs[i].id,
//						code : _formData["code" + index],
//						content : _formData["content" + index]
//					});
//				}
//			}			
//			
//			console.dir(["formData", formData]);
//			$.ajax(app.buildServiceData(this.options.entity + "/saveData.do", {
//				data : formData,
//				context : this,
//				success : function(data) {
//					if (data) {
//						var action = "";
//						if (!data.msg) {
//							action = "新建操作执行";
//						}
//						if (data.success) {
//							app.messager.info(data.msg || action + "成功!");
//						} else {
//							app.messager.warn(data.msg || action + "失败!");
//						}
//					}
//					callback && callback(data);
//				},
//				error : errorback
//			}));			
//		},
		 
		_submitApprovalTemplateForm : function(callback){
			
			//获取所有表格数据 
			var datas = this.approvalTemplate_TemplateTask.datagrid('getRows');

			for (var i=0;i<datas.length;i++){
				//delete datas[i]['isNewRecord'];
				if(datas[i]['content'].trim()=="" || datas[i]['content'].length>500){
					//datas.splice(i,1);
					app.messager.warn("配置内容必填，且最多500个字符");
					return false;
				}
			}
			var url=""; 
			
			url=  "templateRule/saveAllData.do";
			
			var str = JSON.stringify(datas);
			this.approvalChainJsonData.val(str);
			
			if(this.idInput.val()) {
			} else {
				if(this.nameCombobox.combobox("getValue")) {
				} else {
					app.messager.warn('请选择规则!');
					return ;
				}
				
				var nameComboboxData = this.nameCombobox.combobox("getData");
				var count = 0;
				for(var i=0;i<nameComboboxData.length; i++) {
					if(nameComboboxData[i].name == this.nameCombobox.combobox("getText")) {
						count = 1;
					}
				}
				if(count == 0) {
					app.messager.warn('请选择规则!');
					return ;
				}
			} 
			
			

			
			this.editForm.form('submit',{
				url:url,
				dataType:'json',
				beforeSubmit : _.bind(function() {
					//return this.editForm.form('validate');
					
					return true;
				}, this),
		    	success: function(data){  
		    		var obj = eval('(' + data + ')');
					if (obj.success == 1) {
						app.messager.info(obj.msg);
						
					}else{
						app.messager.warn(obj.msg);
					}
					callback && callback(obj);
		    	}   
			});
		
		},
		_confirmInput : function() {
			var flag = true;
				flag = this._onSaveRow();
			return flag;
		},
		
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				if (this._confirmInput()) {
//					app.messager.confirm("确认信息","是否确定?",_.bind(function(r){
//						if(r){
							this._submitApprovalTemplateForm(callback);
//						}
//					},this));
					 
				}
			} else if (eventId === "cancel") {
				if (callback) {
					callback();
				}
			}
		}
	});
});