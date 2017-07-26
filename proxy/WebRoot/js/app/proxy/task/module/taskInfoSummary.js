define([ "text!./templates/taskInfoSummary.html", "app/base/BaseSheetSummary" ], function(template, BaseSheetSummary) {
	return $.widget("app.taskInfoSummary", BaseSheetSummary, {
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
			console.dir('render', this.formData);
			this._super(formData);
			this._initData();
			this._initButtons();
		},
		// --------biz function-----------------
		_initData : function() {
			this.logger.debug();
			this.sheetTypeSpan.text(this.formData.taskInfo.taskName);
			this.taskCodeSpan.text(this.formData.taskInfo.taskCode );
			this.partnerFullNameSpan.text(this.formData.taskInfo.partnerFullName);
			this.createDateSpan.text(app.formatter.formatTime(this.formData.taskInfo.ctime));
			this.statusSpan.text(app.formatter.formatTaskInfoStatus(this.formData.taskInfo.status));
			
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
