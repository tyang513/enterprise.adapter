({
	appDir : '../',
	baseUrl : './js',
	dir : '../../compressed',
	modules : [
//			{
//				name : 'zf/apploader',
//				exclude : [ "jquery" ]
//			},
			{
				name : 'app/_base/main',
				exclude : [ "jquery", "underscore", "accounting", "jqueryplugins/jquery.form", "zf/base/BaseWidget" ]
			},
			{
				name : 'app/_base/app',
				exclude : [ "jquery", "underscore", "ztree", "jqueryplugins/jquery.form", "zf/base/BaseWidget", "app/_base/constants", "app/_base/extension",
						"app/_base/messager", "app/_base/security", "app/_base/serviceLocator", "app/_base/utils" ]
			}
			//@tpl_insert@
//			,
//			{
//				name : 'app/_base/htmltpl'
//			},
//			{
//				name : 'app/_base/jsontpl'
//			}
			],
	fileExclusionRegExp : /(^(r|build|prebuild|postbuild|apploader.min|bootloader.min)\.js$)|(^\.)/,
	optimizeCss : 'standard',
//	removeCombined : true,
//	uglify : {
//		toplevel : true,
//		ascii_only : true,
//		beautify : true,
//		max_line_length : 1000,
//
//		// How to pass uglifyjs defined symbols for AST symbol replacement,
//		// see "defines" options for ast_mangle in the uglifys docs.
//		defines : {
//			DEBUG : [ 'name', 'false' ]
//		},
//
//		// Custom value supported by r.js but done differently
//		// in uglifyjs directly:
//		// Skip the processor.ast_mangle() part of the uglify call (r.js 2.0.5+)
//		no_mangle : true
//	},
	paths : {
		"zf" : "./zf",
		"app" : "./app",
		"jquery" : "./lib/jquery/jquery-1.10.2",
		"backbone" : "./lib/backbone/backbone",
		"accounting" : "./lib/accounting/accounting",
		"underscore" : "./lib/underscore/underscore",
		"underscoreplugins" : "./lib/underscore/plugins",
		"jqueryplugins" : "./lib/jquery/plugins",
		"jqueryui" : "./lib/jquery/ui",
		"easyui" : "./lib/easyui1.3.5/jquery.easyui.min",
		"easyuiplugins" : "./lib/easyui1.3.5/plugins",
		"easyuilocale" : "./lib/easyui1.3.5/locale",
		"json" : "./lib/json/json2",

		"ztree" : "./lib/ztree/jquery.ztree.all-3.5",
		"jquery.ui.widget" : "jqueryui/jquery.ui.widget",

		"jquery.fileupload" : "./lib/blueimp/jQuery-File-Upload/jquery.fileupload",
		"jquery.fileupload-process" : "./lib/blueimp/jQuery-File-Upload/jquery.fileupload-process",
		"jquery.fileupload-image" : "./lib/blueimp/jQuery-File-Upload/jquery.fileupload-image",
		"jquery.fileupload-audio" : "./lib/blueimp/jQuery-File-Upload/jquery.fileupload-audio",
		"jquery.fileupload-video" : "./lib/blueimp/jQuery-File-Upload/jquery.fileupload-video",
		"jquery.fileupload-validate" : "./lib/blueimp/jQuery-File-Upload/jquery.fileupload-validate",

		"jquery.fileupload-ui" : "./lib/blueimp/jQuery-File-Upload/jquery.fileupload-ui",
		"jquery.fileupload-jquery-ui" : "./lib/blueimp/jQuery-File-Upload/jquery.fileupload-jquery-ui",

		"load-image" : "./lib/blueimp/JavaScript-Load-Image/load-image",
		"load-image-meta" : "./lib/blueimp/JavaScript-Load-Image/load-image-meta",
		"load-image-exif" : "./lib/blueimp/JavaScript-Load-Image/load-image-exif",
		"load-image-ios" : "./lib/blueimp/JavaScript-Load-Image/load-image-ios",

		"tmpl" : "./lib/blueimp/JavaScript-Templates/tmpl",
		"canvas-to-blob" : "./lib/blueimp/Canvas-to-Blob/canvas-to-blob",

		"domReady" : "./lib/requirejs/plugins/domReady",
		"text" : "./lib/requirejs/plugins/text"
	},
	shim : {
		'backbone' : {
			// These script dependencies should be loaded before loading
			// backbone.js
			deps : [ 'underscore', 'jquery' ],
			// Once loaded, use the global 'Backbone' as the
			// module value.
			exports : 'Backbone'
		},
		'underscore' : {
			exports : '_'
		},
		'underscoreplugins/underscore.string.min' : {
			deps : [ 'underscore' ],
			exports : '_.string'
		},
		"jqueryplugins/jquery.channel" : [ "jquery" ],
		"jqueryplugins/jquery.form" : [ "jquery" ],

		"jqueryui/jquery.ui.core" : [ "jquery" ],
		"jqueryui/jquery.ui.widget" : [ "jqueryui/jquery.ui.core" ],

		"easyuiplugins/jquery.panel.custom" : [ "easyui" ],
		"easyuiplugins/jquery.pagination.custom" : [ "easyui" ],
		"easyuiplugins/jquery.datagrid.custom" : [ "easyui", "easyuiplugins/jquery.pagination.custom" ],
		"easyuiplugins/jquery.validatebox.custom" : [ "easyui" ],
		"easyuiplugins/jquery.calendar.custom" : [ "easyui" ],
		"easyuiplugins/jquery.datebox.custom" : [ "easyui", "easyuiplugins/jquery.calendar.custom" ],
		"easyuiplugins/jquery.datetimebox.custom" : [ "easyui", "easyuiplugins/jquery.datebox.custom" ],
		"easyuiplugins/datagrid-groupview" : [ "easyuiplugins/jquery.datagrid.custom" ],
		"easyuiplugins/datagrid-detailview" : [ "easyuiplugins/jquery.datagrid.custom" ],

		"easyuilocale/easyui-lang-zh_CN" : [ "easyui", "easyuiplugins/jquery.panel.custom", "easyuiplugins/jquery.datagrid.custom",
				"easyuiplugins/jquery.validatebox.custom", "easyuiplugins/jquery.datetimebox.custom" ],

		"jqueryui/jquery.ui.button" : [ "jqueryui/jquery.ui.widget" ],
		"jquery.fileupload-ui" : [ "jqueryui/jquery.ui.widget", "jqueryui/jquery.ui.button", "./lib/blueimp/jQuery-File-Upload/jquery.iframe-transport" ],

		"easyui" : [ "jquery" ],
		"ztree" : [ "jquery" ]
	}
})