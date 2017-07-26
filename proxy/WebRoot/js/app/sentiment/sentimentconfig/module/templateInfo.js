define([ "text!./templates/templateInfo.html", "app/base/BaseFormModule" ], function(template, BaseFormModule) {
	return $.widget("app.templateInfo", BaseFormModule, {
		// default options
		options : {
		},

		templateString : template,

		// ---------------- interface -----------------
		onFormAction : function(data) {
			this.logger.debug();
			console.dir([ 'data', data ]);
			var eventId = data.eventId;
			if (eventId === "editTemplate") {
				this._openTemplateDialog(this.template);
			}
		},
		
		render : function(formData) {
			this._super(formData);
//			this.type = formData.item.type;
//			if(this.type == app.constants.SHEET_TYPE_DEFAULT_SUBJECTUM) {
//				this.editBtnAlink.html('<span class="l-btn-text">修改标准结算模板</span>')
//			}
			this.template=formData.template;
			this._initData();
			
			//按钮下移 start
			if(this.options.readOnly) { 
				this.editBtn.hide(); 
			}
			//按钮下移 end
		},

		validate : function() {
			if (this.options.notNull) {
				return this.editForm.form('validate');
			}
			return true;
		},
		
		// --------event handler----------------

		// --------biz function-----------------
		_initData : function() {
			this.loadViewData();
		},
		
		loadViewData : function(template) {
			this.template = template || this.template;
			
			this.idSpan.val(this.template.id);
			this.codeSpan.text(this.template.templateCode);
			this.partnerFullNameSpan.text(this.template.partnerFullNameSpan);
			this.version.text(this.template.version);
			this.status.text(app.formatter.formatStatus(this.template.status));
			
			this.effectDateSpan.text(app.formatter.formatTime(this.template.effectDate));
			this.expiryDateSpan.text(app.formatter.formatTime(this.template.expiryDate ));
			/*if(this.type !== app.constants.SHEET_TYPE_DEFAULT_SUBJECTUM) {
				this.defaultDiv.show();
				this.merchantIdSpan.text(this.settleTemplate.merchantId);
				this.effectDateSpan.text(app.formatter.formatTime(this.template.effectDate));
				this.expiryDateSpan.text(app.formatter.formatTime(this.template.expiryDate));
			}*/
		},
		
		_openTemplateDialog : function(dataInfo) {
			var title = '修改模板配置';
			var height = 300;
			var width = 435;
			this.logger.debug();
			//this.template.type = this.type; // 传递type值
			var dlg = $("<div>").dialogExt({
				title : title,
				width : width,
				height : height,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/sentimentconfig/dialog/templateDialog',
				modal : true,
				data : {
					item : this.template,
					action : app.constants.ACT_EDIT
				},
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						if (data) {
							if (data.success) {
								dlg.dialogExt('destroy');
								app.messager.info("操作成功");
								this.loadViewData(data.data);
								this._publish("app/formmodule/claimTask", {
									"formId" : this.options.formId,
									template : data.data
								});
							} else {
								app.messager.warn(data.msg);
							}
						}
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt('destroy');
					}
				} ]
			});
		}

	});
});
