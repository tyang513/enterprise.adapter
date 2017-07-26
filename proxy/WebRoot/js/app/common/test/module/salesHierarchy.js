define([ "text!./templates/salesHierarchy.html", "zf/base/BaseWidget", "ztree" ], function(template, BaseWidget) {
	return $.widget("app.salesHierarchy", BaseWidget, {

		options : {},

		templateString : template,

		_create : function() {
			this._super();

			this._changes = [];
			this._initData();
		},

		_initData : function() {
			var setting = {
				edit : {
					enable : true,
					showRemoveBtn : false,
					showRenameBtn : false
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				callback : {
					beforeDrag : _.bind(this._beforeDrag, this),
					beforeDrop : _.bind(this._beforeDrop, this)
				}
			};

			var zNodes = [ {
				id : 1,
				pId : 0,
				name : "随意拖拽 1",
				open : true
			}, {
				id : 11,
				pId : 1,
				name : "随意拖拽 1-1"
			}, {
				id : 12,
				pId : 1,
				name : "随意拖拽 1-2",
				open : true
			}, {
				id : 121,
				pId : 12,
				name : "随意拖拽 1-2-1",
				drop : false
			}, {
				id : 122,
				pId : 12,
				name : "随意拖拽 1-2-2",
				drop : false
			}, {
				id : 123,
				pId : 12,
				name : "随意拖拽 1-2-3",
				drop : false
			}, {
				id : 13,
				pId : 1,
				name : "禁止拖拽 1-3",
				open : true,
				drag : false
			}, {
				id : 131,
				pId : 13,
				name : "禁止拖拽 1-3-1",
				drag : false
			}, {
				id : 132,
				pId : 13,
				name : "禁止拖拽 1-3-2",
				drag : false
			}, {
				id : 133,
				pId : 13,
				name : "随意拖拽 1-3-3"
			}, {
				id : 2,
				pId : 0,
				name : "随意拖拽 2",
				open : true
			}, {
				id : 21,
				pId : 2,
				name : "随意拖拽 2-1"
			}, {
				id : 22,
				pId : 2,
				name : "禁止拖拽到我身上 2-2",
				open : true,
				drop : false
			}, {
				id : 221,
				pId : 22,
				name : "随意拖拽 2-2-1",
				drop : false
			}, {
				id : 222,
				pId : 22,
				name : "随意拖拽 2-2-2",
				drop : false
			}, {
				id : 223,
				pId : 22,
				name : "随意拖拽 2-2-3",
				drop : false
			}, {
				id : 23,
				pId : 2,
				name : "随意拖拽 2-3",
				drop : false
			} ];

			$.fn.zTree.init($("#ztree_" + this.uuid), setting, zNodes);

			var zTree = $.fn.zTree.getZTreeObj("ztree_" + this.uuid);
			zTree.setting.edit.drag.prev = false;
			zTree.setting.edit.drag.inner = true;
			zTree.setting.edit.drag.next = false;
		},

		_buildChanges : function() {
			this.logger.debug();
			var datas = [];
			_.each(this._changes, function(change) {
				datas.push({
					id : change.treeNodes[0].id,
					pid : change.targetNode.id
				});
			});
			return datas;
		},

		_onSave : function(event) {
			this.logger.debug();
			var datas = this._buildChanges();
			console.dir([ 'datas', datas ]);
		},

		_beforeDrag : function(treeId, treeNodes) {
			this.logger.debug();
			console.dir([ treeId, treeNodes ]);
			for ( var i = 0, l = treeNodes.length; i < l; i++) {
				if (treeNodes[i].drag === false) {
					return false;
				}
			}
			return true;
		},

		_beforeDrop : function(treeId, treeNodes, targetNode, moveType) {
			this.logger.debug();
			var canDrop = targetNode ? (targetNode.drop !== false && moveType === 'inner') : true;
			if (targetNode && canDrop) {
				this._changes.push({
					treeId : treeId,
					treeNodes : treeNodes,
					targetNode : targetNode,
					moveType : moveType
				});
			}
			return canDrop;
		},

		_destroy : function() {
			delete this._changes;
			this._super();
		}
	});
});
