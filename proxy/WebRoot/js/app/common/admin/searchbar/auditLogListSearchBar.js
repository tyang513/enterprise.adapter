define([ "text!./templates/auditLogListSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.auditLogListSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			unselectMsg : '请选择一条记录',
			searchBar : 'auditLogList',
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
			app.utils.initComboboxByDict(this.operationtypecombobox, {
				dicName : 'auditOperationType',
				isCheckAll : true,
				cached : true
			});
			app.utils.initComboboxByDict(this.targettypecombobox, {
				dicName : 'auditTargetType',
				isCheckAll : true,
				cached : true
			});
		},
		
		render : function(){
			this._super();
			this._initData();
		},
		value : function() {
			this.logger.debug();
			return {
				actorname : $.trim(this.actorname.val()),
				operationtype : this.operationtypecombobox.combobox('getValue'),
				targettype : this.targettypecombobox.combobox('getValue'),
				createtime : this.createtimedatebox.datebox('getValue'),
				findCreatetime : this.findCreatetimedatebox.datebox('getValue')
				
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.actorname.val("");
			this.operationtypecombobox.combobox('setValue', '');
			this.targettypecombobox.combobox('setValue', '');
			this.createtimedatebox.datebox('setValue', '');
			this.findCreatetimedatebox.datebox('setValue', '');
			
		}

	});
});
