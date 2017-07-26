define([ "text!./templates/ruleDefinitionSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/ruleDefinitionSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.ruleDefinitionSearchBar", BaseSearchBar, {
		// default options
		options : {
			searchBar : 'ruleDefinition',
			autoRender : true
		},

		templateString : template,
		
		_create : function(){
			this._super();
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.businessTypeCombobox, 'RuleBusinessType',{
				value : (this.options.item && this.options.item.businessType !== undefined) ? this.options.item.businessType : undefined,
				isCheckAll : true,
				cached : true
			});
			app.utils.initComboboxByDict(this.statusCombobox, 'Status',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
				isCheckAll : true,
				cached : true
			});
			
		},
		
		/*
		 * Status
		 * :0，未启用
			1，启用
			-1，禁用
		 */
		onGridClickRow : function(rowIndex, rowData) {
			this.logger.debug();
			console.dir([rowIndex, rowData]);
			if (rowData.status === "1") {
				this.enableBtn.text("禁用");
			} else {
				this.enableBtn.text("启用");
			}
			
		},
		
		hideEnable : function() {
			// 当前用户非管理员角色，且为结算查看岗时，只读
			/*if(!app.utils.isPlatformAdmin() && !app.utils.isSupperAdmin()) {
				if(app.utils.isSettleView()) {
					if(this.createBtn != undefined) {
						this.createBtn.hide();
					}
					if(this.editBtn != undefined) {
						this.editBtn.hide();
					}
					if(this.deleteBtn != undefined) {
						this.deleteBtn.hide();
					}
					if(this.enableBtn != undefined) {
						this.enableBtn.hide();
					}
					if(this.leadInBtn != undefined) {
						this.leadInBtn.hide();
					}
					if(this.leadOutBtn != undefined) {
						this.leadOutBtn.hide();
					}
				}
			}*/
		}
	});
});
