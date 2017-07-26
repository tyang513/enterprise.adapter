define([ "text!./templates/sysParamSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.sysParamSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			unselectMsg : '请选择记录',

			grid : null,
			searchBar : 'sysParam',

			condition : undefined
		},

		templateString : template,

		_create : function() {
			this._super();
			this._initData();
			
		},
		_initData : function() {
			//参数所属子系统
			this.ipt_paramSubSystem.combobox({
				url : 'businessSystem/getAll.do',
				panelHeight : 'auto',
				valueField : 'code',
				textField : 'name',
				onSelect : _.bind(function (record){
				}
			,this)
			});	
		},
		// ----------------interface---------------------
		value : function() {
			this.logger.debug();
			return {
				paramkey : $.trim(this.ipt_paramEngName.val()),
				paramvalue : $.trim(this.ipt_paramValue.val()),
				systemcode: this.ipt_paramSubSystem.combobox('getValue')
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.ipt_paramEngName.val("");
			this.ipt_paramValue.val("");
			this.ipt_paramSubSystem.combobox('setValue','');
		}
	});
});
