<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<script>
	var app = window.app = {};
	app.ready = false;
	app.traceInfo = {};
	app.traceInfo.startTime = new Date();
</script>
<head>
<title>TalkingData Proxy</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible"
	content="IE=edge,chrome=1,requiresActiveX=true" />

<link rel="stylesheet" type="text/css" href="css/app/themes/orange/bootloader.css">
<link rel="shortcut icon" href="css/app/themes/orange/images/talkingdata.png" type="image/x-icon" />
<script src="js/zf/bootloader.min.js"></script>

</head>
<body class="easyui-layout" data-options="fit:true">
	<div class="app-header" data-options="region:'north',border:false"
		style="height: 100px; min-width: 1000px;">
		<div class="topbox">
			<div class="logo"></div>
			<div class="right">
				<div class="an">
					<div class="an-cbox">
						<div class="link-panel">
							<div class="inline">
								<div data-attach-point="welcomeInfo"
									style="display:inline-block;">
									<span>欢迎您:${user.name}</span>
								</div>
								<!-- <span>|</span>未读消息:<a href="javascript:void(0);"><span>0</span> </a> -->
							</div>
							<div class="inline" data-attach-point="settingDiv">
								<span>|</span><a href="javascript:void(0);"
									class="easyui-menubutton" data-options="menu:'.control_menus'">个人设置</a>
								<div class="control_menus">
									<div>修改密码</div>
									<!-- <div>临时授权</div>  -->
								</div>
							</div>
							<div class="inline">
								<span>|</span><a data-options="plain:true"
									class="easyui-linkbutton" href="logout.do">退出</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="topmenubox">
			<ul class="topmenu">
				<c:forEach var="menu" varStatus="xh" items="${menuList}">
					<li><a href="javascript:void(0);"
						data-options="resourceId:'${menu.resourceId}',index:${xh.index}">${menu.resourceLabel}</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="app-accordionMenu"
		data-options="region:'west',split:true,border:false"
		style="width: 196px;">
		<div class="leftsidebar">
			<c:forEach var="menu" items="${menuList}">
				<div style="display:none;">
					<div class="leftsidebar_top">
						<div class="leftsidebar_top_title">${menu.resourceLabel}</div>
					</div>
					<div class="leftsidebar_content lefter">
						<ul class="menus clear">
							<c:forEach var="submenu1" items="${menu.childrens}">
								<li><a class="level0" href="javascript:void(0);"
									data-options="resourceId:'${submenu1.resourceId}',title:'${submenu1.resourceLabel}',className:'${submenu1.resourceDesc}',childrenlen:${fn:length(submenu1.childrens)}"><span>${submenu1.resourceLabel}</span></a>
								</li>
								<c:if test="${!empty submenu1.childrens}">
									<ul class="submenu2" style="display:none;">
										<c:forEach var="submenu2" items="${submenu1.childrens}">
											<li><a class="level1" href="javascript:void(0);"
												data-options="resourceId:'${submenu2.resourceId}',title:'${submenu2.resourceLabel}',className:'${submenu2.resourceDesc}'"><span>${submenu2.resourceLabel}</span></a>
											</li>
										</c:forEach>
									</ul>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<div class="leftsidebar_bottom"></div>
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="app-workspace" data-options="region:'center'">
		<div class="easyui-tabs"
			data-options="fit:true,border:false,plain:true">
			<div title="欢迎"></div>
		</div>
	</div>
</body>
<script>
	requirejs([ "app/_base/app" ]);
</script>
</html>