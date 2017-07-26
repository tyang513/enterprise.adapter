define([ "text!./templates/approvalTemplateManageListSearchBar.html", "app/base/BaseSearchBar" ], function(template, BaseSearchBar) {
	return $.widget("app.approvalTemplateManageListSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'approvalTemplateManageList'
		},

		templateString : template,
		
		_create : function() {
			this._super();		
		},
		_initData : function() {
			this.logger.debug();
			app.utils.initComboboxByDict(this.statuscombobox, {
				dicName : 'oaTemplateStatus',
				isCheckAll : true,
				cached : true
			});

//			var systemcode = this.systemNamecombobox.combobox({
//				url : 'businessSystem/findSubSystem.do',
//				panelHeight : 'auto',
//				valueField : 'code',
//				textField : 'name'
////				onLoadSuccess : _.bind(function(){
////					this.systemNamecombobox.append("<option value=''>全部</option>"); 
////				},this)
//			});
			$.ajax({
		 		url : 'businessSystem/findSubSystem.do',
		 		dataType : 'json',
		 		data : {},
		 		cache : true,
		 		success : _.bind(function(data) {
		 			var result = [];
		 			result.push({text:"全部",value:""});
		 			for(var i=0; i<data.length; i++){
		 				result.push({text:data[i].name,value:data[i].code});
					}
		 			this.systemNamecombobox.combobox("loadData",result);
		 			
		 		},this)
		 	});
			
		},
		render : function() {
			this._super();
			this._initData();
		},
		value : function() {
			this.logger.debug();
			return {
				code : $.trim(this.code.val()),
				name : $.trim(this.name.val()),
				status: this.statuscombobox.combobox('getValue'),
				systemcode : this.systemNamecombobox.combobox("getValue")
			};
		},
		_onReset : function(event) {
			this.logger.debug();
			this.code.val("");
			this.name.val("");
			this.statuscombobox.combobox('setValue','');
			this.systemNamecombobox.combobox('setValue', '');
		}
	
	});
});
