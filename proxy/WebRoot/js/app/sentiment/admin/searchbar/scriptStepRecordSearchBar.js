define([ "text!./templates/scriptStepRecordSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/scriptStepRecordSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.scriptStepRecordSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'scriptStepRecord'
		},

		templateString : template,
		
		render : function() {
			this._super();
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.typeCombobox, 'ScriptInfoType',{
				value : (this.options.item && this.options.item.type !== undefined) ? this.options.item.type : undefined,
				isCheckAll : false,
				cached : true
			});
		},
		
		value : function(){
			this.logger.debug();
			var queryObj = this.searchForm.serializeObject();
			
			if(this.options.scriptRunId){
				queryObj.scriptRunId = this.options.scriptRunId;
			}
			
			return queryObj;
		}
		
	});
});
