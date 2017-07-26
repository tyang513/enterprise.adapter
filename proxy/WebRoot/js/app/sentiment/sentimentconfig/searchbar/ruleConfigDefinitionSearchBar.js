define([ "text!./templates/ruleConfigDefinitionSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/ruleConfigDefinitionSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.ruleConfigDefinitionSearchBar", BaseSearchBar, {
		// default options
		options : {
			autoRender : true,
			searchBar : 'ruleConfigDefinition'
		},
		templateString : template,
		
		hideEnable : function() {
			
		}
	});
});
