define([ "text!./templates/sysErrorRecordDetailSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/sysErrorRecordDetailSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.sysErrorRecordDetailSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'sysErrorRecordDetail'
		},

		templateString : template,
		
		value : function(){
			this.logger.debug();
			var queryObj = this.searchForm.serializeObject();
			
			if(this.options.jobId){
				queryObj.jobId = this.options.jobId;
			}
			return queryObj;
		}
	});
});
