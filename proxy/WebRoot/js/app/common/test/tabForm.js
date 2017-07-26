define([ "text!./templates/tabForm.html", "zf/base/BaseWidget", "app/base/BaseTabForm" ], function(template, BaseWidget) {
	return $.widget("app.tabForm", BaseWidget, {
		// default options
		options : {
			ajaxLock : true,
			lockMsg : '数据处理中，请稍候  ...',

			mode : 'edit',
			finSalesCancelSheet : null
		},

		templateString : template,

		// the constructor
		_create : function() {
			this._super();

			this._refresh();
		},

		_refresh : function() {
			this.logger.debug();

			var finSalesCancelSheet = this.options.finSalesCancelSheet;
			if (finSalesCancelSheet) {
				console.dir([ "finSalesCancelSheet", finSalesCancelSheet ]);
				var data = {
					sheetId : finSalesCancelSheet.id
				};
				$.ajax(app.buildServiceData("getAllSheetInfoBySheetId", {
					data : data,
					context : this,
					success : this._renderFormData
				}));
			}
		},

		_openApplyDialog : function(action, data) {
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '作废申请',
				width : 460,
				height : 220,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/dialog/cancelSheetApplyDialog',
				modal : true,
				data : data,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						dlg.dialogExt('destroy');
						this._saveOrSubmit({
							action : action,
							data : {
								"sheetId" : this.options.finSalesCancelSheet.id,
								"sheetType" : this.options.finSalesCancelSheet.cancelType,
								"sheetData" : data
							}
						});
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt('destroy');
					}
				} ]
			});
		},

		_onFormAction : function(event, data) {
			this.logger.debug();
			var eventId = data.eventId;

			if (eventId === "save") {
				var value = this.baseTabForm.BaseTabForm("value");
				console.dir([ "value", value ]);
				// 如下处理代码会导致审批链清空
				if (value.ticketCardRefund) {
					// var actionData = {
					// action : "save",
					// data : {
					// "sheetId" : this.options.finSalesCancelSheet.id,
					// "sheetType" :
					// this.options.finSalesCancelSheet.cancelType,
					// "sheetData" : {
					// finSalesCancelSheet : this.options.finSalesCancelSheet,
					// approveChain : []
					// }
					// }
					// };
					// this._saveOrSubmit(actionData);
				} else {
					app.messager.alert("TODO:" + this.widgetId, "积分作废不需要保存按钮", "info");
				}

			} else if (eventId === "view") {
				this._viewSheet();
			} else if (eventId === "submit") {
				if (!this.baseTabForm.BaseTabForm("validate")) {
					return;
				}
				// 验证是否上传了附件
				var value = this.baseTabForm.BaseTabForm("value");

				if (value.attachment && value.attachment.attachments && value.attachment.attachments.length > 0) {
					var data = {
						finSalesCancelSheet : this.options.finSalesCancelSheet
					};
					this._openApplyDialog(eventId, data);
				} else {
					app.messager.warn('提交前请至少上传一个附件!');
				}

			} else if (eventId === "cancel") {
				this._closeTab(false);
			}
		},

		_closeTab : function(refresh) {
			this._publish("app/closeTab", {
				refresh : refresh
			});
		},

		_saveOrSubmit : function(actionData) {
			this.logger.debug();
			var action = actionData.action;

			$.ajax(app.buildServiceData(action + "Sheet", {
				data : JSON.stringify(actionData.data),
				context : this,
				success : function(data) {
					if (action === "submit") {
						app.messager.info('作废单提交成功！');
						this._closeTab(true);
					} else if (action === "save") {
						app.messager.info('作废单保存成功！');
					}
				},
				error : function() {
					app.messager.error('作废单提交失败,运行异常！');
				}
			}));
		},

		_renderFormData : function(formData) {
			this.logger.debug();
			this.formData = formData;
			var cancelType = formData.finSalesCancelSheet.cancelType;
			var sheetType = formData.finSalesCancelSheet.sheetType;
			var taskcode = "";
			var readTaskcode = "";
			// 订单作废
			if (cancelType == "CS" && (sheetType == "SO" || sheetType == "SI")) {
				taskcode = "orderCancel";
				readTaskcode = "readOrderCancel";
			}
			// 订单券卡退货
			else if (cancelType == "RC" && (sheetType == "SO" || sheetType == "SI")) {
				taskcode = "cardRefund";
				readTaskcode = "readCardRefund";
			}
			// 使用单作废
			else if (cancelType == "CS" && (sheetType == "TC" || sheetType == "TP" || sheetType == "TG")) {
				taskcode = "redeemSheetCancel";
				readTaskcode = "readRedeemSheetCancel";
			}
			// 使用单券卡作废
			else if (cancelType == "RC" && (sheetType == "TC" || sheetType == "TP" || sheetType == "TG")) {
				taskcode = "redeemSheetCardRefund";
				readTaskcode = "readRedeemSheetCardRefund";
			} else {
				app.messager.alert('提示', '作废的申请单类型未知!');
				return;
			}
			// 动态修改表单模块
			var formConf = {
				viewType : {
					processcode : 'tabform',
					taskcode : 'default'
				}
			};

			formConf.readOnly = (this.options.mode === 'view');
			this.baseTabForm.BaseTabForm(formConf);

			// merge data
			if (formData.finSalesCancelSheet) {
				formData.finSalesCancelSheet.type = cancelType;
			}
			this.baseTabForm.BaseTabForm("render", formData);
		},
		_viewSheet : function() {
			this.logger.debug();
			var sheet;
			var sheetName;
			var sheetObjectName;
			var className;

			var formType = this.baseTabForm.BaseTabForm("getFormType");

			if (formType === app.constants.APPLY_SHEET_TYPE_CS_FSO) {
				sheet = this.formData.finSalesOrder;
				sheetName = "积分销售单";
				sheetObjectName = "finSalesOrder";
				className = "app/pointsale/saleorder/saleOrderForm";
			} else if (formType === app.constants.APPLY_SHEET_TYPE_CS_FSRS) {
				sheet = this.formData.finSalesRedeemSheet;
				sheetName = "积分使用单";
				sheetObjectName = "finSalesRedeemSheet";
				className = "app/pointsale/redeem/redeemForm";
			}
			var sheetData = this.baseTabForm.BaseTabForm("getSheetData");
			var obj = {
				"title" : sheetName + "--" + sheet.id,
				"url" : className,
				"mode" : "view"
			};
			obj[sheetObjectName] = sheet;
			console.dir([ "obj", obj ]);
			this._publish("app/openTab", obj);
		},
		
		// events bound via _on are removed automatically
		// revert other modifications here
		_destroy : function() {
			this._super();
		}
	});
});
