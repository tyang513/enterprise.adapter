define([ "text!./templates/taskApprovedDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.taskApprovedDialog", BaseWidget, {
		// default options
		options : {
			ajaxLock : true,
			lockMsg : '数据处理中，请稍候  ...'
		},

		templateString : template,

		_create : function() {
			this._super();
			this.remark.validatebox('enableValidation');
//			this.backApproveUser.combobox('enableValidation');
			this.approve = this.options.approve;
			this.formData = this.options.formData;
			this.taskId = this.formData.taskId;
			this.askFlag = this.formData.taskData.taskname.indexOf("征询") >= 1;
			
			
			
			this._initData();
		},

		execute : function(eventId, callback) {
			if (eventId === "ok") {
				if (callback) {
					var flag = this.approvedForm.form('validate');
					var approveIndex = this.backApproveUser.combobox("getValue");
					//不同意，直接退回给申请人
					if(!this.approve){
						approveIndex = "-1";
					}
					var remark = this.remark.val();
					var backUserId = "";
					if (approveIndex != ""){
//						if (this.askFlag || approveIndex === "-1"){
//							backUserId = this.options.formData.subjectum.applierUmId;
//						}
//						else{
//							backUserId = this.baseApproveHistory[approveIndex - 1].approverumid;													
//						}
						backUserId = this.options.formData.subjectum === undefined ? this.options.formData.settleSheet.umId : this.options.formData.subjectum.applierUmId
					}
					var approve = this.approve ? "1" : "0";; /// 1.同意  0.不同意
					var post = {
						approve : approve,
						remark : this.remark.val(),
						backUserId : backUserId,
						taskId : this.taskId,
						approveIndex : approveIndex,
						isSuggests : this.askFlag
					};
					if(flag){
						callback(post);
					}
				}
			} else if (eventId === "cancel") {
				if (callback) {
					callback();
				}
			}
		},

		_initData : function() {
			//不同意也不显示退回人，直接退回到销售人员
			if(!this.approve){
				this.approveResult.text("不同意");
				this.initApproveDate();
				this.backApproveUserDiv.css("display","none");
				this.remark.val("不同意");
			}else{
				this.backApproveUserDiv.css("display","none");
				this.remark.val("同意");
			}
		},
		initApproveDate : function() {
			
			$.ajax(app.buildServiceData("initTaskApprove", {
				context : this,
				data : {
					finTaskId : this.taskId
				},
				success : function(data){
					if(data.applyUser){
						this.askFlag = data.isTaskRequestSuggest;
						if (data.isTaskRequestSuggest){
							this.requestSuggest(data);
							return ;
						}
						this.requestData(data);
					}
				}
			}));
		},
		requestSuggest : function(approveData){
			$.ajax(app.buildServiceData("querySuggestBackUser", {
				context : this,
				data : {
					finTaskId : this.taskId
				},
				success : function(data){
					if(approveData.isTaskRequestSuggest){
						if (data.baseApproveHistory.length > 0){
							this.baseApproveHistory = [data.baseApproveHistory[data.baseApproveHistory.length - 1]];
						}
						else{
							this.baseApproveHistory = [];
						}
						this.backApproveUser.combobox({
							required:true,
							valueField: 'index',
							textField: 'approvername',
							panelHeight : 'auto',
							data : this.baseApproveHistory
						});
						this.backApproveUser.combobox('setValue', this.baseApproveHistory[0].index);
						this.approveResult.text("不同意(征求人不同意将结束流程)");
						this.backApproveUserDiv.css("display","none");
						this.remark.val("不同意");
					}
				}
			}));
		},
		requestData : function (data) {
			this.baseApproveHistory = data.baseApproveHistory;
			
			//设置销售的index为0，后端会直接结束流程
			this.baseApproveHistory.unshift({
				approvername : data.applyUser.userName,
				index : -1,
				approverumid : data.applyUser.umid
			});
			
			this.backApproveUser.combobox({
				valueField: 'index',
				textField: 'approvername',
				panelHeight : 'auto',
				data : this.baseApproveHistory
			});
		}
	});
});
