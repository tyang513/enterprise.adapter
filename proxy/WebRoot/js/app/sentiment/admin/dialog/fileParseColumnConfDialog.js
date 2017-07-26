define([ "text!./templates/fileParseColumnConfDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.fileParseColumnConfDialog", BaseDialog, {
		// default options
		options : {
			entity : 'fileParseColumnConf'
		},

		templateString : template,
		
		_initData : function() {
			this._super();
			this.fileParseConfId.val(this.options.fileParseConfId);
			if (this.options.mode == "edit" || this.options.mode =="view") {
				this.id.val(this.options.fileParseColumnConf.id);
				this.name.val(this.options.fileParseColumnConf.name);
				this.code.val(this.options.fileParseColumnConf.code);
				this.mappingName.val(this.options.fileParseColumnConf.mappingName);
				this.description.val(this.options.fileParseColumnConf.description);
				this.attr1.val(this.options.fileParseColumnConf.attr1);
				this.isMappingCombobox.combobox('setValue',this.options.fileParseColumnConf.isMapping);
				this.typeCombobox.combobox('setValue',this.options.fileParseColumnConf.type);
				if(this.options.mode =="view"){
					this.editForm.find("input").attr("readonly", true);
					this.isMappingCombobox.combobox({ disabled: true });
					this.typeCombobox.combobox({ disabled: true });
				}
			}
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.isMappingCombobox, 'IsMapping',{
				value : (this.options.fileParseColumnConf && this.options.fileParseColumnConf.isMapping !== undefined && this.options.fileParseColumnConf.isMapping !== 0) ? this.options.fileParseColumnConf.isMapping : undefined,
				isCheckAll : false,
				cached : true,
				onLoadSuccess : _.bind(function(record) {
					if(this.options.mode =="view"){
						this.isMappingCombobox.combobox("disable"); 
					}
				}, this)
			});
			app.utils.initComboboxByDict(this.typeCombobox, 'Type',{
				value : (this.options.fileParseColumnConf && this.options.fileParseColumnConf.type !== undefined) ? this.options.fileParseColumnConf.type : undefined,
				isCheckAll : false,
				cached : true,
				onLoadSuccess : _.bind(function(record) {
					if(this.options.mode =="view"){
						this.typeCombobox.combobox("disable");
					}
				}, this)
			});
			
		}
		
	});
});