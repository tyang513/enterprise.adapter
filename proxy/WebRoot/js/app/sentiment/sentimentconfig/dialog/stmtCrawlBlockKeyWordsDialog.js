define([ "text!./templates/stmtCrawlBlockKeyWordsDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.stmtCrawlBlockKeyWordsDialog", BaseDialog, {
		// default options
		options : {
			entity : 'stmtCrawlBlockKeyWords'
		},

		templateString : template,
		
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.statusCombobox, 'status',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
				isCheckAll : false,
				cached : true
			});
		},
		_initData : function(){
			this._initCombobox();
			this.editForm.form("load", this.options.item);
			if(this.options.action === app.constants.ACT_VIEW){
				this.editForm.find("input").attr("readonly", true);
				this.statusCombobox.combobox({ readonly: true }); 
			}
		}
	
	});
});