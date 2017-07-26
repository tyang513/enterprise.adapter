define([ "zf/base/BaseWidget" ], function(BaseWidget) {
	return $.widget("app.BasePageForm", BaseWidget, {
		// default options
		options : {
			entity : undefined,
			saveType : 'ajax',

			action : app.constants.ACT_CREATE,
			item : null
		},

		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this._initCombobox();
			if (this.options.action === app.constants.ACT_CREATE) {
				this.editForm.resetForm();
			} else if (this.options.action === app.constants.ACT_EDIT) {
				this.editForm.form("load", this.options.item);
			} else if (this.options.action === app.constants.ACT_VIEW) {
				this.editForm.find("input").attr("readonly", true);
				this.editForm.form("load", this.options.item);
			}
		},

		_initCombobox : function() {
		},

		_saveEntity : function(callback, errorback) {
			if (!this.options.entity)
				return;

			this.editForm.ajaxForm(app.buildServiceData(this.options.entity + "/save.do", {
				beforeSubmit : _.bind(function() {
					return this.editForm.form('validate');
				}, this),
				context : this,
				success : function(data) {
					this._saveSuccess(data, callback);
				},
				error : errorback
			}));
			this.editForm.submit();
		},

		_saveData : function(callback, errorback) {
			this.logger.debug();
			var data = {
				entity : this.editForm.serializeObject()
			};
			$.ajax(app.buildServiceData(this.options.entity + "/saveData.do", {
				data : data,
				context : this,
				success : function(data) {
					this._saveSuccess(data, callback);
				},
				error : errorback
			}));
		},

		_saveSuccess : function(data, callback) {
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
				callback && callback(data);
			}
		},
		
		_onSave : function(event) {
			this.logger.debug();
			if (this.options.saveType === "ajax") {
				this._saveData(_.bind(this._closeTab, this));
			} else if (this.options.saveType === "form") {
				this._saveEntity(_.bind(this._closeTab, this));
			} else {
				this.logger.error("未知保存类型:" + this.options.saveType);
			}
		},

		_onCancel : function(event) {
			this.logger.debug();
			this._closeTab({
				success : true
			});
		},

		_closeTab : function(data) {
			if (data.success) {
				this._publish("app/closeTab");
			}
		}
	});
});
