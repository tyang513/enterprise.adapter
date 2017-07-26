(function() {
	requirejs([ "jquery", "underscore",
			"underscoreplugins/underscore.string.min", "easyui",
			"easyuiplugins/jquery.panel.custom",
			"easyuiplugins/jquery.datagrid.custom",
			"easyuiplugins/datagrid-groupview",
			"easyuiplugins/datagrid-detailview",
			"easyuiplugins/jquery.validatebox.custom",
			"easyuiplugins/jquery.datetimebox.custom",
			"easyuilocale/easyui-lang-zh_CN", "json",
			"jqueryplugins/jquery.channel", "jqueryui/jquery.ui.widget",
			"accounting", "ztree", "fancybox", "jqueryplugins/jquery.form",
			"jquery.fileupload-jquery-ui", 
			"zf/_base/BaseWidget",
			"zf/_base/dialogExt", 
			"zf/base/BaseWidget" ], function() {
		// Define global app object
		window.app = window.app || {};

		// Load the main app module to start the app
		requirejs([ "app/_base/main" ]);
	});
})();