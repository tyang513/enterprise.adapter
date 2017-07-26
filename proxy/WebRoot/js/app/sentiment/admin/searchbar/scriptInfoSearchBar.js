define([ "text!./templates/scriptInfoSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/scriptInfoSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.scriptInfoSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'scriptInfo'
		},

		templateString : template,
		
		_create : function(){
			this._super();
			this._initCombobox();
		},
		
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.scriptJobType, 'ScriptInfoType',{
				value : (this.options.item && this.options.item.type !== undefined) ? this.options.item.type : undefined,
				isCheckAll : true,
				cached : true
			});
		}
	});
});
