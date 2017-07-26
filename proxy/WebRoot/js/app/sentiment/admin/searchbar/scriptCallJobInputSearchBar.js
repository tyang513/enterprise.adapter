define([ "text!./templates/scriptCallJobInputSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/scriptCallJobInputSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.scriptCallJobInputSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'scriptCallJobInput'
		},

		templateString : template,
		
		render : function() {
			this._super();
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.statusCombobox, 'CallStatus',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.handleStatusCombobox, 'HandleStatus',{
				value : (this.options.item && this.options.item.handleStatus !== undefined) ? this.options.item.handleStatus : undefined,
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.scriptJobType, 'ScriptInfoType',{
				value : (this.options.item && this.options.item.type !== undefined) ? this.options.item.type : undefined,
				isCheckAll : true,
				cached : true
			});
		}
	});
});
