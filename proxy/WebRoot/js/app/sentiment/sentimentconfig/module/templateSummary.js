define([ "text!./templates/templateSummary.html", "app/base/BaseSheetSummary" ], function(template, BaseSheetSummary) {
	return $.widget("app.templateSummary", BaseSheetSummary, {
		// default options
		options : {
			subEvents : {
				"app/formmodule/claimTask" : "_handleClaimTask"
			}
		},
		templateString : template,
		// ---------------- interface -----------------
		onFormAction : function(data) {
			this;
		},

		render : function(formData) {
			this._super(formData);
			this._initData();
			this._initButtons();
		},

		// --------biz function-----------------
		_initData : function() {
			this.logger.debug();
			var title = "模版配置";
			this.sheetTypeSpan.text(title +"("+ (this.formData.template.urlName!=null?this.formData.template.urlName:"")+"　"+
						app.formatter.formatTime(this.formData.template.effectDate)+"至"+
						app.formatter.formatTime(this.formData.template.expiryDate)+")");
		
			this.codeSpan.text(this.formData.template.templateCode );
			this.versionSpan.text(this.formData.template.version );
			this.createDateSpan.text(app.formatter.formatTime(this.formData.template.ctime));
			this.statusSpan.text(app.formatter.formatStatus(this.formData.template.status));
			/*if(this.formData && this.formData.item && this.formData.item.type == "SD") {
				this.statusSpan.text("已启用");
			} else {
				this.statusSpan.text(app.formatter.formatStatus(this.template.status));
			}*/
			
		},
		
		_initButtons : function() {
			this.logger.debug();
		},
		
		_handleClaimTask : function(event) {
			this.logger.debug();
			var formId = event.data.formId;
			if (formId === this.options.formId) {
				this._initData();
			}
		}
	});
});
