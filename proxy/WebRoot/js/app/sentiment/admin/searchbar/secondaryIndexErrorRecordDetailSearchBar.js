define([ "text!./templates/secondaryIndexErrorRecordDetailSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/admin/config/secondaryIndexErrorRecordDetailSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.secondaryIndexErrorRecordDetailSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'secondaryIndexErrorRecordDetail'
		},

		templateString : template,
		_create : function() {
			this._super();
			this._initData();
		},
		_initData : function() {
			//数据库数据保存为空，暂时不显示--赵帅兵20141013
			/*this.statusCombobox.combobox({
				valueField : 'status',
				textField : 'label',
				data : [ {
					label : '全部',
					status : ''
				},{
					label : '未处理',
					status : '0'
				}, {
					label : '已处理',
					status : '1'
				}]
			});*/
			
			this.operatorTypeCombobox.combobox({
				valueField : 'status',
				textField : 'label',
				data : [ {
					label : '全部',
					status : ''
				},{
					label : '增加',
					status : '0'
				}, {
					label : '删除',
					status : '1'
				}]
			});
		}
	});
});
