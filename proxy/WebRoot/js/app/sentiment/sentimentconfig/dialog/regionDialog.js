define([ "text!./templates/regionDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.regionDialog", BaseDialog, {
		// default options
		options : {
			entity : 'region'
		},

		templateString : template,
		_initData : function(){
			this.editForm.form("load", this.options.item);
			if(this.options.action === app.constants.ACT_VIEW){
				this.parent.hide();
				this.editForm.find("input").attr("readonly", true);
				this.typeCombobox.combobox({ disabled: true });
				this.statusCombobox.combobox({ disabled: true });
				this.parentName.combobox({ disabled: true });
			}
			this._initCombobox();
		},
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.typeCombobox, 'RegionType',{
				value : (this.options.item && this.options.item.type !== undefined) ? this.options.item.type : undefined,
				isCheckAll : false,
				cached : true
			});
			app.utils.initComboboxByDict(this.statusCombobox, 'status',{
				value : (this.options.item && this.options.item.status !== undefined) ? this.options.item.status : undefined,
				isCheckAll : false,
				cached : true
			});
			this.parentName.combobox({
			    mode: 'remote',
			    url: 'region/queryAllRegion.do',
			    valueField: 'id',
			    textField: 'name',
			    value: (this.options.item && this.options.item.parentId !== undefined) ? this.options.item.parentId : undefined,
			    fitColumns:true,
			    required: true,
			    missingMessage: "请选择省份",
			    onSelect : _.bind(function(data) {
					this.parentName.val(data.name);
					this.parentId.val(data.id);
					this._loadRegionName(data.id);
					return data;
				}, this)
			});
		},
		_loadRegionName: function(parentId){
			this.nameCombobox.combobox({
				url : 'region/queryRegionByParentId.do?parentId='+parentId, 
				editable: false, 
				selected : false,
				required: true,
			    missingMessage: "请选择城市",
				formatter : function(node) {
					return node.name;
				},
				loadFilter : _.bind(function(data) {
					this._buildTreeNodes(data);
					return data;
				}, this) 
			});
		},
		
		_buildTreeNodes : function(nodes) {
			for (var i = 0; i < nodes.length; i++) {
				nodes[i].text = nodes[i].name;
				
				if(nodes[i].children) { 
					this._buildTreeNodes(nodes[i].name);
				}
			}
		}
		
		
	});
});