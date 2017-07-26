var fs = require('fs');
var root_path = "../../compressed/js";

function mergeBootloader() {
	var requirejs_file = root_path + "/lib/requirejs/require.js";
	var boot_config_file = root_path + "/zf/boot-config.js";
	var boot_loader_file = root_path + "/zf/bootloader.js";
	var boot_loader_min_file = root_path + "/zf/bootloader.min.js";
	var data1 = fs.readFileSync(requirejs_file, "utf-8");
	var data2 = fs.readFileSync(boot_config_file, "utf-8");
	var data3 = fs.readFileSync(boot_loader_file, "utf-8");
	var data_all = data1 + "\n" + data2 + "\n" + data3;
	fs.writeFileSync(boot_loader_min_file, data_all, "utf-8");
}

console.log("合并bootloader代码");
mergeBootloader();

