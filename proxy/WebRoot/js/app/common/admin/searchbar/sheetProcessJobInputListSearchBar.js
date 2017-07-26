define([ "text!./templates/sheetProcessJobInputListSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.sheetProcessJobInputListSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'sheetProcessJobInputList'
		},

		templateString : template,
		
		_create : function() {
			this._super();		
		},
		_initData : function() {
			this.logger.debug();
			app.utils.initComboboxByDict(this.sheettypecombobox, {
				dicName : 'ApplicationType',
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
				sheetid : $.trim(this.sheetid.val()),
				status: this.statuscombobox.combobox('getValue'),
				sheettype : this.sheettypecombobox.combobox("getValue")
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.sheetid.val("");
			this.statuscombobox.combobox('setValue','');
			this.sheettypecombobox.combobox('setValue', '');
		}
	
	});
});
