var fs = require('fs');
var build_js_file = "./build.js";
var root_path = "../js";
var app_root_path = root_path + "/app";

function getClassPath(rootPath, fileSuffix, ignoreFolderNames, ignoreFileNames) {
	var result = [];
	var files = fs.readdirSync(rootPath);
	ignoreFolderNames = ignoreFolderNames || [];
	ignoreFileNames = ignoreFileNames || [];

	// for(var i=0; i<ignoreFolderNames.length; i++){
	// console.log("ignoreFolderNames" + ignoreFolderNames[i]);
	// }

	files.forEach(function(file) {
		// var re1 = new RegExp(".svn");
		// if (re1.test(file)) {
		// console.log("file" + file);
		// return;
		// }
		var path = rootPath + '/' + file;
		var stat = fs.lstatSync(path);
		var isFolder = stat.isDirectory();
		var isIgnored = false;
		if (isFolder) {
			isIgnored = (ignoreFolderNames.indexOf(file) !== -1);
		} else {
			isIgnored = (ignoreFileNames.indexOf(file) !== -1);
		}
		if (!isIgnored) {
			var re = new RegExp(fileSuffix);
			if (!isFolder) {
				if (re.test(file)) {
					if (fileSuffix == ".js") {
						result.push('"' + path.replace(root_path, '').replace('/app/', 'app/').replace('.js', '') + '"');
//						console.log('"' + path.replace(root_path, '').replace('/app/', 'app/').replace('.js', '') + '"');
					} else {
						result.push('"text!' + path.replace(root_path, '').replace('/app/', 'app/') + '"');
//						console.log('"text!' + path.replace(root_path, '').replace('/app/', 'app/') + '"');
					}
				}
			} else {
				result = result.concat(getClassPath(path, fileSuffix, ignoreFolderNames, ignoreFileNames));
			}
		}
	});
//	for(var i=0; i<result.length;i++){
//		console.log(result[i]);
//	}
	return result;
}

function mergeHtmlFile() {
	console.log("合并Html文件开始...");
	var classes = getClassPath(app_root_path, ".html");
	var htmltplFile = app_root_path + "/_base/htmltpl.js";
	var tmpContent = fs.readFileSync(htmltplFile, 'utf-8');
	var data = classes.join(",\r\n");
	var content = tmpContent.replace("[]", "[\r\n" + data + "\r\n]");
	fs.writeFileSync(htmltplFile, content, "utf-8");
	console.log("合并Html文件结束");
}

function mergeJsonFile() {
	console.log("合并Json文件开始...");
	var classes = getClassPath(app_root_path, ".json", [], [ "form.json", "log-config.json", "searchBar.json", "service.json" ]);
	var jsontplFile = app_root_path + "/_base/jsontpl.js";
	var tmpContent = fs.readFileSync(jsontplFile, 'utf-8');
	var data = classes.join(",\r\n");
	var content = tmpContent.replace("[]", "[\r\n" + data + "\r\n]");
	fs.writeFileSync(jsontplFile, content, "utf-8");
	console.log("合并Json文件结束");
}

function mergeJsFile() {
	console.log("合并Js文件开始...");
	var classes = getClassPath(app_root_path, ".js", [ "base", "config" ]);
	var jstplFile = app_root_path + "/_base/jstpl.js";
	var tmpContent = fs.readFileSync(jstplFile, 'utf-8');
	var data = classes.join(",\r\n");
	var content = tmpContent.replace("[]", "[\r\n" + data + "\r\n]");
	fs.writeFileSync(jstplFile, content, "utf-8");
	console.log("合并Js文件结束");
}

function updateBuildJs() {
	console.log("更新Build文件开始...");
	var excludeClass = [ '"jquery"', '"underscore"', '"accounting"', '"jqueryplugins/jquery.form"', '"zf/base/BaseWidget"', '"jquery"', '"underscore"',
			'"ztree"', '"jqueryplugins/jquery.form"', '"zf/base/BaseWidget"', '"app/_base/constants"', '"app/_base/extension"', '"app/_base/messager"',
			'"app/_base/security"', '"app/_base/serviceLocator"', '"app/_base/utils"' ];

	var htmlClasses = getClassPath(app_root_path, ".html");
	var jsonClasses = getClassPath(app_root_path, ".json", [], [ "form.json", "log-config.json", "searchBar.json", "service.json" ]);
	for (var i = 0; i < htmlClasses.length; i++) {
		excludeClass.push(htmlClasses[i]);
	}
	for (var i = 0; i < jsonClasses.length; i++) {
		excludeClass.push(jsonClasses[i]);
	}

	var content = [];
	content.push("			,");
	content.push("			{");
	content.push("				name : 'app/_base/htmltpl'");
	content.push("			},");
	content.push("			{");
	content.push("				name : 'app/_base/jsontpl'");
	content.push("			},");
	content.push("			{");
	content.push("				name : 'app/_base/jstpl',");
	content.push("				exclude : [" + excludeClass.join(",") + "]");
	content.push("			}");

	var tmpContent = fs.readFileSync(build_js_file, 'utf-8');
	var data = tmpContent.replace("//@tpl_insert@", content.join("\r\n"));
	fs.writeFileSync(build_js_file, data, "utf-8");
	console.log("更新Build文件结束");
}

mergeHtmlFile();
mergeJsonFile();
mergeJsFile();
updateBuildJs();