define([ "text!./templates/saleApproveChainDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.saleApproveChainDialog", BaseWidget, {
		// default options
		options : {
			dataChains : null,
			templateCode : null
		},

		templateString : template,

		_create : function() {
			this.approveChains = null;
			this._super();
			this._initData();
			
		},

		// -------------------interface------------------
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				var rowData = this.approveChains;
				if (callback && rowData) {
					callback(rowData);
				}
			} else if (eventId === "cancel") {
				if (callback) {
					callback();
				}
			}
		},

		_initData : function() {
			this.logger.debug();
			console.dir([ 'dataChains', this.options.dataChains ]);

			var gridInfo = this._buildGridInfo();
			this.chainGrid.datagrid(gridInfo);
			
			if (this.options.dataChains) {
				this.approveChains = this.options.dataChains;
				this.chainGrid.datagrid({
					data : this.options.dataChains
				});
			}
			
			if (this.options.chainTemplateMapSetp) {
				this.chainTemplateMapSetp = this.options.chainTemplateMapSetp;
			}
		},

		_buildGridInfo : function() {
			var _tcaa = this;
			var gridInfo = {};
			gridInfo = {
				idField : 'id',
				pagination : false,
				rownumbers : true,
				fit : true,
				fitColumns : true,
				singleSelect : true,
				columns : [ [ {
					field : 'chainid',
					hidden : true
				}, {
					field : 'approverumid',
					hidden : true
				}, {
					field : 'index',
					hidden : true
				}, {
					field : 'taskname',
					title : '任务名称',
					width : 50,
					sortable : true,
					halign : 'center',
					formatter:_.bind(this._formatTaskname, this)
				}, {
					field : 'approvername',
					title : '审批人',
					width : 35,
					sortable : true,
					halign : 'center',
					formatter : _.bind(this._formatApproverName, this)
				}] ]
			};
			return gridInfo;
		},

		_onClickUser : function(event) {
			this.logger.debug();
			var options = app.utils.parseOptions($(event));
			var index = options.index;
			// var linkBtn = this["chainStep" + index];

			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '选择用户',
				width : 600,
				height : 400,
				closed : false,
				cache : false,
				className : 'app/common/admin/dialog/userSearchDialog',
				modal : true,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						if(this.approveChains) {
							this.approveChains[index].approverumid = data.umid;
							this.approveChains[index].approvername = data.username;
							
							this.chainGrid.datagrid({
								data : this.approveChains
							});
						}
						
						
						// linkBtn.html(data.username);
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {

					}
				} ]
			});
		},

		_formatApproverName : function(value, row, index) {
			var approver = '';
			/*
				if (row.flag !== 'Y') {
					//approver = "<a href='javascript:void(0);' class='easyui-linkbutton' data-options=\"plain:true,index:" + index
					//+ "\" onClick=_onClickUser();>" + value + "</a>";
					return value;
				} else {
					approver = "<a href='javascript:void(0);' class='easyui-linkbutton' data-options=\"plain:true,index:" + index
					+ "\" onClick=_onClickUser();>选择审批人</a>";
				}
				*/
			
//			if (row.flag === 'Y') {
//				var approverName = "选择审批人";
//				if(value) {
//					approverName = value;
//				}
//				approver = "<a href='javascript:void(0);' class='easyui-linkbutton' data-options=\"plain:true,index:" + index
//				+ "\" onClick=_onClickUser();>" + approverName + "</a>";
//				return app.utils.bind(approver, this);
//			} else {
//				return value;
//			}
 
			if (this.options.chainTemplateMapSetp) { 
				if(this._getApproverumid(row.taskindex)) {
					approver = value;

				} else {
					approver = "<a style='color:red'  href='javascript:void(0);' class='easyui-linkbutton' data-options=\"plain:true,index:" + index
					+ "\" onClick=_onClickUser();>" + value + "</a>";

				}
			} else {
				approver = "<a style='color:red'  href='javascript:void(0);' class='easyui-linkbutton' data-options=\"plain:true,index:" + index
				+ "\" onClick=_onClickUser();>" + value + "</a>";

			} 
			return app.utils.bind(approver, this);
			
		},
		
		_getApproverumid: function(index) { 
			var chainsTemplate = this.options.chainTemplateMapSetp;
				for(var i=0; i<chainsTemplate.length;i++){
					if(chainsTemplate[i].taskindex == index) {
						return chainsTemplate[i].approverumid;
					}
				}
				return false; 
		},
		
		_formatTaskname:function(value, row, index){
			var taskname='';
			taskname=value.split('—')[0];
			return app.utils.bind(taskname, this);
		}
	});
});
