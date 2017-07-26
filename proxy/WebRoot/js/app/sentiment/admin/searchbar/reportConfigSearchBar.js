define([ "text!./templates/reportConfigSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/reportConfigSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.reportConfigSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'reportConfig'
		},
		templateString : template,
		_create : function(){
			this._super();
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.fileTypeCombobox,'FileType',{
				value : (this.options.item && this.options.item.fileType !== undefined) ? this.options.item.fileType : undefined,
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.emailTypeCombobox,'EmailType',{
				value : (this.options.item && this.options.item.emailType !== undefined) ? this.options.item.emailType : undefined,
				isCheckAll : false,
				cached : true
			});
		}
	});
});
