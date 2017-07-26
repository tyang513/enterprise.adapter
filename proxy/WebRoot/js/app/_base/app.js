define([], function() {
	var isIE = !!/msie [\w.]+/.exec(navigator.userAgent.toLowerCase());
	if (isIE) {
		var _nullFn = function() {
		};

		window.console = $.extend({
			debug : _nullFn,
			log : _nullFn,
			info : _nullFn,
			warn : _nullFn,
			error : _nullFn,
			dir : _nullFn
		}, window.console || {});
	}

	// 延时加载资源，加载完成后，app.ready值为true
	requirejs([ "zf/apploader.min" ]);

	var loadCSS = function(cssUrl) {
		var link = document.createElement('link');
		link.type = 'text/css';
		link.rel = 'stylesheet';
		link.href = cssUrl;
		document.getElementsByTagName("head")[0].appendChild(link);
	};

	loadCSS("css/app/themes/orange/default.css");

	$(function() {
		$.parser.parse();

		var accordionMenus = $(".leftsidebar").children();
		$(accordionMenus[0]).show();

		var parseOptions = function(target) {
			var s = '{' + $.trim(target.attr('data-options')) + '}';
			return (new Function('return ' + s))();
		};

		var tabsHelper = function(tabs, method, options) {
			var aaa = options.className.split("#");
			var anchor = aaa[1];
			var fullName = aaa[0];
			var aa = fullName.split("/");
			var shortName = aa[aa.length - 1];
			require([ fullName ], function() {
				delete options.className;
				delete options.url;
				options.anchor = anchor;
				var tab = tabs.tabs("add", options).tabs('getSelected');

				app.workspace.tabContainer.children('.tabs-header').children('.tabs-wrap').children('.tabs').children('ul li').unbind('contextmenu');
				/* 为选项卡绑定右键 */
				app.workspace.tabContainer.children('.tabs-header').children('.tabs-wrap').children('.tabs').children('ul li').on('contextmenu', function(e) {
					/* 选中当前触发事件的选项卡 */
					var subtitle = $(this).text();
					$('.easyui-tabs').tabs('select', subtitle);

					// 显示快捷菜单
					$('#menu').menu('show', {
						left : e.pageX,
						top : e.pageY
					});

					return false;
				});

				if (tab && tab[shortName] && _.isFunction(tab[shortName])) {
					tab[shortName](options);
					tab.addClass("app-" + shortName);
				}
			});
		};

		// 绑定topmenu事件
		$(".topmenu a").click(function(e) {
			var options = parseOptions($(e.currentTarget));
			accordionMenus.hide();
			$(accordionMenus[options.index]).show();
		});

		// 绑定topmenu事件
		$(".leftsidebar_content ul>li>a").click(function(e) {
			$(".leftsidebar_content ul>li>a").removeClass("curSelectedNode");

			var menu = $(e.currentTarget);
			menu.addClass("curSelectedNode");

			var options = parseOptions(menu);
			if (options.className) {
				app.workspace.openTab(options);
			} else {
				if (options.childrenlen > 0) {

					var submenu = menu.parent().next();
					submenu.toggle();
				} else {
					console.dir([ "没有配置菜单" ]);
				}
			}
		});

		var convertUrl = function(url) {
			return url;
		};

		var app = window.app = window.app || {};
		app.workspace = {
			tabContainer : $(".easyui-tabs"),
			openTab : function(resource) {
				console.dir([ "resource", resource ]);
				if (!app.ready) {
					console.warn("app is not ready, please try it again!!!");
				}
				if (resource.className) {
					resource.className = convertUrl(resource.className);
				}

				var url = resource.className;
				var isDialog = _.str.startsWith(url, "dialog:");
				if (isDialog) {
					url = url.substring(7);
				}
				var aa = url.split("#");
				var params = "";
				if (aa.length > 1) {
					params = aa[1];
				}
				url = aa[0];
//				url = "app/common/admin/dialog/changeUserPasswordDialog";				
				if (isDialog) {
					var width = app.utils.getQueryStringFormUrl(params, 'width') || 800;
					var height = app.utils.getQueryStringFormUrl(params, 'height') || 600;
					
					console.dir(["dialogInfo", isDialog, params, url, resource.className]);

					var dlg = $("<div>").dialogExt({
						title : resource.title,
						width : width,
						height : height,
						closed : false,
						autoClose : false,
						cache : false,
						className : url,
						modal : true,
						data : {},
						buttons : [ {
							text : '确认',
							eventId : "ok",
							handler : _.bind(function(result) {
								dlg.dialogExt('destroy');
							}, this)
						}, {
							text : '取消',
							eventId : "cancel",
							handler : function() {
								dlg.dialogExt('destroy');
							}
						} ]
					});
				} else {
					if (this.tabContainer.tabs("exists", resource.title)) {
						this.tabContainer.tabs("select", resource.title);
					} else {
						resource.closable = resource.closable || true;
						tabsHelper(this.tabContainer, "add", resource);
					}
				}
			},

			closeTab : function() {
				var selectedPanel = this.tabContainer.tabs("getSelected");
				var selectTitle = selectedPanel.panel("options").title;
				this.tabContainer.tabs("close", selectTitle);
			},

			_onSelect : function(title, index) {
				var tab = this.tabContainer.tabs('getTab', index);
				var widgetId = tab.attr('wid');
				if (widgetId) {
					var widgetClass = widgetId.split('_')[0];
					var widget = tab.data(widgetClass);
					if (widget.refresh && _.isFunction(widget.refresh)) {
						widget.refresh();
					}
				}
			},

			_handleOpenTab : function(event) {
				var resource = event.data;
				resource.className = resource.url;
				resource.closable = resource.closable || true;
				this.openTab(resource);
			},

			_handleCloseTab : function(event) {
				this.closeTab();
			},

			init : function() {
				$.subscribe("app/openTab", _.bind(this._handleOpenTab, this));
				$.subscribe("app/closeTab", _.bind(this._handleCloseTab, this));
				app.workspace.tabContainer.tabs({
					onSelect : _.bind(this._onSelect, this)
				});

				// 修改密码
				$(".control_menus div :eq(0)").click(function(e) {
					// 打开对话框
					var dlg = $("<div>").dialogExt({
						title : '修改密码',
						width : 300,
						height : 190,
						closed : false,
						autoClose : false,
						cache : false,
						className : 'app/common/admin/dialog/changeUserPasswordDialog',
						modal : true,
						data : {
							submituserumid : app.userInfo.user.loginName
						},
						buttons : [ {
							text : '确认',
							eventId : "ok",
							handler : _.bind(function() {
								dlg.dialogExt('destroy');
							}, this)

						}, {
							text : '取消',
							eventId : "cancel",
							handler : function() {
								dlg.dialogExt('destroy');
							}
						} ]

					});
				});

				// 临时授权
				// $(".control_menus div :eq(1)").click(function(e) {
				// // 打开对话框
				// var dlg = $("<div>").dialogExt({
				// title : '临时授权',
				// width : 700,
				// height : 450,
				// closed : false,
				// autoClose : false,
				// cache : false,
				// className : 'app/admin/dialog/temporaryAuthorizeDialog',
				// modal : true,
				// data : {
				// submituserumid : app.userInfo.user.loginName
				// }
				// });
				// });

				$(document).bind("ajaxSend", function(event) {
					console.log("ajaxSend ...");
					app.workspace.tabContainer.mask('处理中，请稍后……');
				}).bind("ajaxComplete", function(event) {
					console.log("ajaxComplete ...");
					app.workspace.tabContainer.unmask();
				});

				// 获取用户信息
				$.ajax(app.buildServiceData("getUserInfo", {
					data : {},
					success : function(data) {
						if (data) {
							app.userInfo = data;
						}
					},
					error : function(error) {
						app.messager.error('获取用户信息异常asdf！');
					}
				}));

				// 处理自动打开流程审批界面
				app.utils.processAction();

				// start keep live
				if (app.config.enableKeepLive) {
					console.log("start keep live!!!");
					app.utils.startKeepLive();
				}
			}
		};

		// 关闭所有
		$("#m-closeall").click(function() {
			app.workspace.tabContainer.children('.tabs-header').children('.tabs-wrap').children('.tabs').children('ul li').each(function(i, n) {
				var title = $(n).text();
				if (title != "欢迎")
					app.workspace.tabContainer.tabs('close', title);
			});
		});

		// 除当前之外关闭所有
		$("#m-closeother").click(function() {
			var currTab = app.workspace.tabContainer.tabs('getSelected');
			currTitle = currTab.panel('options').title;

			app.workspace.tabContainer.children('.tabs-header').children('.tabs-wrap').children('.tabs').children('ul li').each(function(i, n) {
				var title = $(n).text();

				if (currTitle != title && title != "欢迎") {
					app.workspace.tabContainer.tabs('close', title);
				}
			});
			app.workspace.tabContainer.tabs('select', currTitle);
		});

		// 关闭当前
		$("#m-close").click(function() {
			var currTab = app.workspace.tabContainer.tabs('getSelected');
			currTitle = currTab.panel('options').title;
			if (currTitle != "欢迎")
				app.workspace.tabContainer.tabs('close', currTitle);
		});

		app.traceInfo.uiReadyTime = new Date();
		console.dir([ "UI is ready!!!", (app.traceInfo.uiReadyTime - app.traceInfo.startTime) + 'ms' ]);
	});
});