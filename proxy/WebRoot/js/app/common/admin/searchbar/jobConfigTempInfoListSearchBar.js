define([ "text!./templates/jobConfigTempInfoListSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.jobConfigTempInfoListSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'jobConfigTempInfoList'
		},

		templateString : template,
		
		_create : function() {
			this._super();		
		},
		_initData : function() {
			this.logger.debug();
			app.utils.initComboboxByDict(this.statuscombobox, {
				dicName : 'TempJobStatus',
				isCheckAll : true,
				cached : true
			});
		},
		render : function() {
			this._super();
			this._initData();
		},
		value : function() {
			this.logger.debug();
			return {
				jkey : $.trim(this.jkey.val()),
				status: this.statuscombobox.combobox('getValue'),
				fromStarttime : this.fromStarttime.datetimebox("getValue"),
				toStarttime : this.toStarttime.datetimebox("getValue")
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.jkey.val("");
			this.statuscombobox.combobox('setValue','');
			this.fromStarttime.datetimebox('setValue', '');
			this.toStarttime.datetimebox('setValue', '');
		}
	
	});
});
