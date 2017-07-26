define([ "text!./templates/extendedAttriDefinitionSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/extendedAttriDefinitionSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.extendedAttriDefinitionSearchBar", BaseSearchBar, {
		// default options
		options : {
			autoRender : true,
			searchBar : 'extendedAttriDefinition'
		},

		templateString : template,
		_create : function(){
			this._super();
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.extendedAttriTypeCombobox,'ExtendedAttri',{
				value : (this.options.item && this.options.item.type !== undefined) ? this.options.item.type : undefined,
				isCheckAll : false,
				cached : true
			});
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
