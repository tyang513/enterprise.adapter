define([ "text!./templates/mailTemplateManageSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.mailTemplateManageSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			unselectMsg : '请选择记录',

			grid : null,
			searchBar : 'mailTemplateManage',

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
				code : this.ipt_code.val(),
				systemcode: this.ipt_paramSubSystem.combobox('getValue')
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.ipt_code.val("");
			this.ipt_paramSubSystem.combobox('setValue','');
		}
	});
});
