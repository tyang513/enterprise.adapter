define([ "text!./templates/templateSearchDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.templateSearchDialog", BaseDialog, {
		// default options
		options : {},

		templateString : template,

		_create : function() {
			this._super();
			this._initData();
		},

		// -------------------interface------------------
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				var rowData = this.templateListGrid.datagrid("getSelected");
				if (callback && rowData) {
					callback(rowData);
				}
			} else if (eventId === "cancel") {
				if (callback) {
					callback();
				}
			}
		},


		_saveData  : function(callback, errorback) {
			 var data = this.templateListGrid.datagrid('getSelected');
			   if (data == null) {
			    app.messager.warn("请选择一行数据");
			    return ;
			   }
			   $.ajax(app.buildServiceData("templateCategoryRel/save.do", {
					data : {
						categoryId : this.options.categoryId,
						templateCode : data.templateCode
					},
					type : 'POST',
					context : this,
					global : true,
					success : function(data) {
						if (data) {
							 if(data.success){
								 app.messager.info(data.msg);
							 }else{
								 app.messager.warn(data.msg);
							 }
						}
						callback && callback(data);
					},
					error : errorback
				}));
			
		},
		_initData : function() {
			this.logger.debug();
			this.templateListGrid.datagrid(app.buildServiceData("template/list.do", {
				/*queryParams : {
					partnerId:this.options.partnerId
				},*/
				onLoadSuccess : _.bind(function(rowData){
					
				},this)
			}));
			app.utils.bindDatagrid(this, this.templateListGrid);
			
		},
		execute : function(eventId, callback) {
			if (eventId === app.constants.BTN_OK) {
				this._saveData(callback, this.options.error);
			} else if (eventId === app.constants.BTN_CANCEL) {
				if (callback) {
					callback();
				}
			}
		}
		
		
	});
});
