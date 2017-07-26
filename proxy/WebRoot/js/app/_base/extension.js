define([], function() {
	// ----------------javascript object extension------------------

	/*
	 * 函数：格式化日期 参数：formatStr-格式化字符串 d：将日显示为不带前导零的数字，如1 dd：将日显示为带前导零的数字，如01
	 * ddd：将日显示为缩写形式，如Sun dddd：将日显示为全名，如Sunday M：将月份显示为不带前导零的数字，如一月显示为1
	 * MM：将月份显示为带前导零的数字，如01 MMM：将月份显示为缩写形式，如Jan MMMM：将月份显示为完整月份名，如January
	 * yy：以两位数字格式显示年份 yyyy：以四位数字格式显示年份 h：使用12小时制将小时显示为不带前导零的数字，注意||的用法
	 * hh：使用12小时制将小时显示为带前导零的数字 H：使用24小时制将小时显示为不带前导零的数字 HH：使用24小时制将小时显示为带前导零的数字
	 * m：将分钟显示为不带前导零的数字 mm：将分钟显示为带前导零的数字 s：将秒显示为不带前导零的数字 ss：将秒显示为带前导零的数字
	 * l：将毫秒显示为不带前导零的数字 ll：将毫秒显示为带前导零的数字 tt：显示am/pm TT：显示AM/PM 返回：格式化后的日期
	 */
	Date.prototype.format = function(formatStr) {
		var date = this;
		/*
		 * 函数：填充0字符 参数：value-需要填充的字符串, length-总长度 返回：填充后的字符串
		 */
		var zeroize = function(value, length) {
			if (!length) {
				length = 2;
			}
			value = new String(value);
			for ( var i = 0, zeros = ''; i < (length - value.length); i++) {
				zeros += '0';
			}
			return zeros + value;
		};
		return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {
			switch ($0) {
			case 'd':
				return date.getDate();
			case 'dd':
				return zeroize(date.getDate());
			case 'ddd':
				return [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat' ][date.getDay()];
			case 'dddd':
				return [ 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday' ][date.getDay()];
			case 'M':
				return date.getMonth() + 1;
			case 'MM':
				return zeroize(date.getMonth() + 1);
			case 'MMM':
				return [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ][date.getMonth()];
			case 'MMMM':
				return [ 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December' ][date
						.getMonth()];
			case 'yy':
				return new String(date.getFullYear()).substr(2);
			case 'yyyy':
				return date.getFullYear();
			case 'h':
				return date.getHours() % 12 || 12;
			case 'hh':
				return zeroize(date.getHours() % 12 || 12);
			case 'H':
				return date.getHours();
			case 'HH':
				return zeroize(date.getHours());
			case 'm':
				return date.getMinutes();
			case 'mm':
				return zeroize(date.getMinutes());
			case 's':
				return date.getSeconds();
			case 'ss':
				return zeroize(date.getSeconds());
			case 'l':
				return date.getMilliseconds();
			case 'll':
				return zeroize(date.getMilliseconds());
			case 'tt':
				return date.getHours() < 12 ? 'am' : 'pm';
			case 'TT':
				return date.getHours() < 12 ? 'AM' : 'PM';
			}
		});
	};
	// 测试 new Date().format("yyyy-MM-dd hh:mm:ss")

	/*
	 * 
	 * 日期格式的属性扩展，放到公用js文件中公用 var time1 = new Date().format("yyyy-MM-dd
	 * HH:mm:ss"); var time2 = new Date().format("yyyy-MM-dd");
	 */
	Date.prototype.dateFormat = function(fmt) { // author: meizz
		var o = {
			"M+" : this.getMonth() + 1, // 月份
			"d+" : this.getDate(), // 日
			"h+" : this.getHours(), // 小时
			"m+" : this.getMinutes(), // 分
			"s+" : this.getSeconds(), // 秒
			"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
			"S" : this.getMilliseconds()
		// 毫秒
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	};
	
	/*表单转成json数据*/
	$.fn.serializeObject = function() {
	    var o = {};
	    var a = this.serializeArray();
	    $.each(a, function() {
	        if (o[this.name]) {
	            if (!o[this.name].push) {
	                o[this.name] = [ o[this.name] ];
	            }
	            o[this.name].push(this.value || '');
	        } else {
	            o[this.name] = this.value || '';
	        }
	    });
	    return o;
	};


	$.extend({
		tabsHelper : function(tabs, method, options) {
			if (!tabs) {
				console.log("Warning: parameter 'tabs' is null, do nothing!!!");
				return false;
			}
			if (!options) {
				console.log("Warning: parameter 'options' is null, do nothing!!!");
				return false;
			}
			if (!options.className) {
				console.log("Warning: parameter 'options.className' is null, do nothing!!!");
				return false;
			}
			if (method !== "add") {
				console.log("Warning: only support 'add' method, but parameter 'method' is " + method + ", do nothing!!!");
				return false;
			}
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
				if (tab && tab[shortName] && _.isFunction(tab[shortName])) {
					tab[shortName](options);
					tab.addClass("app-" + shortName);
				}
			});
		}
	});

	$.extend($.fn.validatebox.defaults.rules, {
		minLength : {
			validator : function(value, param) {
				return value.length >= param[0];
			},
			message : '输入字符数不能少于{0}个'
		},
		maxLength : {
			validator : function(value, param) {
				return value.length <= param[0];
			},
			message : "输入字符数不能超过{0}个"
		},
		CHS : {
			validator : function(value) {
				return /^[\u0391-\uFFE5]+$/.test(value);
			},
			message : "只能输入汉字"
		},
		// 移下手机号码验证
		mobile : {// value值为文本框中的值
			validator : function(value) {
				var reg = /^1[3|4|5|8|9]\d{9}$/;
				return reg.test(value);
			},
			message : "输入手机号码格局不正确."
		},
		// 国内邮编验证
		zipcode : {
			validator : function(value) {
				var reg = /^[1-9]\d{5}$/;
				return reg.test(value);
			},
			message : "邮编必须是6位数字."
		},
		number: {
	        validator: function (value, param) {
	            return /^\d+$/.test(value);
	        },
	        message: '只能输入数字'
	    }
	});

	$.extend($.fn.numberbox.defaults.rules, {
		range : {
			validator : function(value, param) {
				value = value.replace(/,/g, '')
				value = parseInt(value);
				return (value >= param[0] && value <= param[1]);
			},
			message : "输入值必须在{0}和{1}之间"
		},
		maxLength : {
			validator : function(value, param) {
				return value.length <= param[0];
			},
			message : "输入字符数不能超过{0}个"
		},
		positiveNumber : {
			validator : function(value, param) {
				return value > -1;
			},
			message : "输入数字必须是正数"
		}
	});

	/**
	 * 动态修改表格列的editor
	 */
    $.extend($.fn.datagrid.methods, {   
        addEditor : function(jq, param) {   
            return jq.each(function(){   
                if (param instanceof Array) {   
                    $.each(param, function(index, item) {   
                        var e = $(jq).datagrid('getColumnOption', item.field);   
                        e.editor = item.editor;   
                    });   
                } else {   
                    var e = $(jq).datagrid('getColumnOption', param.field);   
                    e.editor = param.editor;   
                }   
            });   
        },   
        removeEditor : function(jq, param) {   
            return jq.each(function(){   
                if (param instanceof Array) {   
                    $.each(param, function(index, item) {   
                        var e = $(jq).datagrid('getColumnOption', item);   
                        e.editor = {};   
                    });   
                } else {   
                    var e = $(jq).datagrid('getColumnOption', param);   
                    e.editor = {};   
                }   
            });   
        }   
    });  
    
    (function($, h, c) {
    	var a = $([]), e = $.resize = $.extend($.resize, {}), i, k = "setTimeout",
    	j = "resize", d = j + "-special-event", b = "delay", f = "throttleWindow";
    	e[b] = 250;
    	e[f] = true;
    	$.event.special[j] = {
    		setup : function() {
    			if (!e[f] && this[k]) {
    				return false
    			}
    			var l = $(this);
    			a = a.add(l);
    			$.data(this, d, {
    				w : l.width(),
    				h : l.height()
    			});
    			if (a.length === 1) {
    				g()
    			}
    		},
    		teardown : function() {
    			if (!e[f] && this[k]) {
    				return false
    			}
    			var l = $(this);
    			a = a.not(l);
    			l.removeData(d);
    			if (!a.length) {
    				clearTimeout(i)
    			}
    		},
    		add : function(l) {
    			if (!e[f] && this[k]) {
    				return false
    			}
    			var n;
    			function m(s, o, p) {
    				var q = $(this), r = $.data(this, d);
    				r.w = o !== c ? o : q.width();
    				r.h = p !== c ? p : q.height();
    				n.apply(this, arguments)
    			}
    			if ($.isFunction(l)) {
    				n = l;
    				return m
    			} else {
    				n = l.handler;
    				l.handler = m
    			}
    		}
    	};
    	function g() {
    		i = h[k](function() {
    			a.each(function() {
    				var n = $(this), m = n.width(), l = n.height(), o = $.data(
    						this, d);
    				if (m !== o.w || l !== o.h) {
    					n.trigger(j, [ o.w = m, o.h = l ])
    				}
    			});
    			g()
    		}, e[b])
    	}
    })(jQuery, this);
    
    String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
        if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
            return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
        } else {  
            return this.replace(reallyDo, replaceWith);  
        }  
    }

});