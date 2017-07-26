define([ "text!./templates/hadoopJobProgressSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/hadoopJobProgressSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.hadoopJobProgressSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'hadoopJobProgress'
		},
		templateString : template,
		
		render : function() {
			this._super();
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.taskNameCombobox, 'ProgressTaskName',{
				value : (this.options.item && this.options.item.taskName !== undefined) ? this.options.item.taskName : undefined,
				isCheckAll : false,
				cached : true
			});
			
			app.utils.initComboboxByDict(this.jobTypeCombobox, 'ProgressJobType',{
				value : (this.options.item && this.options.item.jobType !== undefined) ? this.options.item.jobType : undefined,
				isCheckAll : false,
				cached : true
			});
		}
	});
});
