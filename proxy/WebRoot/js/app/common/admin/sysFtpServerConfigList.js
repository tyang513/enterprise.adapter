define([ "text!./templates/sysFtpServerConfigList.html", "app/base/BaseContentList", "app/common/admin/searchbar/sysFtpServerConfigSearchBar"], function(template, BaseContentList, searchBar) {
	return $.widget("app.sysFtpServerConfigList", BaseContentList, {
		// default options
		options : {
			entity : 'sysFtpServerConfig',
			entityName : 'FTP服务器表',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/common/admin/dialog/sysFtpServerConfigDialog'
			},
			formConfig : {
				url : 'app/common/admin/sysFtpServerConfigForm'
			}
		},
		_handleEditPassword : function(data) {
			this._openChangePasswordDialog(data);
		},
		
		_openChangePasswordDialog : function(data) {
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '修改密码',
				width : 400,
				height : 200,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/admin/dialog/sysFtpServerConfigUpdatePasswordDialog',
				modal : true,
				data : data,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						dlg.dialogExt('destroy');
						this.dataGrid.datagrid('reload');
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt('destroy');
					}
				} ]
			});
		},
		templateString : template
	});
});
