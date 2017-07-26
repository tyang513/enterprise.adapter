define([ "text!./templates/regionSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/regionSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.regionSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'region'
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
			app.utils.initComboboxByDict(this.typeCombobox,'RegionType',{
				value : (this.options.item && this.options.item.type !== undefined) ? this.options.item.type : undefined,
				isCheckAll : false,
				cached : true
			});
		}
	});
});
