define([ "text!./templates/${dialogWidgetName}.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.${dialogWidgetName}", BaseDialog, {
		// default options
		options : {
			entity : '${lowerName}'
		},

		templateString : template,
		
		_initCombobox : function() {
#foreach($po in $!{columnDatas})
#if ($po.dataName != $primaryKey && $po.viewData && $po.viewData.form == true && $po.viewData.dictionary)  	   
			app.utils.initComboboxByDict(this.${po.dataName}Combobox, '${po.viewData.dictionary}',{
				value : (this.options.item && this.options.item.$po.dataName !== undefined) ? this.options.item.$po.dataName : undefined,
				isCheckAll : false,
				cached : true
			});
#end
#end			
		}
	});
});