define([ "text!./templates/dicManageSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.dicManageSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			unselectMsg : '请选择记录',

			grid : null,
			searchBar : 'dicManage',

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
				id : $.trim(this.ipt_id.val()),
				name : $.trim(this.ipt_name.val()),
				description : $.trim(this.ipt_description.val()),
				systemcode: this.ipt_paramSubSystem.combobox('getValue')
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.ipt_id.val("");
			this.ipt_name.val("");
			this.ipt_description.val("");			
			this.ipt_paramSubSystem.combobox('setValue','');
		}
	});
});
