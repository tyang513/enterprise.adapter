define([ "text!./templates/stmtCrawlKeyWordsDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.stmtCrawlKeyWordsDialog", BaseDialog, {
		// default options
		options : {
			entity : 'stmtCrawlKeyWords'
		},

		templateString : template,
		_initData : function() {
			this._initCombobox();
				if(this.options.action === app.constants.ACT_EDIT || this.options.action === app.constants.ACT_VIEW) {
					this.category.combotree('setValue',this.options.item.categoryName);
					this.keyWord.val(this.options.item.keyWord);
					this.categoryId.val(this.options.item.categoryId);
					this.id.val(this.options.item.id);
					this.categoryName.val(this.options.item.categoryName);
				} 
		
			if(this.options.action === app.constants.ACT_VIEW){
				this.editForm.find("input").attr("readonly", true);
				this.statusCombobox.combobox({ readonly: true }); 
				this.category.combotree("disable"); 
				this.categoryCode.val(this.options.item.categoryCode);
				this.code.show();
				
			}
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.statusCombobox, 'status',{
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
					this.categoryId.val(data.id);
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