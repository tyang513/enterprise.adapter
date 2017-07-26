define([ "text!./templates/searchBarTest.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.searchBarTest", BaseSearchBar, {
		// default options
		options : {
			enableFilter : false,
			searchBar : "test"
		},
		templateString : template,

		// the constructor
		_create : function() {
			this._super();		
			
		},
		_destroy : function() {
			this._super();
		}
	});
});
