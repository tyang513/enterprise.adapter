define([ "text!./templates/calendarListSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.calendarListSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'calendarList'
		},

		templateString : template,
		
		_create : function() {
			this._super();	
			this._initData();
		},
		_initData : function() {
			var myDate = new Date();
			var year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
			this.logger.debug();
			this.year.val(year);
		},
		value : function() {
			this.logger.debug();
			return {
				year : $.trim(this.year.val())
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.year.val("");
		}
	
	});
});
