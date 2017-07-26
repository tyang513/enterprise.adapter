define([ "text!./templates/showOaInstanceList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/showOaInstanceListSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.showOaInstanceList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
			this.dicts={};
		},
		_initData : function() {
			this.logger.debug();

			var dicLoaded = function(data) {
				this.dicts = data;				
				this.dataGrid.datagrid(app.buildServiceData("queryOaInstance", {
					queryParams : {}, // 查询条件
					loadMsg : '数据加载中请稍后……'
				}));
			};
			// 缓存字典数据
			app.utils.getDicts('TemplateType', _.bind(dicLoaded, this));
		},
		
		_getLabel : function(dictName, value){
			var label = null;
			var dict = this.dicts[dictName];
			for(var i=0; i<dict.length; i++){
				if(parseInt(dict[i].dicitemkey) === parseInt(value)){
					label = dict[i].dicitemvalue;
					break;
				}
			}
			return label;
		},

		_formaterTemplateType : function(value, row, index) {
			return this._getLabel('TemplateType', value);
		},
		// ---------- event handler -------------
		_doAction : function(event, data) {
			this.logger.debug();
			console.dir([ event, data ]);
			var handlerName = "_handle" + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');
		},
		_handleSearch : function(data) {
			this.logger.debug();
			var queryParams = this.searchBar.showOaInstanceListSearchBar('value');
			this.dataGrid.datagrid('options').queryParams = queryParams;
			this.dataGrid.datagrid('load');
		}
	});
});
