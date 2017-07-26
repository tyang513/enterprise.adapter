define([ "jquery", "underscore", "accounting", "app/_base/extension", "app/_base/serviceLocator", "app/_base/constants", "app/_base/utils", "app/_base/messager",
		"app/_base/security", "app/_base/formatter" ], function($, _, accounting, ext, serviceLocator, constants, utils, messager, security, formatter) {
	$(function() {
		var app = window.app = window.app || {};
		app.config = {};
		app.config.dicts = {};
		app.config.dictmap = {};
		app.config.sysparams = {};
		app.config.keepLiveInterval = 10 * 60 * 1000;
		app.config.enableKeepLive = true;

		app.accounting = accounting;
		app.constants = constants;
		app.messager = messager;
		app.security = security;
		app.utils = utils;
		app.formatter = formatter;
		app.getJSON = _.bind(utils.getJSON, utils);
		app.getDictionary = _.bind(utils.getDictionary, utils);

		app.serviceLocator = serviceLocator;
		app.buildServiceData = _.bind(serviceLocator.buildServiceData, serviceLocator);

		$.ajaxSetup({
			cache : false, // ajax不缓存
			global : false
		// 默认不发送全局ajax事件
		});

		// 检测debug模式
		app.isDebug = (window.location.search.indexOf('?debug') > -1);

		app.workspace.init();

		app.ready = true;
		app.traceInfo.frameworkReadyTime = new Date();
		console.dir([ "Welcome to partner ", (app.traceInfo.frameworkReadyTime - app.traceInfo.startTime) + 'ms' ]);

		require([ "app/_base/htmltpl", "app/_base/jsontpl" ], function() {
			require([ "app/_base/jstpl" ], function() {
				app.traceInfo.allscriptReadyTime = new Date();
				console.dir([ "All script is ready!!!", (app.traceInfo.allscriptReadyTime - app.traceInfo.startTime) + 'ms' ]);
			});
		});

	});
});