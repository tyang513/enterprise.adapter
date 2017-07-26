define([ "text!./templates/businessSystemListSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.businessSystemListSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'businessSystemList'
		},

		templateString : template,
		
		_create : function() {
			this._super();	
			this._initData();
		},
		_initData : function() {

		},		
		render : function(){
			this._super();
			this._initData();
		},
		value : function() {
			this.logger.debug();
			return {
				//year : $.trim(this.year.val())
			};
		},
		_onReset : function(event) {
			this.logger.debug();
		}
	
	});
});
