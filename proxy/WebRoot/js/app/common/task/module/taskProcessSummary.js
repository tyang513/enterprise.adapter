define([ "text!./templates/taskProcessSummary.html", "app/base/BaseSheetSummary" ],
		function(template, BaseSheetSummary,serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.taskProcessSummary", BaseSheetSummary, {
		// default options
		options : {
			// ajaxLock : true,
			order : null,

			readOnlyButtons : [ {
				"text" : "查看申请单",
				"eventId" : "view"
			}, {
				"text" : "查看处理历史",
				"eventId" : "viewHistory",
				"actionDijit" : "taskProcessSummary"
			}]
		},

		templateString : template,

		// -----------------interface--------------------
		render : function(formData) {
			this._super(formData);
			this.formData = formData;
			this._initData();
			this._hiddenBtn(false);
//			this._initActionMsg();
		},

		value : function() {
			return {
				"taskData" : this.formData.taskData
			};
		},

		_initData : function() {
			var taskName = this.formData.taskData.taskname.split("—")[0];
			var sheetId = this.formData.taskData.sheetId;
			this.finTaskId = this.formData.taskData.id;
			this.sheetId.text(sheetId);
			this.taskName.text(taskName);
			var constants = app.constants;
			var sheet = null;
			if (this.formType == constants.SHEET_SUBJECTUM) {// 结算主体
				sheet = this.formData.subjectum;
			} else if (this.formType == constants.SHEET_SETTLE_SHEET) {// 结算单
				sheet = this.formData.settleSheet;
			}
			if (sheet != null) {
				if (sheet.tempauthorizeduserumid && sheet.tempauthorizeduserumid != "") {
					this.applierName.text(sheet.tempauthorizedusername + "(" + sheet.appliername + "授权)");
				} else if (sheet.appliername) {
					this.applierName.text(sheet.appliername);
				} else if (sheet.tempAuthorizedUserUmid && sheet.tempAuthorizedUserUmid != "") {
					this.applierName.text(sheet.tempAuthorizedUserName + "(" + sheet.applierName + "授权)");
				} else if (sheet.applierName) {
					this.applierName.text(sheet.applierName);
				}
				if (this.formType === constants.SHEET_SETTLE_SHEET) {
					this.applierName.text(sheet.umName);
				}
			} else {
				this.applierName.text(this.formData.taskData.startusername);
			}
			var applyTime = new Date(this.formData.taskData.fpSartTime);
			this.applyTime.text(app.formatter.formatTime(applyTime));
		},
		
		onFormAction : function(data) {
			this.logger.debug();
			var action = data.eventId;
			if (action == "claim") {
				
				$.ajax(app.buildServiceData("claimTask", {
					data : {
						finTaskId : this.finTaskId
					},
					context : this,
					global : true,
					success : function(data) {
						if (data.status == 200) {
							this._hiddenBtn(true);
							
//							if(this.formData.taskData.taskcode === app.constants.SUBJECTUM_MERCHANT_CONF_JOB) {
								this._trigger("action", null, {
									eventId : "reloadForm"
								});
//							}
							
							app.messager.info(data.message);							
						} else {
							app.messager.error(data.message);
						}
					},
					fail : function(error) {
						app.messager.error('任务处理异常.');
					}
				}));
			} else if (action == "askOther") {// 征求他人意见
				this._callAskOtherDialog(action);
			} else if (action == "turnOther") { // 转发他人处理
				this._callTurnOtherDialog(action);
			} else if (action == "viewHistory") {// 查看处理历史记录
				this._processHistoryDialog();
			} else if (action == "viewPartner") { //查看商户信息
				this._openPartnerDialog();
			}
		},
		
		
		_openPartnerDialog: function(){
			var constants = app.constants;
			this.logger.debug();
			var processId = this.formData.taskData.processInstanceId;
			var processRemark = this.formData.taskData.processRemark;
			
			var formTy = this.formType;// 单子的类型
			var orderInfo = '';
			var rowdata = {};
			if(formTy === constants.SHEET_SUBJECTUM){
				orderInfo = this.formData.subjectum; 
			}
			if(formTy === constants.SHEET_SETTLE_SHEET){
				orderInfo = this.formData.settleSheet;
			}
			if(orderInfo) {
				rowdata["partnerId"] = orderInfo.partnerId; 
			}
			
			// 打开对话框
			var title = "查看商户信息";
			var buttons = [  {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt('destroy');
					}
				} ]; 
			
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : title,
				width : 945,
				height : 350,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/partner/dialog/partnerInfo',
				modal : true,
				data : {
					data : rowdata,
					mode : "view"
				},
				buttons : buttons
			});
		},
		
		_processHistoryDialog : function() {
			var constants = app.constants;
			this.logger.debug();
			var formTy = this.formType;// 单子的类型
			var orderInfo = '';

			if (formTy == constants.SHEET_SUBJECTUM) {// 结算主体使用
				orderInfo = this.subjectum;
			}
			
			if (formTy == constants.SHEET_SETTLE_SHEET) {// 结算单使用
				orderInfo = this.settleSheet;
			}

			var processId = this.formData.taskData.processInstanceId;
			var processRemark = this.formData.taskData.processRemark;
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '查看流程记录',
				width : 600,
				height : 300,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/task/dialog/processHistoryDialog',
				modal : true,
				data : {
					processId : processId,
					remark : processRemark,
					formTy : formTy,
					finSalesOrder : orderInfo
				}

			});
		},
		_callAskOtherDialog : function(action) {
			var taskId = this.formData.taskData.id;
			var dlg = $("<div>").dialogExt({
				title : '征求他人意见',
				width : 350,
				height : 230,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/task/dialog/requestSuggestDialog',
				modal : true,
				data : {
					finTaskId : taskId
				},
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(remark) {
						dlg.dialogExt("destroy");
						app.utils.closeTab(this);
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt("destroy");
					}
				} ]
			});
		},
		_callTurnOtherDialog : function(action) {
			var taskId = this.formData.taskData.id;
			var dlg = $("<div>").dialogExt({
				title : '转发他人处理',
				width : 350,
				height : 230,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/task/dialog/forwardOaTaskDialog',
				modal : true,
				data : {
					finTaskId : taskId
				},
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(remark) {
						dlg.dialogExt("destroy");
						app.utils.closeTab(this);
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : _.bind(function() {
						dlg.dialogExt("destroy");
					}, this)
				} ]
			});
		},

		_delayEnableMenus : function() {
			var btnOptions = this.claimButton.linkbutton("options");
			if (btnOptions.enableMenus) {
				this._enableMenus(btnOptions.enableMenus);
			}
		},

		// ---------------------event handler----------------------
		_hiddenBtn : function(isForce) {
			if (this.claimButton) {
				var btnOptions = this.claimButton.linkbutton("options");
				if (isForce) {
					this._hideButton(btnOptions.eventId);
					if (btnOptions.showButtons) {
						this._showButtons(btnOptions.showButtons);
					}

					setTimeout(_.bind(this._delayEnableMenus, this), 100);
				}
				if (this.formData.taskData.assignerumid != null && this.formData.taskData.assignerumid != '') {
					this._hideButton(btnOptions.eventId);
					if (btnOptions.showButtons) {
						this._showButtons(btnOptions.showButtons);
					}
				}
				if (this.formData.taskData.status && this.formData.taskData.status != "1") {
					this._hideButton(btnOptions.eventId);
					if (btnOptions.showButtons) {
						this._hideButtons(btnOptions.showButtons);
					}
					this._hideButtons("askOther,turnOther");
				}

				// 处理更多操作菜单
				if (this.moreActionButton && btnOptions.enableMenus) {
					if (this.formData.taskData && this.formData.taskData.assignerumid !== '') {
						// 已领用
						this._enableMenus(btnOptions.enableMenus);
					} else {
						// 未领用
						this._disableMenus(btnOptions.enableMenus);
					}
				}
			}
			// freezeStatus == 5 流程已被冻结 禁用添加/修改积分池按钮
			if (this.formData.taskData.freezeStatus === 5 || this.formData.taskData.freezeStatus === '5') {
				this._hideButtons("addPointPool");
			}
		},
		_onSave : function(event) {
			this.logger.debug();
		},
		_initActionMsg : function() {
			this.logger.debug();
//			// 如果已经开票，提示收回发票
//			if (this.formData.taskData.taskcode == 'SiebelAddPoint') {
//				this._appendActionMsg("请发起siebel注分前先下载注分附件。");
//			}
//			// freezeStatus == 5 流程已被冻结 提示
//			if (this.formData.taskData.freezeStatus === 5 || this.formData.taskData.freezeStatus === '5') {
//				// this._appendActionMsg("申请单正在申请作废，任务已冻结。");
//				this.element.find(".summary").addClass("freezeImg")
//			}
		},
		_onSubmit : function(event) {
			this.logger.debug();
		}
	});
});
