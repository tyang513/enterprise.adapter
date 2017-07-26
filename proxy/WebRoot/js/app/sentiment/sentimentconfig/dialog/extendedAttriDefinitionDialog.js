define([ "text!./templates/extendedAttriDefinitionDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.extendedAttriDefinitionDialog", BaseDialog, {
		// default options
		options : {
			entity : 'extendedAttriDefinition'
		},

		templateString : template,

		_initCombobox : function() {
			app.utils.initComboboxByDict(this.typeCombobox, 'ExtendedAttri', {
				value : (this.options.item && this.options.item.type !== undefined) ? this.options.item.type : undefined,
				isCheckAll : false,
				cached : true,
				disabled : this.options.item != null,
				onSelect : _.bind(function(record) {
					// dicitemkey 0:字符串, 1:数字, 2:数据字典 3:文本
					if (record.dicitemkey == 1 || record.dicitemkey == 0 || record.dicitemkey == 3) {
						this.defaultValueDiv.hide();
						this.defaultValueInput.validatebox({ required: false});
					} else {
						this.defaultValueDiv.show();
					}
				}, this)
			});

			if (this.options.action === app.constants.ACT_VIEW) {
				this.typeCombobox.combobox("disable");

			}
		},

		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.editForm.find("div.fitem:even").css("clear", "both");
			this._initCombobox();
			if (this.options.action === app.constants.ACT_EDIT) {
				if (this.options.item.type === '1' || this.options.item.type === '0' || this.options.item.type === '3') {
					this.defaultValueDiv.hide();
					this.defaultValueInput.validatebox({ required: false});
				} else {
					this.defaultValueDiv.show();
				}
				this.codeInput.attr("readonly", true);
				this.nameInput.attr("readonly", true);
				this.defaultValueInput.attr("readonly", true);
				this.editForm.form("load", this.options.item);
			} else if (this.options.action === app.constants.ACT_CREATE) {
				this.editForm.resetForm();
				this.defaultValueDiv.hide();
				this.defaultValueInput.validatebox({ required: false});
			} else if (this.options.action === app.constants.ACT_VIEW) {
				if (this.options.item.type === '1' || this.options.item.type === '0' || this.options.item.type === '3') {
					this.defaultValueDiv.hide();
					this.defaultValueInput.validatebox({ required: false});
				} else {
					this.defaultValueDiv.show();
				}
				this.editForm.find("input").attr("readonly", true);
				this.editForm.find("textarea").attr("readonly", true);
				this.editForm.form("load", this.options.item);
			} 
		}
	});
});