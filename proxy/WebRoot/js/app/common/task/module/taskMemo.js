define([ "text!./templates/taskMemo.html", "app/base/BaseFormModule" ], function(template, BaseFormModule) {
	return $.widget("app.taskMemo", BaseFormModule, {
		// default options
		options : {
			order : null
		},

		templateString : template,

		render : function(formData) {
			this._super(formData);
			this.formData = formData;
			this._initData();
		},

		_initData : function() {
			
			if (this.formData.taskData){ // 任务
				this.remarkValue = this.formData.taskData.memo;
				if (this.remarkValue == "创建任务"){
					this.formData.taskData.memo = "";
					this.remarkValue = undefined;
				}
			}
			else if (this.formData.subjectum){ // 结算主体
				this.remarkValue = this.formData.subjectum.description;
			}
			else if (this.formData.settleSheet){// 结算单
				this.remarkValue = this.formData.settleSheet.memo;
			}
			
			this.remarkView.text(this.remarkValue);
//			this._showRemark(this.remarkValue);
		},
//		_showRemark : function (remarkValue){
//			if (remarkValue) {
//				this._showModule(true);
//			} else {
//				this._showModule(false);
//			}
//		},
		onFormAction : function(data) {
			this.logger.debug();
			var action = data.eventId;
			if (action == "remark"){
				this._callRemarkDialog();
			}
		},
		_callRemarkDialog : function (){
			var dlg = $("<div>").dialogExt({
				title : '备注',
				width : 400,
				height : 250,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/task/dialog/taskMemoDialog',
				modal : true,
				data : {
					remarkValue : this.remarkValue
				},
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(remark) {
						dlg.dialogExt("destroy");
						this.remarkView.text(remark);
						this.remarkValue = remark;
						if (this.formData.taskData){
							this.formData.taskData.memo = this.remarkView.text();
						}
						else if (this.formData.subjectum){
							this.formData.subjectum.description = this.remarkValue;
						}
						else if (this.formData.settleSheet){
							this.formData.settleSheet.memo = this.remarkValue;
						}
						
//						this._showRemark(remark);
					}, this)
				},{
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt("destroy");
					}
				}]
			});
		},
		validate : function (){
			if (this.remarkValue != undefined && this.remarkValue != ""){
				if (this.remarkValue.length > 512){
					app.messager.error("备注长度不能超过300汉字");
					return false;
				}
			}
			return true;
		},
		_onSave : function(event) {
			this.logger.debug();
		},
		_onSubmit : function(event) {
			this.logger.debug();
		}
	});
});
