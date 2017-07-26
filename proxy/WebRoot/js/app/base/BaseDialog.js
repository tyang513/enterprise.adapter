define([ "zf/base/BaseWidget" ], function(BaseWidget) {
	return $.widget("app.BaseDialog", BaseWidget, {
		// default options
		options : {
			entity : undefined,
			action : app.constants.ACT_CREATE,
			item : null
		},

		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
//			this.editForm.find("div.fitem:even").css("clear","both");
			this._initCombobox();
			if (this.options.action === app.constants.ACT_CREATE) {
				this.editForm.resetForm();
			} else if (this.options.action === app.constants.ACT_EDIT) {
				this.editForm.form("load", this.options.item);
			} else if (this.options.action === app.constants.ACT_VIEW) {
				this.editForm.find("input").attr("readonly", true);
				this.editForm.find("textarea").attr("readonly", true);
				this.editForm.form("load", this.options.item);
			}
		},

		_initCombobox : function() {
			
		},

		_saveData : function(callback, errorback) {
			if (!this.options.entity)
				return;

			this.editForm.ajaxForm(app.buildServiceData(this.options.entity + "/save.do", {
				beforeSubmit : _.bind(function() {
					return this.editForm.form('validate');
				}, this),
				context : this,
				success : function(data) {
					if (data) {
						var action = "";
						if (!data.msg) {
							if (this.options.action === app.constants.ACT_CREATE) {
								action = "新建操作执行";
							} else if (this.options.action === app.constants.ACT_EDIT) {
								action = "修改操作执行";
							}
						}
						if (data.success) {
							app.messager.info(data.msg || action + "成功!");
						} else {
							app.messager.warn(data.msg || action + "失败!");
						}
					}
					callback && callback(data);
				},
				error : errorback
			}));
			this.editForm.submit();
		},

		execute : function(eventId, callback) {
			if (eventId === app.constants.BTN_OK) {
				this._saveData(callback);
			} else if (eventId === app.constants.BTN_CANCEL) {
				if (callback) {
					callback();
				}
			}
		}
	});
});
