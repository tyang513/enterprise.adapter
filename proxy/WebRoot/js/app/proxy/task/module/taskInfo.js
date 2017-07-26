define([ "text!./templates/taskInfo.html", "app/base/BaseFormModule" ], function(template, BaseFormModule) {
	return $.widget("app.taskInfo", BaseFormModule, {
		// default options
		options : {
		},

		templateString : template,

		// ---------------- interface -----------------
		onFormAction : function(data) {
			this.logger.debug();
			console.dir([ 'data', data ]);
			var eventId = data.eventId;
			if (eventId === "editTaskInfo") {
				this._openTemplateDialog(this.template);
			}
		},
		
		render : function(formData) {
			this._super(formData);
			this.taskInfo = formData.taskInfo;
			this._initData();
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
		
		loadViewData : function(taskInfo) {
			this.taskInfo = taskInfo || this.taskInfo;
			console.dir(['taskInfo', this.taskInfo]);
			this.idSpan.val(this.taskInfo.id);
			this.partnerFullNameSpan.text(this.taskInfo.partnerFullName);
			this.typeSpan.text(this.taskInfo.type);
			this.fileNameSpan.text(app.formatter.trim(this.taskInfo.fileName));
			this.filePathSpan.text(app.formatter.trim(this.taskInfo.filePath));
			this.refDmpIdSpan.text(app.formatter.trim(this.taskInfo.refDmpId));
			this.refCloudIdSpan.text(app.formatter.trim(this.taskInfo.refCloudId ));
		}
	});
});
