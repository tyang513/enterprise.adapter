define([ "text!./templates/showOaInstanceListSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.showOaInstanceListSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'showOaInstanceList'
		},

		templateString : template,
		
		_create : function() {
			this._super();		
		},
		_initData : function() {
			this.logger.debug();
			app.utils.initComboboxByDict(this.statuscombobox, {
				dicName : 'oaInstanceStatus',
				isCheckAll : true,
				cached : true
			});
			app.utils.initComboboxByDict(this.templatetypecombobox, {
				dicName : 'TemplateType',
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
				title : $.trim(this.title.val()),
				status: this.statuscombobox.combobox('getValue'),
				templatetype: this.templatetypecombobox.combobox('getValue'),
				starttime : this.starttime.datebox("getValue"),
				findStartTime : this.findStartTime.datebox("getValue"),
				endtime : this.endtime.datebox("getValue"),
				findEndTime : this.findEndTime.datebox("getValue")
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.title.val("");
			this.statuscombobox.combobox('setValue','');
			this.templatetypecombobox.combobox('setValue','');
			this.starttime.datebox('setValue', '');
			this.findStartTime.datebox('setValue', '');
			this.endtime.datebox('setValue', '');
			this.findEndTime.datebox('setValue', '');
		}
	
	});
});
