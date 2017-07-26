define([ "text!./templates/fileParseColumnConfSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/fileParseColumnConfSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.fileParseColumnConfSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'fileParseColumnConf'
		},

		templateString : template,
		_create : function(){
			this._super();
//			this._initCombobox();
		},
		/*_initCombobox : function() {
			app.utils.initComboboxByDict(this.isMappingCombobox,'IsMapping',{
				value : (this.options.item && this.options.item.isMapping !== undefined) ? this.options.item.isMapping : undefined,
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.typeCombobox,'Type',{
				value : (this.options.item && this.options.item.type !== undefined) ? this.options.item.type : undefined,
				isCheckAll : false,
				cached : true
			});
		}*/
	});
});
