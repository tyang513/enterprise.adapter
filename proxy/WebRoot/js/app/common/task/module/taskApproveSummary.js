define([ "text!./templates/taskApproveSummary.html", "app/base/BaseSheetSummary" ], function(template, BaseSheetSummary) {
	return $.widget("app.taskApproveSummary", BaseSheetSummary, {
		// default options
		options : {
			// ajaxLock : true,
			// lockMsg : '任务处理中，请稍候 ...',
			order : null,

			readOnlyButtons : [ {
				"text" : "查看申请单",
				"group" : "left",
				"eventId" : "view"
			}, {
				"text" : "查看处理历史",
				"group" : "left",
				"eventId" : "viewHistory",
				"actionDijit" : "taskApproveSummary"
			}, {
				"text" : "关闭",
				"group" : "right",
				"eventId" : "cancel"
			}]
		},

		templateString : template,
		_create : function() {
			this._super();
		},
		render : function(formData) {
			this._super(formData);
			this.formData = formData;
			this._initData();
			this.hideButton();
			this._initActionMsg();
		},
		_initData : function() {
			var taskName = this.formData.taskData.taskname.split("—")[0];
			var sheetId = this.formData.taskData.sheetId;
			this.finTaskId = this.formData.taskData.id;
			this.sheetId.text(sheetId);
			this.taskName.text(taskName);
			var sheet = null;
			
			if (this.formType == app.constants.SHEET_SUBJECTUM) {// 活动申请
				sheet = this.formData.subjectum;
			}
			
			if (this.formType == app.constants.SHEET_SETTLE_SHEET) {// 活动申请
				sheet = this.formData.settleSheet;
			}

			if (sheet != null) {
				if (sheet.tempauthorizeduserumid && sheet.tempauthorizeduserumid != "") {
					this.applierName.text(sheet.tempauthorizedusername + "(" + sheet.appliername + "授权)");
				} else if (sheet.appliername) {
					this.applierName.text(sheet.appliername);
				} else if (sheet.tempAuthorizedUserUmid && sheet.tempAuthorizedUserUmid != "") {
					this.applierName.text(sheet.tempAuthorizedUserName + "(" + sheet.applierName + "授权)");
				} else if (sheet.umName) {
					this.applierName.text(sheet.umName);
				}
			}
			var applyTime = new Date(this.formData.taskData.fpSartTime);
			this.applyTime.text(app.formatter.formatTime(applyTime));

		},
		
		onFormAction : function(data) {
			this.logger.debug();
			var action = data.eventId;

			if (action == "agree" || action == "disagree") {
				this._callAgreeDialog(action);

			}else if (action == "beginProcess") {
				this._hideButton("beginProcess");
				console.dir([ "showButtons", data.showButtons ]);
				this._showButtons(data.showButtons);
			} else if (action == "askOther") {// 征求他人意见
				this._callAskOtherDialog(action);
			} else if (action == "turnOther") { // 转发他人处理
				this._callTurnOtherDialog(action);
			} else if (action == "remark") {
				_callRemarkDialog();
			} else if (action == "viewHistory") {// 查看处理历史记录
				this._processHistoryDialog();
			}
		},
		
		_processHistoryDialog : function() {
			var constants = app.constants;
			this.logger.debug();
			var processId = this.formData.taskData.processInstanceId;
			var processRemark = this.formData.taskData.processRemark;
			
			var formTy = this.formType;// 单子的类型
			var orderInfo = '';
			if(formTy === constants.SHEET_SUBJECTUM){
				orderInfo = this.formData.subjectum;
			}
			if(formTy === constants.SHEET_SETTLE_SHEET){
				orderInfo = this.formData.settleSheet;
			}
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
		_callAgreeDialog : function(action) {
			var approve = true;
			var height = 185;
			if (action == "disagree") {
				approve = false;
				height = 220;
				var dlg = $("<div>").dialogExt({
					title : '审批意见',
					width : 400,
					height : height,
					closed : false,
					autoClose : false,
					cache : false,
					className : 'app/common/task/dialog/taskApprovedDialog',
					modal : true,
					data : {
						formData : this.formData,
						approve : approve
					},
					buttons : [ {
						text : '确认',
						eventId : "ok",
						handler : _.bind(function(remark) {
							dlg.dialogExt("destroy");
							this.submitApproveForm(remark);
						}, this)
					}, {
						text : '取消',
						eventId : "cancel",
						handler : function() {
							dlg.dialogExt("destroy");
						}
					} ]
				});
			} else if (action == "agree") {
				var approve = approve ? "1" : "0";
				; // / 1.同意 0.不同意
				var post = {
					approve : approve,
					remark : "同意",
					backUserId : "",
					taskId : this.formData.taskData.id,
					approveIndex : "",
					isSuggests : false
				};
				this.submitApproveForm(post);
			}
		},
		_callRemarkDialog : function() {
			var taskId = this.formData.taskData.id;
			var dlg = $("<div>").dialogExt({
				title : '备注',
				width : 350,
				height : 230,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/task/dialog/taskMemoDialog',
				modal : true,
				data : {
					finTaskId : taskId
				},
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(remark) {
						dlg.dialogExt("destroy");
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
		submitApproveForm : function(data) {
			var approve = data.approve;
			var remark = data.remark;
			var backUserId = data.backUserId;
			var taskId = data.taskId;
			var approveIndex = data.approveIndex;
			var isSuggests = data.isSuggests;
			$.ajax(app.buildServiceData("completeApprove", {
				context : this,
				global : true,
				data : {
					approve : approve,
					backUserId : backUserId,
					finTaskId : taskId,
					memo : remark,
					approveIndex : approveIndex,
					isSuggests : isSuggests
				},
				success : function(data) {
					if (data.status == 200) {
						app.messager.info(data.message);
						app.utils.closeTab(this);
					} else {
						app.messager.error("任务审批失败");
					}
				}
			}));
		},
		_onSave : function(event) {
			this.logger.debug();
		},

		_createReadOnlyButtons : function() {
			var buttons = [ {
				"text" : "同意",
				"group" : "left",
				"eventId" : "agree",
				"actionDijit" : "taskApproveSummary"
			}, {
				"text" : "不同意",
				"group" : "left",
				"eventId" : "disagree",
				"actionDijit" : "taskApproveSummary"
			} ];

			var buttons = this.options.buttons;
			if (buttons && buttons.length > 0) {
				var toolbar = this.element.find(this.options.toolbarClass);

				var buttonsHtml = [];
				var group;
				var index = 0;
				_.each(buttons, function(button) {
					index++;

					if (group !== button.group) {
						if (group !== undefined) {
							buttonsHtml.push("<span class=\"group-seperator\"></span>");
						}
						group = button.group;
					}

					var uuid = this.widgetId + "_" + index;
					var html = button.type === "menubutton" ? _self._getMenuButtonHtml(button, uuid) : _self._getButtonHtml(button, uuid);
					buttonsHtml.push(html);
				});

				$(buttonsHtml.join('')).appendTo(toolbar);
				this._parse(toolbar);
			}
		},

		hideButton : function() {
			if (this.formData.taskData.status != "1") {
				this._hideButtons("agree,disagree,askOther,turnOther,moreAction");

			}
		},
		refresh : function() {

		},
		_onSubmit : function(event) {
			this.logger.debug();
		},
		_initActionMsg : function() {
			this.logger.debug();
			// freezeStatus == 5 流程已被冻结 提示
			if (this.formData.taskData.freezeStatus === 5 || this.formData.taskData.freezeStatus === '5') {
				this.element.find(".summary").addClass("freezeImg")
			}
		}
	});
});
