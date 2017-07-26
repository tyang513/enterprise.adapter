define([ "text!./templates/templateCategoryRelDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.templateCategoryRelDialog", BaseDialog, {
		// default options
		options : {
			entity : 'templateCategoryRel'
		},

		templateString : template,
		
		_initCombobox : function() {
		}
	});
});