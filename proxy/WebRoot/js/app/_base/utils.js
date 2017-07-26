define([ "jqueryplugins/jquery.form" ], function() {
	var utils = {
		getQueryString : function(name) {
			return app.utils.getQueryStringFormUrl(window.location.search.substr(1), name);
		},
		
		getQueryStringFormUrl : function(url, name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = url.match(reg);
			if (r != null)
				return unescape(r[2]);
			return null;
		},

		processAction : function() {
			var action = app.utils.getQueryString('action');
			if (action === 'openTask') {
				var taskId = app.utils.getQueryString('taskId');
				console.dir([ "打开任务", taskId ]);
				$.ajax(app.buildServiceData("queryMyTaskById", {
					data : {
						id : taskId
					},
					context : this,
					global : true,
					success : _.bind(function(data) {
						if (data.total == 0) {
							return;
						}
						app.workspace.openTab({
							"title" : data.rows[0].taskname,
							"className" : "app/common/task/taskForm",
							"taskData" : data.rows[0],
							"mode" : "edit"
						});
//						this._publish("app/openTab", {
//							"title" : data.rows[0].taskname,
//							"url" : "app/common/task/taskRequestForm",
//							"taskData" : data.rows[0],
//							"mode" : "edit"
//						});
					}, this)
				}));
			}
		},

		preLoadDictsAction : function(dictNames) {
			console.dir([ "preLoadDictsAction - dictNames", dictNames ]);
			if (dictNames && dictNames.length > 0) {
				var names = dictNames.join(",");
				app.utils.getDicts(names, function(data) {
					app.config.dicts = $.extend(app.config.dicts, data);

					// 转为map格式
					for ( var p in data) {
						var _dic = {};
						_.each(data[p], function(dictitem) {
							_dic[dictitem.dicitemkey] = dictitem.dicitemvalue;
						});
						app.config.dictmap[p] = _dic;
					}
				});
			}
		},

		getCachedDictByArray : function(dictName) {
			return app.config.dicts[dictName] || [];
		},

		getCachedDictByMap : function(dictName) {
			return app.config.dictmap[dictName] || {};
		},

		getDictItemLabel : function(dictName, dictItemKey) {
			var dictItemLabel = dictItemKey;
			if (app.config.dictmap[dictName] && app.config.dictmap[dictName][dictItemKey] !== undefined) {
				dictItemLabel = app.config.dictmap[dictName][dictItemKey];
			}
			return dictItemLabel;
		},

		keepLive : function() {
			$.ajax(app.buildServiceData("keepLive", {
				data : {}
			}));
		},

		startKeepLive : function() {
			app.utils.stopKeepLive();
			this._keepLiveId = setInterval(app.utils.keepLive, app.config.keepLiveInterval - 1000);
		},

		stopKeepLive : function() {
			if (this._keepLiveId) {
				clearInterval(this._keepLiveId);
				delete this._keepLiveId;
			}
		},

		isSupperAdmin : function() {
			for (var i = 0; i < app.userInfo.roles.length; i++) {
				if (app.userInfo.roles[i].rolename == app.constants.USER_SUPER_ADMIN || app.userInfo.roles[i].rolename == app.constants.USER_PLATFORM_ADMIN) {
					return true;
				}
			}
			return false;
		},
		parseOptions : function(target) {
			var options = {};
			var s = $.trim(target.attr('data-options'));
			if (s) {
				if (s.substring(0, 1) != '{') {
					s = '{' + s + '}';
				}
				options = (new Function('return ' + s))();
			}
			return options;
		},

		parseTemplate : function(template, context) {
			return template.replace(/\$\{([^\s\}]+)\}/g, function(match, key) {
				return context[key];
			});
		},

		getDijitName : function(target) {
			var dijitName = null;
			if (target) {
				var widgetId = target.attr('wid');
				if (widgetId) {
					var widgetFullName = widgetId.split("_")[0];
					dijitName = widgetFullName.split("-")[1];
				}
			}
			return dijitName;
		},

		findWidgetByWid : function(widgetId) {
			var widgetFullName = widgetId.split("_")[0];
			var widget = $("div[wid='" + widgetId + "']").data(widgetFullName);
			return widget;
		},

		eventBroker : function(event) {
			var options = app.utils.parseOptions($(event));
			var widgetId = options.widgetId;
			var handler = options.handler;
			var widgetFullName = widgetId.split("_")[0];
			var widget = $("div[wid='" + widgetId + "']").data(widgetFullName);
			if (widget[handler] && _.isFunction(widget[handler])) {
				widget[handler](event);
			}
		},

		bind : function(html, context) {
			var handler;
			html = html.replace(/on[Cc]lick\s*=\s*(\w+)\(\);/g, function(match, key) {
				handler = key;
				return "onClick=app.utils.eventBroker(this)";
			});
			if (handler) {
				html = html.replace(/data-options\s*=\s*\"/g, function(match, key) {
					return match + "widgetId:'" + context.widgetId + "',handler:'" + handler + "',";
				});
			}
			return html;
		},

		bindDatagrid : function(context, datagridEl) {
			var columns = datagridEl.datagrid("options").columns[0];
			_.each(columns, function(col) {
				if (col.formatter && _.isString(col.formatter) && context[col.formatter] && _.isFunction(context[col.formatter])) {
					col.formatter = _.bind(context[col.formatter], context);
				}
			});
		},

		getErrorMsg : function(context, jqXHR) {
			var msgDetails = [];
			msgDetails.push('<div>状态码: <span>' + jqXHR.status + '</span></div>');

			var msgs = [];
			var errorMsgJson = eval("(" + jqXHR.responseText + ")");
			if (errorMsgJson.msg) {
				var strs = errorMsgJson.msg.split(":");
				msgs.push('<div><span>' + strs[1] + '</span><br><br></div>');
				msgDetails.push('<div>异常类型: <span>' + strs[0] + '</span></div>');
				msgDetails.push('<div>异常信息: <span>' + strs[1] + '</span></div>');
			}

			msgDetails.push('<div>错误原因: <span>' + jqXHR.statusText + '</span></div>');
			if (context.widgetId) {
				msgDetails.push('<div>来源组件: <span>' + context.widgetId + '</span></div>');
			}
			msgs.push('<div><a href=\"javascript:void(0);\" onClick=$(this).parent().next().toggle()>详细信息</a></div>');
			msgs.push('<div style="display:none;">' + msgDetails.join('') + '</div>');

			return msgs.join('');
		},

		getJSON : function(url, callback, errorback) {
			var deffer = $.ajax(url, {
				dataType : 'html'
			}).done(function(result) {
				if (callback && _.isFunction(callback)) {
					callback(eval("(" + result + ")"));
				}
			}).fail(function(error) {
				if (errorback && _.isFunction(errorback)) {
					errorback(error);
				}
			});
			return deffer;
		},

		initComboboxByDict : function(combobox, dictInfo, options) {
			var _options = {
				panelHeight : 'auto',
				valueField : 'dicitemkey',
				textField : 'dicitemvalue'
			};
			if (_.isString(dictInfo)) {
				dictInfo = {
					dicName : dictInfo,
					isCheckAll : options.isCheckAll,
					cached : options.cached
				};
			}
			_options = $.extend(_options, options);
			app.utils.getDict(dictInfo, function(dicts) {
				_options.data = _.clone(dicts);
				if (dictInfo.selectFirst) {
					_options.value = dicts[0].dicitemkey;
				}
				combobox.combobox(_options);
			});
		},

		initCombobox : function(combobox, service, data, options) {
			$.ajax(app.buildServiceData(service, {
				data : data,
				success : function(data) {
					options.data = data;
					combobox.combobox(options);
				},
				error : function(error) {
					app.messager.error('下拉框数据加载失败！');
				}
			}));
		},

		getDictionary : function(dictKey, callback, errorback) {
			var dicts = app.config.dicts;
			if (dicts && dicts[dictKey]) {
				callback && callback(dicts[dictKey]);
			} else {
				$.ajax(app.buildServiceData("getDicts", {
					data : {},
					success : function(data) {
						app.config.dicts = data;
						callback && callback(data[dictKey]);
					},
					error : function(error) {
						app.messager.error('字典加载失败！');
					}
				}));
			}
		},

		getDict : function(dictInfo, callback, errorback) {
			var dicts = app.config.dicts;
			if (_.isString(dictInfo)) {
				dictInfo = {
					dicName : dictInfo,
					isCheckAll : false,
					cached : true
				};
			}
			var key = dictInfo.dicName;
			if (dictInfo.parentId != undefined) {
				key += "_" + dictInfo.parentId;
			}

			dictInfo.isCheckAll = dictInfo.isCheckAll === undefined ? false : dictInfo.isCheckAll;
			if (dictInfo.isCheckAll === true) {
				key += "_allInclude";
			}
			if (dictInfo.cached && dicts && dicts[key]) {
				callback && callback(dicts[key]);
			} else {
				$.ajax(app.buildServiceData("findDicItem", {
					data : dictInfo,
					success : function(data) {
						app.config.dicts[key] = data;
						callback && callback(data);
					},
					error : function(error) {
						if (errorback) {
							errorback(error);
						} else {
							app.messager.error('字典加载失败！');
						}
					}
				}));
			}
		},

		getDicts : function(dicNames, callback, errorback) {
			$.ajax(app.buildServiceData("findDicItemForMap", {
				data : {
					dicNames : dicNames
				},
				success : function(data) {
					callback && callback(data);
				},
				error : function(error) {
					if (errorback) {
						errorback(error);
					} else {
						app.messager.error('字典加载失败！');
					}
				}
			}));
		},

		casecadeValidate : function(values) {
			var len = values.length;
			var checkIdx = -1;
			for (var i = len - 1; i > 0; i--) {
				if (values[i] != "") {
					checkIdx = i;
					break;
				}
			}

			var ret = true;
			for (var j = 0; j < checkIdx; j++) {
				ret = ret && (values[j] != "");
			}

			return ret ? -1 : checkIdx;
		},

		download : function(ajaxData) {
			var form = $("#download_form");
			if (form.length == 0) {
				form = $("<form id='download_form'></form>").appendTo("body");
			}
			form.attr('method', ajaxData.type);

			var data = ajaxData.data;
			var url = ajaxData.url;
			if (ajaxData.type === "GET" || ajaxData.type === "get") {
				var paras = "";
				var index = 0;
				for ( var p in data) {
					if (index == 0) {
						paras += "?";
					} else {
						paras += "&";
					}
					paras += p + "=" + data[p];
					index++;
				}
				url = ajaxData.url + paras;
			} else {
				var elements = [];
				for ( var p in data) {
					var element = $("#" + p);
					if (element.length == 0) {
						var elementStr = "<input id=" + p + " name=" + p + " value=" + data[p] + ">";
					} else {
						element.val(data[p]);
					}
					elements.push(elementStr);
				}
				$(elements.join("")).appendTo(form);
			}
			var success = function() {
				
			};

			var error = function() {

			};

			console.dir([ "download url", url ]);

			form.form("submit", {
				url : url,
				success : ajaxData.success || success,
				error : ajaxData.error || error
			});
		},

		/**
		 * 金钱小写转大写
		 * 
		 * @param Num
		 * @returns {String}
		 */
		Arabia_to_Chinese : function(numberValue) {
			if (_.isNumber(numberValue)) {
				numberValue = numberValue.toFixed(2);
			}
			var qianzhui = "";
			if (numberValue.split("-").length > 1) {
				numberValue = numberValue.replace("-", "");
				qianzhui = "负";
			} else {
				qianzhui = "";
			}

			numberValue = parseFloat(numberValue).toFixed(2);
			var numberValue = new String(Math.round(numberValue * 100)); // 数字金额
			var chineseValue = ""; // 转换后的汉字金额
			var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
			var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
			var len = numberValue.length; // numberValue 的字符串长度
			var Ch1; // 数字的汉语读法
			var Ch2; // 数字位的汉字读法
			var nZero = 0; // 用来计算连续的零值的个数
			var String3; // 指定位置的数值
			if (len > 15) {
				app.messager.warn("超出计算范围");
				return "";
			}
			if (numberValue == 0) {
				chineseValue = "零元整";
				return chineseValue;
			}

			String2 = String2.substr(String2.length - len, len); // 取出对应位数的STRING2的值
			for (var i = 0; i < len; i++) {
				String3 = parseInt(numberValue.substr(i, 1), 10); // 取出需转换的某一位的值
				if (i != (len - 3) && i != (len - 7) && i != (len - 11) && i != (len - 15)) {
					if (String3 == 0) {
						Ch1 = "";
						Ch2 = "";
						nZero = nZero + 1;
					} else if (String3 != 0 && nZero != 0) {
						Ch1 = "零" + String1.substr(String3, 1);
						Ch2 = String2.substr(i, 1);
						nZero = 0;
					} else {
						Ch1 = String1.substr(String3, 1);
						Ch2 = String2.substr(i, 1);
						nZero = 0;
					}
				} else { // 该位是万亿，亿，万，元位等关键位
					if (String3 != 0 && nZero != 0) {
						Ch1 = "零" + String1.substr(String3, 1);
						Ch2 = String2.substr(i, 1);
						nZero = 0;
					} else if (String3 != 0 && nZero == 0) {
						Ch1 = String1.substr(String3, 1);
						Ch2 = String2.substr(i, 1);
						nZero = 0;
					} else if (String3 == 0 && nZero >= 3) {
						Ch1 = "";
						Ch2 = "";
						nZero = nZero + 1;
					} else {
						Ch1 = "";
						Ch2 = String2.substr(i, 1);
						nZero = nZero + 1;
					}
					if (i == (len - 11) || i == (len - 3)) { // 如果该位是亿位或元位，则必须写上
						Ch2 = String2.substr(i, 1);
					}
				}
				chineseValue = chineseValue + Ch1 + Ch2;
			}

			if (String3 == 0) { // 最后一位（分）为0时，加上“整”
				chineseValue = chineseValue + "整";
			}

			return qianzhui + chineseValue;
		},

		openTab : function(target, tabInfo) {
			target._publish("app/openTab", tabInfo);
		},

		closeTab : function(target, refresh) {
			target._publish("app/closeTab", {
				refresh : refresh
			});
		},
		
		/**
		 * 可编辑表格新增操作
		 * 
		 * @param $dg
		 *            datagrid
		 */
		gridEditAdd : function($dg) {
			if ($dg) {
				var rows = $dg.datagrid('getRows');
				var index = rows.length;
				var i = app.utils.getEditRowIndex($dg);
				if (app.utils.gridEditEnd($dg, i)) {
					$dg.datagrid('appendRow', {});
					$dg.datagrid("selectRow", index).datagrid('beginEdit', index);
					return true;
				}
			}
			return false;
		},
		/**
		 * 结束编辑
		 * 
		 * @param $dg
		 *            datagrid
		 */
		gridEditEnd : function($dg, index) {
			if (index < 0 || index == undefined)
				return true;
			// 如果正在编辑的行没通过验证，返回false，否则结束正在编辑的行
			if ($dg.datagrid("validateRow", index)) {
				$dg.datagrid("endEdit", index);
				return true;
			}
			return false;
		},
		/**
		 * 获取正在编辑的行
		 * 
		 * @param $dg
		 *            datagrid
		 */
		getEditRowIndex : function($dg) {
			var rows = $dg.datagrid('getRows');
			var index = rows.length + 1;
			var result;
			for ( var i = 0; i < index; i++) {
				var ed = $dg.datagrid('getEditors', i);
				if (ed.length > 0) {
					result = i;
					break;
				}
			}
			return result;
		},
		/**
		 * 可编辑表格确定操作
		 * 
		 * @param $dg
		 *            datagrid
		 * @return true:保存完成 ,false:保存失败
		 */
		gridEditSave : function($dg) {
			if ($dg) {
				var i = app.utils.getEditRowIndex($dg);
				return app.utils.gridEditEnd($dg, i);
			}
		},
		
		/**
		 * 删除指定table中的指定行
		 * 
		 * @param $dg
		 *            datagrid
		 */
		gridEditRemove : function($dg) {
			var i = app.utils.getEditRowIndex($dg);
			if ($dg && app.utils.gridEditEnd($dg, i)) {
				var row = $dg.datagrid('getSelected');
				var length = $dg.datagrid('getRows').length;
				var rowIndex;
				if (length == 0)
					return;
				if (row) {
					rowIndex = $dg.datagrid('getRowIndex', row);
					$dg.datagrid('cancelEdit', rowIndex).datagrid('deleteRow', rowIndex);
				} else {
					rowIndex = length - 1;
					$dg.datagrid('cancelEdit', rowIndex).datagrid('deleteRow', rowIndex);
				}
			}else {
				//未编辑完成的 直接删除
				var row = $dg.datagrid('getSelected');
				var length = $dg.datagrid('getRows').length;
				var rowIndex;
				if (length == 0)
					return;
				if (row) {
					rowIndex = $dg.datagrid('getRowIndex', row);
					if(rowIndex !== length-1){
						$dg.datagrid('deleteRow', rowIndex);
					}else{
						$dg.datagrid('cancelEdit', rowIndex).datagrid('deleteRow', rowIndex);
					}
					
				} else {
					rowIndex = length - 1;
					$dg.datagrid('cancelEdit', rowIndex).datagrid('deleteRow', rowIndex);
				}
			}
		},
		
		//验证手机
		checkMobile: function(mobile) {
			var reg = /^13\d{9}|145\d{8}|147\d{8}|15\d{9}|18\d{9}$/;
			return reg.test(mobile);
		},
		
		//判断是否平台管理员
		isPlatformAdmin : function() {
			for (var i = 0; i < app.userInfo.roles.length; i++) {
				if (app.userInfo.roles[i].rolename == app.constants.USER_PLATFORM_ADMIN) {
					return true;
				}
			}
			return false;
		},
		
		//判断是否IT维护岗
		isItMaintainStaff : function() {
			for (var i = 0; i < app.userInfo.roles.length; i++) {
				if (app.userInfo.roles[i].rolename == app.constants.SETTLE_IT_MAINTAIN_STAFF) {
					return true;
				}
			}
			return false;
		},
		renderCardView : function(div, data, subType, contentPoint, options) {
			if (!div || !data || !subType || !contentPoint) {
				return;
			}
			var paging = (typeof options) == "object" ? options.paging : options;
			if (paging) {
				div[contentPoint].html("");
			} else {
				div[contentPoint].find(".app-" + subType + "").remove();
				div[contentPoint].find(".line").remove();
			}
			var rows = data.rows;
			var total = data.total;
			for ( var i = 0; i < rows.length; i++) {
				var point = subType + i;
				var lowerType = subType.toLowerCase();
				var templateHtml = '<div class="app-' + subType + '" data-attach-point="' + point + '" data-attach-event="' + lowerType + 'refresh:_onRefresh,'
						+ lowerType + 'confirm:_onConfirm,' + lowerType + 'modify:_onModify"></div>';
				if (i !== 0) {
					templateHtml += "<div class='line' style='height:20px;'></div>";
				}
				$(templateHtml).prependTo(div[contentPoint]);
			}
			if (total != undefined && paging) {
				var pageNumber = (typeof options) == "object" ? options.pageNumber : 1;
				var pageSize = (typeof options) == "object" ? options.pageSize : 3;
				var pagingHtml = '<div class="easyui-pagination" data-attach-point="pageDiv" data-options="total:' + total
					+ ',pageSize:'+pageSize+',pageNumber:' + pageNumber + ',pageList:[1,3,5,10]" style="border:1px solid #ddd;"></div>';
				$(pagingHtml).appendTo(div[contentPoint]);
			}
			div._parse(div[contentPoint], {
				templateString : templateHtml
			});
			div[contentPoint].find(".app-" + subType + "").each(_.bind(function(i, item) {
				var widgetElement = $(item);
				if (typeof options == "object") {
					for ( var f in options) {
						rows[i][f] = options[f];
					}
				}
				widgetElement[subType]('render', rows[i]);
			}, this));
			if (paging) {
				div["pageDiv"].pagination({
					onSelectPage : _.bind(div._renderCard, div)
				});
			}
		},
		loadDivSpanData : function(div, data, attrName) {
			if (!attrName) {
				attrName = "data-attach-point";
			}
			var arr = div.find("[" + attrName + "]");
			_.each(arr, function(t) {
				$t = $(t);
				var key = $t.attr(attrName);
				var keyArr = key.split(".");var v = data[keyArr[0]];
				if(keyArr.length>1&&v){v = v[keyArr[1]];}
				if (v!=null && v!=undefined) {
					var formatter = $t.attr("formatter");
					if (formatter) {
						v = eval(formatter + "(" + v + ")");
					}
					if ($t.is("span")) {
						$t.text(v);
					} else if ($t.is("input")||$t.is("textarea")) {
						$t.val(v);
					}
				}
			});
		},
		/**
		 * @return >0 正常文件
		 * @return =0 文件被删除
		 * @return <0 文件不存在
		 * @author changpengfei
		 */
		getFileSize:function(target){
			 	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;         
			    var fileSize = 0;          
			    if (isIE && !target[0].files) {      
			      var filePath = target.val();      
			      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");         
			      var file = fileSystem.GetFile (filePath);      
			      fileSize = file.Size;     
			    } else { 
			    	if(target[0].files.length==0){
			    		fileSize=-1;
			    	}else
			    		fileSize = target[0].files[0].size;      
			     }    
			    return fileSize;
		},
		
		/**
		 * 商户维护岗判断，权限控制
		 */
		isMerchantMaintain : function() {
			for (var i = 0; i < app.userInfo.roles.length; i++) {
				if (app.userInfo.roles[i].rolename == app.constants.SETTLE_MERCHANT_MAINTAIN_STAFF) {
					return true;
				}
			}
			return false;
		},
		
		/**
		 * 是否是结算查看岗
		 */
		isSettleView : function() {
			for (var i = 0; i < app.userInfo.roles.length; i++) {
				if (app.userInfo.roles[i].rolename == app.constants.SETTLE_SETTLE_VIEWER) {
					return true;
				}
			}
			return false;
		},
		
		/**
		 * 比较数值(精确2位小数，进行比较)
		 * 
		 * @return -1：小于 0 ：等于 1：大于
		 */
		compareNumerical : function(a, b) {
			a = _.isString(a) ? Number(a) : a;
			b = _.isString(b) ? Number(b) : b;
			if (isNaN(a))
				a = 0;

			if (isNaN(b))
				b = 0;
			a = a.toFixed(2);
			b = b.toFixed(2);
			if (Number(a) > Number(b)) {
				return 1;
			} else if (Number(a) == Number(b)) {
				return 0;
			} else {
				return -1;
			}
		}
		
	}
	return utils;
});
