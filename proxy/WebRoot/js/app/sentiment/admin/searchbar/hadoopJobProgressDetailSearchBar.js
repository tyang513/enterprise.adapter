define([ "text!./templates/hadoopJobProgressDetailSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/hadoopJobProgressDetailSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.hadoopJobProgressDetailSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'hadoopJobProgressDetail'
		},
		templateString : template,
		
		render : function() {
			this._super();
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.executeTypeCombobox, 'ProgressExecuteType',{
				value : (this.options.item && this.options.item.executeType !== undefined) ? this.options.item.executeType : undefined,
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.resultTypeCombobox, 'ProgressResultType',{
				value : (this.options.item && this.options.item.resultType !== undefined) ? this.options.item.resultType : undefined,
				isCheckAll : false,
				cached : true
			});
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
			app.utils.initComboboxByDict(this.isInsertCombobox, 'ProgressIsInsert',{
				value : (this.options.item && this.options.item.isInsert !== undefined) ? this.options.item.isInsert : undefined,
				isCheckAll : false,
				cached : true
			});
		}
	});
});
