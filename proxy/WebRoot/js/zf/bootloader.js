(function() {
	// Place third party dependencies in the lib folder
	//
	// Configure loading modules from the lib directory,
	// except 'app' ones,
	requirejs
			.config({
				baseUrl : 'js',
				waitSeconds : 60,
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
					"easyui" : "./lib/easyui/jquery.easyui.min",
					"easyuiplugins" : "./lib/easyui1.3.5/plugins",
					"easyuilocale" : "./lib/easyui/locale",
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

					"bootstrap" : "./lib/bootstrap/bootstrap.min",
//					"echarts" : "./lib/echarts2.2.0/echarts-all",
					
					"domReady" : "./lib/requirejs/plugins/domReady",
					"text" : "./lib/requirejs/plugins/text"
				},
				shim : {
					'backbone' : {
						// These script dependencies should be loaded before
						// loading
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
					"jqueryui/jquery.ui.button" : [ "jqueryui/jquery.ui.widget" ],
					"jquery.fileupload-ui" : [ "jqueryui/jquery.ui.widget",
							"jqueryui/jquery.ui.button",
							"./lib/blueimp/jQuery-File-Upload/jquery.iframe-transport" ],

					// ----------easyui----------
					// Base
					"easyuiplugins/jquery.pagination" : [ "jquery",
							"easyuiplugins/jquery.linkbutton" ],
					"easyuiplugins/jquery.searchbox" : [ "jquery",
							"easyuiplugins/jquery.menubutton" ],

					// Layout
					"easyuiplugins/jquery.tabs" : [ "jquery",
							"easyuiplugins/jquery.linkbutton",
							"easyuiplugins/jquery.panel.custom" ],
					"easyuiplugins/jquery.accordion" : [ "jquery",
							"easyuiplugins/jquery.panel.custom" ],
					"easyuiplugins/jquery.layout" : [ "jquery",
							"easyuiplugins/jquery.resizable",
							"easyuiplugins/jquery.panel.custom" ],

					// Menu and Button
					"easyuiplugins/jquery.menubutton" : [ "jquery",
							"easyuiplugins/jquery.menu",
							"easyuiplugins/jquery.linkbutton" ],
					"easyuiplugins/jquery.splitbutton" : [ "jquery",
							"easyuiplugins/jquery.menubutton" ],

					// Form
					"easyuiplugins/jquery.validatebox.custom" : [ "jquery",
							"easyuiplugins/jquery.tooltip" ],
					"easyuiplugins/jquery.combo" : [ "jquery",
							"easyuiplugins/jquery.validatebox.custom",
							"easyuiplugins/jquery.panel.custom" ],
					"easyuiplugins/jquery.combobox" : [ "jquery",
							"easyuiplugins/jquery.combo" ],
					"easyuiplugins/jquery.combotree" : [ "jquery",
							"easyuiplugins/jquery.combo",
							"easyuiplugins/jquery.tree" ],
					"easyuiplugins/jquery.combogrid" : [ "jquery",
							"easyuiplugins/jquery.combo",
							"easyuiplugins/jquery.datagrid.custom" ],
					"easyuiplugins/jquery.numberbox" : [ "jquery",
							"easyuiplugins/jquery.validatebox.custom" ],
					"easyuiplugins/jquery.datebox.custom" : [ "jquery",
							"easyuiplugins/jquery.combo",
							"easyuiplugins/jquery.calendar.custom" ],
					"easyuiplugins/jquery.datetimebox.custom" : [ "jquery",
							"easyuiplugins/jquery.datebox.custom",
							"easyuiplugins/jquery.timespinner" ],
					"easyuiplugins/jquery.spinner" : [ "jquery",
							"easyuiplugins/jquery.validatebox.custom" ],
					"easyuiplugins/jquery.numberspinner" : [ "jquery",
							"easyuiplugins/jquery.spinner",
							"easyuiplugins/jquery.numberbox" ],
					"easyuiplugins/jquery.timespinner" : [ "jquery",
							"easyuiplugins/jquery.spinner" ],
					"easyuiplugins/jquery.slider" : [ "jquery",
							"easyuiplugins/jquery.draggable" ],

					// Window
					"easyuiplugins/jquery.window" : [
							"easyuiplugins/jquery.draggable",
							"easyuiplugins/jquery.resizable",
							"easyuiplugins/jquery.panel.custom" ],
					"easyuiplugins/jquery.dialog" : [
							"easyuiplugins/jquery.window",
							"easyuiplugins/jquery.linkbutton" ],
					"easyuiplugins/jquery.messager" : [
							"easyuiplugins/jquery.window",
							"easyuiplugins/jquery.linkbutton",
							"easyuiplugins/jquery.progressbar" ],

					// DataGrid and Tree
					"easyuiplugins/jquery.datagrid.custom" : [ "jquery",
							"easyuiplugins/jquery.panel.custom",
							"easyuiplugins/jquery.resizable",
							"easyuiplugins/jquery.linkbutton",
							"easyuiplugins/jquery.pagination" ],
					"easyuiplugins/jquery.propertygrid" : [ "jquery",
							"easyuiplugins/jquery.datagrid.custom" ],
					"easyuiplugins/datagrid-groupview" : [ "jquery",
							"easyuiplugins/jquery.datagrid.custom" ],
					"easyuiplugins/datagrid-detailview" : [ "jquery",
							"easyuiplugins/jquery.datagrid.custom" ],
					"easyuiplugins/jquery.tree" : [ "jquery",
							"easyuiplugins/jquery.draggable",
							"easyuiplugins/jquery.droppable" ],
					"easyuiplugins/jquery.treegrid" : [ "jquery",
							"easyuiplugins/jquery.datagrid.custom" ],

					// Local
					"easyuilocale/easyui-lang-zh_CN" : [
							"easyuiplugins/jquery.validatebox.custom",
							"easyuiplugins/jquery.combobox",
							"easyuiplugins/jquery.datetimebox.custom",
							"easyuiplugins/jquery.dialog",
							"easyuiplugins/jquery.messager",
							"easyuiplugins/jquery.datagrid.custom",
							"easyuiplugins/datagrid-groupview",
							"easyuiplugins/datagrid-detailview" ],

					"easyui" : [ "jquery" ],
					"ztree" : [ "jquery" ],

					"zf/base/BaseWidget" : [ "zf/_base/BaseWidget" ]
				}
			});
	
	//压缩bootloader.js时，注释如下代码 --begin//
	requirejs([ "jquery", "easyuiplugins/jquery.parser",
				"easyuiplugins/jquery.resizable",
				"easyuiplugins/jquery.layout",
				"easyuiplugins/jquery.tabs",
				"easyuiplugins/jquery.menubutton"]);
	//压缩bootloader.js时，注释如下代码 --end//
})();