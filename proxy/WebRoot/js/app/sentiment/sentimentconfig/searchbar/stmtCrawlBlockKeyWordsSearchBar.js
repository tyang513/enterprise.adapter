define([ "text!./templates/stmtCrawlBlockKeyWordsSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/stmtCrawlBlockKeyWordsSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.stmtCrawlBlockKeyWordsSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'stmtCrawlBlockKeyWords'
		},

		templateString : template,
		_create : function(){
			this._super();
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.statusCombobox,'Status',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
				isCheckAll : false,
				cached : true
			});
		}
	});
});
