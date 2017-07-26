define([ "text!./templates/showTakeoverListSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.showTakeoverListSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			unselectMsg : '请选择一条记录',
			searchBar : 'showTakeoverList',
			grid : null,
			status : undefined
		},

		templateString : template,

		_create : function() {
			this._super();			
		},
		_refresh : function() {
			this._super();
			if (this.options.status !== undefined) {
				if (this.options.status != 0) {
					this.element.find("a").hide();
					this.element.find("a[data-options*='view']").show();
				}
			}
		},
		_initData : function() {
			this.logger.debug();			
			
		},
		
		render : function(){
			this._super();
			this._initData();
		},
		value : function() {
			this.logger.debug();
			return {
				userName : $.trim(this.name.val()),
				targetUserName : $.trim(this.takeovername.val()),
				startTime : this.starttime.datebox('getValue'),
				endTime : this.endtime.datebox('getValue')
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.name.val("");
			this.takeovername.val("");
			this.starttime.datebox('setValue', '');
			this.endtime.datebox('setValue', '');
		}

	});
});
