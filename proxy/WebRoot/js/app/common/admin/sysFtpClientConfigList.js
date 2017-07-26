define([ "text!./templates/sysFtpClientConfigList.html", "app/base/BaseContentList", "app/common/admin/searchbar/sysFtpClientConfigSearchBar"], function(template, BaseContentList, searchBar) {
	return $.widget("app.sysFtpClientConfigList", BaseContentList, {
		// default options
		options : {
			entity : 'sysFtpClientConfig',
			entityName : 'FTP客户端配置表',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/common/admin/dialog/sysFtpClientConfigDialog'
			},
			formConfig : {
				url : 'app/common/admin/sysFtpClientConfigForm'
			}
		},

		templateString : template
	});
});
