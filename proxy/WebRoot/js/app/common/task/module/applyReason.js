define([ "text!./templates/applyReason.html", "app/base/BaseFormModule" ], function(template, BaseFormModule) {
	return $.widget("app.applyReason", BaseFormModule, {
		// default options
		options : {
		},

		templateString : template,

		_create : function() {
			this._super();
		},
		render : function(formData) {
			this._super(formData);
			this._initData();
		},
		_initData : function(){
			var applayMsg = undefined;
			var msg = undefined;
			//xiaopy 注释 20131113 以前是从订单中取，现在从流程表中取 ，先别删除以下代码
			if (this.formData.finSalesOrder){
				//applayMsg = this.formData.finSalesOrder.orderremark
				applayMsg = this.formData.taskData.processRemark;
				var status = this.formData.finSalesOrder.redeemstatus;
				var salesType = this.formData.finSalesOrder.salestype;
				if (salesType == 1){
					msg = status == 0 ? "注：暂不申请券卡出库。" : undefined;
				}
				else if (salesType == 2){
					msg = status == 0 ? "注：暂不申请后台注分。" : undefined;
				}
			}
			else {
				//applayMsg = undefined;
				applayMsg = this.formData.taskData.processRemark;
			}
			applayMsg = applayMsg == "" ? undefined : applayMsg;
			msg = msg == "" ? undefined : msg;
			if (applayMsg == undefined && msg == undefined){
				this._showModule(false);
				return ;
			}
			this._showModule(true);
			
			if (applayMsg == undefined || applayMsg == ""){
				this.applayContentDiv.hide();
			}
			this.msg.html(msg);
			this.applayMsg.html(applayMsg);
		},
		_destroy : function() {
			this._super();
		}
	});
});
