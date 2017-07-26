define([ "text!./templates/stmtCrawlKeyWordsSearchBar.html", "app/base/BaseSearchBar", "text!app/sentiment/sentimentconfig/config/stmtCrawlKeyWordsSearchBar.json"], function(template, BaseSearchBar,searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.stmtCrawlKeyWordsSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'stmtCrawlKeyWords'
			},
		

		templateString : template,
		_create : function(){
			this._super();
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.statusCombobox,'Status',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
				isCheckAll : false,
				cached : true
			});
			this.category.combotree({
				url : 'category/querySmallCategory2Tree.do',  
				editable: false, 
				selected : false,
				formatter : function(node) {
					return node.name;
				},
				onSelect:_.bind(function(data){
					var smallCategoryArray=[];
					var smallCategoryNameArray=[];
					this._setSmallCategoryArray(data,smallCategoryArray,smallCategoryNameArray);
					console.log(data);
					this.categoryName.val(smallCategoryNameArray[0]);
					this.smallCategoryArray=smallCategoryArray;
				},this),
				loadFilter : _.bind(function(data) {
					this._buildTreeNodes(data);
					return data;
				}, this)
			});
			
		},
		_setSmallCategoryArray:function(data,smallCategoryArray,smallCategoryNameArray){
			smallCategoryArray.push(data.id);
			smallCategoryNameArray.push(data.name)
			if(data.children!=null&&data.children.length!=0){
				for(var temp in data.children){
					this._setSmallCategoryArray(data.children[temp],smallCategoryArray);
					this._setSmallCategoryArray(data.children[temp],smallCategoryNameArray);
				}
			}
		},
		
		_buildTreeNodes : function(nodes) {
			for (var i = 0; i < nodes.length; i++) {
				nodes[i].text = nodes[i].name;
				nodes[i].id = nodes[i].id;
				if(nodes[i].children) { 
					this._buildTreeNodes(nodes[i].children);
				}
			}
		}

	});
});
