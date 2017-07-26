define([ "text!./templates/templateSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/templateSearchBar.json"], function(template, BaseSearchBar,searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.templateSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'template'
			},
		

		templateString : template,
		_create : function(){
			this._super();
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.mediaSourceCombobox,'MediaSource',{
				value : (this.options.item && this.options.item.mediaSource !== undefined) ? this.options.item.mediaSource : undefined,
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.statusCombobox,'Status',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
				isCheckAll : false,
				cached : true
			});
			this.effectDateInfo.datebox({
				onSelect: _.bind(function(date){
					this.expiryDateInfo.datebox('calendar').calendar({
						minDate : date
					});
					minDate : date
					
				},this)
			});
			this.expiryDateInfo.datebox({
				onSelect: _.bind(function(date){
					date.setDate(date.getDate()+1);
					this.effectDateInfo.datebox('calendar').calendar({
						maxDate : date
					});
					maxDate : date
					
				},this)
			});
		}
		

	});
});
