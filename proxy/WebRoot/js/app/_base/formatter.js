define([], function() {
	var formatter = {
		formatterTotalAmt : function(value, row, index) {
		     return app.formatter.formatMoneyNumber((row.orderAmt+row.feeAmt)/100);
		},
		
		formatterOrderPoints : function(value, row, index) {
		     return app.formatter.formatMoneyNumber((row.orderPoints/500));
		},
		formatterScriptRunTime : function(value, row, index) {
			var beginTime = row.beginTime; 
			var endTime = row.endTime;
			if (beginTime == null || beginTime == undefined || beginTime == "") {
				return "";
			}
			if (endTime == null || endTime == undefined || endTime == "") {
				return "";
			}
			beginTime = new Date(beginTime).getTime();
			endTime = new Date(endTime).getTime();

			return (endTime - beginTime) / 1000;
		},
		formatterOrderscore : function(value) {
			if (value == "" || value == null) {
				value = 0.00;
			}
			if (!_.isNumber(value)) {
				val = 0.00;
			}
			var pointCash = parseFloat(value) / 500;

			return app.formatter.formatMoneyDefaultNumber(pointCash);
		},

		formatterPointCash : function(value) {
			if (value == "" || value == null) {
				value = 0.00;
			}
			if (!_.isNumber(value)) {
				val = 0.00;
			}
			var pointCash = parseFloat(value) / 100;

			return app.formatter.formatMoneyDefaultNumber(pointCash);
		},

		formatterB2CRecordStatus : function(value) {
			if (value === "1") {
				return "有效";
			} else if (value === "-1") {
				return "过期";
			} else if (value === "0") {
				return "未处理";
			} else {
				return "数据异常";
			}
		},
		formatterReportType : function(value) {
			if (value === "1") {
				return "常规活动";
			} else if (value === "2") {
				return "非常规活动";
			}
		},
		formatterScriptType : function(value) {
			if (value === "1") {
				return "数据导入";
			} else if (value === "2") {
				return "预处理";
			} else if (value === "3") {
				return "数据统计";
			} else if (value === "4") {
				return "对账";
			} else if (value === "5") {
				return "对账明细下载";
			} else if (value === "6") {
				return "报表";
			}

		},
		changeTwoDecimal : function(value) {
			var f_x = parseFloat(value);
			if (isNaN(f_x)) {
				alert('function:changeTwoDecimal->parameter error');
				return false;
			}
			var f_x = Math.round(value * 100) / 100;

			return app.formatter.formatMoneyNumber(f_x);
		},
		formatterSettleBatchItemAdjustStatus : function(value) {
			if (value === "1") {
				return "调整金额";
			} else if (value === "2") {
				return "计算金额";
			}
		},
		formatterSettleBatchItemCalcMode : function(value, row, index) {
			if (row.calcMode === "2") {
				return "手动计算";
			} else if (row.calcMode === "1") {
				return "系统计算";
			}
		},
		formatterSettleBatchItemMoney : function(value, row, index) {
			if (row.calcMode === "2") {
				return "无需计算";
			} else {
				if (row.cashInOutMode === "2") {
					if (value === null || value === undefined || value === 0) {
						value = "0.00";
					} else {
						/*
						 * if ((value + "").indexOf("-") === -1) { value = "-" +
						 * value; }
						 */
					}
				}
				return app.formatter.formatMoneyNumber(value);
			}
		},
		formatterSettleBatchItemFinalMoney : function(value, row, index) {
			if (row.cashInOutMode === "2") {
				if (value === null || value === undefined || value === 0) {
					value = "0.00";
				} else {
					/*
					 * if ((value + "").indexOf("-") === -1) { value = "-" +
					 * value; }
					 */
				}
			}
			return app.formatter.formatMoneyNumber(value);
		},
		formatterSettleItemCalcMode : function(value) {
			if (value === "1") {
				return "系统计算";
			} else if (value === "2") {
				return "手工输入";
			}
		},
		formatterSettleBatchStatus : function(value) {
			if (value === "1") {
				return "待确认";
			} else if (value === "2") {
				return "已确认";
			} else if (value === "3") {
				return "已生成结算单";
			} else if (value === "4") {
				return "结算单已审批";
			} else if (value === "5") {
				return "结算单已结算";
			} else if (value === "9") {
				return "未出批次";
			}
		},

		formatterSettleBatchStatusDefault : function(value) {
			return "已确认";
		},

		formatExtendedAttriType : function(value) {
			if (value === "0") {
				return "字符串";
			} else if (value === "1") {
				return "数字";
			} else if (value === "2") {
				return "数据字典";
			} else if (value === "3") {
				return "文本";
			} else {
				return "";
			}
		},
		formatterReconBatchType : function(value) {
			if (value === "1") {
				return "按发放";
			} else if (value === "2") {
				return "按兑换";
			}
		},

		formatterSettleBatchItemType : function(value) {
			if (value === "1") {
				return "收款";
			} else if (value === "2") {
				return "付款";
			}
		},

		formatterReconBatchResultStatus : function(value) {
			if (value === "0") {
				return "待确认";
			} else if (value === "1") {
				return "对账已确认";
			}
		},

		formatterAttachmentType : function(value) {
			if (value == "1") {
				return "邮件";
			} else if (value == "2") {
				return "签报";
			} else if (value == "100") {
				return "其他";
			} else if (value == "3") {
				return "对账文件";
			} else if (value == "4") {
				return "对账结果文件";
			} else if (value == "5") {
				return "金额调整凭证";
			} else if (value == "6") {
				return "B2C数据补抽文件";
			}
		},
		formatterSubjectumAttachmentType : function(value) {
			if (value == "1") {
				return "邮件";
			} else if (value == "2") {
				return "签报";
			} else if (value == "3") {
				return "其他";
			} else {
				return "";
			}
		},
		formatterReconBatchTxnStatus : function(value) {
			if (value === "1") {
				return "未处理";
			} else if (value === "2") {
				return "处理中";
			} else if (value === "3") {
				return "已完成";
			} else if (value === "4") {
				return "异常";
			} else if (value === "5") {
				return "错误已处理";
			}
		},
		formatSubjectumStatus : function(value) {
			if (value === "1") {
				return "未提交";
			} else if (value === "2") {
				return "已提交";
			} else if (value === "3") {
				return "审批通过(启用)";
			} else if (value === "-1") {
				return "禁用";
			} else if (value === "-2") {
				return "审批不通过";
			} else if (value === "-3") {
				return "流程被终止";
			}
		},
		formatStatus : function(value) {
			if (value === "0" || value === 0) {
				return "未启用";
			} else if (value === "1" || value === 1) {
				return "启用";
			}  else if (value === "-1" || value === -1) {
				return "禁用";
			}
		},
		formatTaskInfoStatus : function(value) {
			if (value === "0" || value === 0) {
				return "未开始";
			} else if (value === "1" || value === 1) {
				return "处理中";
			} else if (value === "2" || value === 2) {
				return "已完成";
			} else if (value === "-1" || value === -1) {
				return "异常";
			}
		},
		formatLevelStatus : function(value) {
			if (value === "0") {
				return "优";
			} else if (value === "1") {
				return "良好";
			} else if (value === "2") {
				return "一般";
			} else if (value === "3") {
				return "较差";
			} else if (value === "4") {
				return "差";
			}
		},
		formatSentimentStatus : function(value) {
			if (value === 1) {
				return "启用";
			} else if (value === 0) {
				return "未启用";
			} else if (value === -1) {
				return "禁用";
			}
		},
		formatReconBatchTransferStatus : function(value) {
			if (value === "1") {
				return "未取消";
			} else if (value === "2") {
				return "已取消";
			}
		},
		// 该方法用来对浮点数进行格式化，满足去尾法，可用于计算
		// 默认两位小数
		formatMoneyNumber : function(val, precision) {
			if (precision === undefined) {
				precision = 2;
			}

			return app.accounting.formatNumber(val, precision);
		},

		// 该方法用来对浮点数进行格式化，满足去尾法，可用于计算
		// 默认两位小数
		formatMoneyNumber1 : function(val) {
			if (val === null || val === "") {
				return "无限制";
			}
			if (!_.isNumber(val)) {
				val = 0;
			}
			return app.accounting.formatNumber(val, 2);
		},

		// 默认两位小数,去尾法
		formatMoneyDefaultNumber : function(val) {
			if (val === null || val === "") {
				return "0.00";
			}
			if (!_.isNumber(val)) {
				val = 0;
			}
			return app.accounting.formatNumber(val, 2);
		},

		// 该方法在计算浮点数的时候，不满足四舍五入的规则，如果仅用于界面显示，不存在问题
		formatMoney : function(s, integer) {
			var isMinus = false;
			if (s == null || s == "")
				return "0.00";
			s = s + "";
			if (s.indexOf("-") == 0) {
				isMinus = true;
				s = s.substring(1, s.length);
			}
			if (/[^0-9\.]/.test(s))
				return "0";
			s = s.toString().replace(/^(\d*)$/, "$1.");
			s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
			s = s.replace(".", ",");
			var re = /(\d)(\d{3},)/;
			while (re.test(s))
				s = s.replace(re, "$1,$2");
			s = s.replace(/,(\d\d)$/, ".$1");
			if (integer == true) {// 不带小数位(默认是有小数位)
				var a = s.split(".");
				if (a[1] == "00") {
					s = a[0];
				}
			}
			if (isMinus) {
				return "-" + s;
			}
			return s;
		},
		// 给datagrid中格式化金额用， 不带小数位
		formatIntMoney : function(s) {
			var isMinus = false;
			if (s == null || s == "")
				return "0";
			s = s + "";
			if (s.indexOf("-") == 0) {
				isMinus = true;
				s = s.substring(1, s.length);
			}
			if (/[^0-9\.]/.test(s))
				return "0";
			s = s.toString().replace(/^(\d*)$/, "$1.");
			s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1");
			s = s.replace(".", ",");
			var re = /(\d)(\d{3},)/;
			while (re.test(s))
				s = s.replace(re, "$1,$2");
			s = s.replace(/,(\d\d)$/, ".$1");
			// 不带小数位(默认是有小数位)
			var a = s.split(".");
			if (a[1] == "00") {
				s = a[0];
			}
			if (isMinus) {
				return "-" + s;
			}
			return s;
		},
		formatTime : function(val) {
			if (!val) {
				return "";
			}
			if (_.isString(val)) {
				val = val.replaceAll("-", "/");
			}
			return new Date(val).dateFormat("yyyy-MM-dd");
		},
		formatYearMonth : function(val) {
			if (!val) {
				return "";
			}
			if (_.isString(val)) {
				val = val.replaceAll("-", "/");
			}
			return new Date(val).dateFormat("yyyy-MM");
		},
		formatTime1 : function(val) {
			if (!val) {
				return "";
			}
			if (_.isString(val)) {
				val = val.replaceAll("-", "/");
			}
			return new Date(val).dateFormat("yyyy/MM/dd");
		},
		formatTime2 : function(val) {
			if (!val) {
				return "";
			}
			if (_.isString(val)) {
				val = val.replaceAll("-", "/");
			}
			return new Date(val).dateFormat("yy/MM/dd");
		},
		formatTime3 : function(val) {
			if (!val) {
				return "";
			}
			if (_.isString(val)) {
				val = val.replaceAll("-", "/");
			}
			return new Date(val).dateFormat("yyyy年MM月dd日");
		},
		formatTimeStamp : function(val) {
			if (!val) {
				return "";
			}
			if (_.isString(val)) {
				val = val.replaceAll("-", "/");
			}
			return new Date(val).dateFormat("yy-MM-dd hh:mm");
		},
		formatTimeStamp1 : function(val) {
			if (!val) {
				return "";
			}
			if (_.isString(val)) {
				val = val.replaceAll("-", "/");
			}
			return new Date(parseInt(val)).dateFormat("yyyy-MM-dd hh:mm:ss");
		},

		formatDate : function(val) {
			if (!val) {
				return "";
			} else {

				return new Date(val).dateFormat("yyyy-MM-dd hh:mm:ss");
			}
		},

		padNumber : function(num, n) {
			if (num !== null && num !== undefined) {
				num = num.toString();
				var len = num.length;
				while (len < n) {
					num = '0' + num;
					len++;
				}
			}
			return num;
		},
		formarEntityStatusText : function(value) {
			if (value == 0) {
				return '禁用';
			} else if (value == 1) {
				return '启用';
			}
		},
		formatdownloadAttachment : function(value, row, index) {
			return '<a href="salesAttachment/downloadAttachment.do?id=' + row.id + '" target="blank" style="color:blue">' + value + '</a>';
		},
		formatCheckBox : function(val) {
			if (val == 1) {
				return '是';
			} else if (val == 2) {
				return '否';
			} else {
				return '';
			}
		},
		formatProcessStatus : function(val) {
			if (val) {
				if (val == 1) {
					return "运行中";
				} else if (val == 2) {
					return "已结束";
				} else if (val == 3) {
					return "被管理员结束";
				} else if (val == 4) {
					return "被系统结束";
				} else if (val == 5) {
					return "校验中";
				} else {
					return "";
				}
			} else {
				return val;
			}
		},
		formatCallStatus : function(val) {
			if(val == 0) {
				return "未处理";
			} else if(val == 1) {
				return "处理中";
			}  else if(val == 2) {
				return "调用脚本完成";
			} else if(val == -1) {
				return "调用脚本异常";
			}
		},
		formatTaskApprove : function(val) {
			if (val == 0) {
				return "不同意";
			} else if (val == 1) {
				return "同意";
			} else if (val == 2) {
				return "征求意见";
			} else if (val == 3) {
				return "转发处理";
			} else {
				return "";
			}
		},
		formatTaskType : function(val) {
			if (val) {
				if (val == 1) {
					return "审批类任务";
				} else if (val == 2) {
					return "工作型任务";
				} else {
					return "";
				}
			} else {
				return val;
			}
		},
		formatTimeoutRemind : function(val) {
			if (val == "1") {
				return "开启";
			} else if (val == "0") {
				return "关闭";
			} else {
				return "";
			}
		},
		formatJobStatus : function(val) {
			if (val == "1") {
				return "有效";
			} else if (val == "0") {
				return "无效";
			} else {
				return "";
			}
		},
		formatVaildStatus : function(val) {
			if (val == "0") {
				return "有效";
			} else if (val == "1") {
				return "无效";
			} else {
				return "";
			}
		},
		formatdownloadAttachment : function(value, row, index) {
			return '<a href="sysAttachment/dowloadById.do?id=' + row.id + '" target="blank" style="color:blue">' + value + '</a>';
		},
		formatInvoiceContent : function(value, row, index) {
			if (value == 1) {
				return "服务费";
			} else if (value == 2) {
				return "佣金";
			} else {
				return "";
			}
		},
		formatBusinessType : function(value, row, index) {
			if (value == 1) {
				return '请求文件检查';
			} else if (value == 2) {
				return '请求文件处理';
			}
			else if (value == 3){
				return '响应文件处理';
			}
		},
		formatFileType : function(value, row, index) {
			if (value == "1") {
				return "发票";
			} else if (value == "2") {
				return "收据";
			} else {
				return "";
			}
		},

		formatCalcMode : function(value, row, index) {
			if (value == "1") {
				return "系统计算";
			} else if (value == "2") {
				return "手工输入";
			} else {
				return "";
			}
		},

		formatLogicStatus : function(value, row, index) {
			if (value == "1") {
				return "是";
			} else if (value == "2") {
				return "否";
			} else {
				return "";
			}
		},

		formatSettleInterval : function(value, row, index) {
			if (value == "1") {
				return "按天";
			} else if (value == "2") {
				return "按周";
			} else if (value == "3") {
				return "按月";
			} else if (value == "4") {
				return "按季";
			} else if (value == "5") {
				return "半年";
			} else if (value == "6") {
				return "按年";
			}
		},

		formatSettleSheetStatus : function(value, row, index) {
			if (value === "1") {
				return "未提交";
			} else if (value === "2") {
				return "已提交";
			} else if (value === "3") {
				return "审批通过";
			} else if (value === "-1") {
				return "禁用";
			} else if (value === "-2") {
				return "审批不通过";
			} else if (value === "-3") {
				return "流程被终止";
			}

		},
		formatReconBatchTransferType : function(value) {
			if (value === "1") {
				return "转出";
			} else if (value === "2") {
				return "转入";
			}
		},
		formatSettleItemCashType : function(value) {
			if (value === "1") {
				return "收款";
			} else if (value === "2") {
				return "付款";
			}
		},
		formatdownloadAttachment_contract : function(value, row, index) {
			if (row.attachmentId) {
				return '<a href="sysAttachment/downloadAttachmentById.do?id=' + row.attachmentId + '" target="blank" style="color:blue">' + row.attachmentName
						+ '</a>';

			} else {
				return "";
			}
		},

		// 交易流水积分量显示
		formatPointRemoveZero : function(value, row, index) {
			if (row) {
				if (row.recordTypeCd === "1") {
					value = row.acAccrualedValue;
				} else if (row.recordTypeCd === "2") {
					value = row.rdValue;
				}
			}
			if (!value) {
				return "";
			} else if (value.indexOf(".0000000") != -1) {
				return value.substring(0, value.indexOf(".0000000"));
			} else if (value.indexOf(".000000") != -1) {
				return value.substring(0, value.indexOf(".000000"));
			} else if (value.indexOf(".00000") != -1) {
				return value.substring(0, value.indexOf(".00000"));
			} else if (value.indexOf(".0000") != -1) {
				return value.substring(0, value.indexOf(".0000"));
			} else if (value.indexOf(".000") != -1) {
				return value.substring(0, value.indexOf(".000"));
			} else if (value.indexOf(".00") != -1) {
				return value.substring(0, value.indexOf(".00"));
			} else if (value.indexOf(".0") != -1) {
				return value.substring(0, value.indexOf(".0"));
			} else if (!value && value == "") {
				return "0";
			} else {
				return value;
			}
		},
		// 交易流水时间处理
		formatTransactionTime : function(value, row, index) {
			if (!value) {
				return "";
			}
			var date = new Date(value);
			if (!isNaN(date)) {
				return date.dateFormat("yyyy-MM-dd hh:mm:ss");
			} else if (value.length > 19) {
				return value.substring(0, 19);
			} else {
				return value;
			}
		},
		formateSex : function(value, row, index) {
			if (value === 1) {
				return "男";
			} else if (value === 2) {
				return "女";
			} else {
				return "";
			}
		},
		formatterShowTip : function(value, row, index) {
			if (row.itemName !== '佣金') {
				return row.itemName;
			}
			var content = '<div><div style="float:left;margin-left:30px">' + '&nbsp;' + value + '</div>'
					+ '<div class="showYj icon-help" style="width: 16px;height: 16px;float: left;margin-left: 5px;"></div></div>';
			return content;
		},
		isPingan : function(value, row, index) {
			if (value === 0) {
				return "否";
			} else if (value === 1) {
				return "是";
			} else {
				return "";
			}
		},
		b2cOrderStat : function(value, row, index) {
			if (value == 10) {
				return "交易成功";
			} else if (value == 20) {
				return "退货成功";
			} else {
				return "";
			}
		},
		// 商品类型
		formatProductType : function(value, row, index) {
			if (value === "1") {
				return "实物类";
			} else if (value === "2") {
				return "服务类";
			} else {
				return value;
			}
		},
		formatOaTemplateStatus : function(value) {
			if (value === 1) {
				return "未生效";
			} else if (value === 2) {
				return "启用";
			} else if (value === 3) {
				return "禁用";
			}
		},
		
		formatMediaSource :function(value){
			if(value ===1){
				return "新闻";
			}
			else if(value ===2){
				return "微博 ";
			}
			else if(value ===3){
				return "微信 ";
			}
			else if(value ===4){
				return "论坛 ";
			}
			else if(value ===5){
				return "问卷调查";
			}
			else if(value ===6){
				return "其它 ";
			}
			
		},
		formatType :function(value){
			if(value ===1){
				return "省（市）";
			}
			else if(value ===2){
				return "地级市";
			}
			else if(value ===3){
				return "县级市";
			}
		},
		formatEmailType : function(value,row,index) {
			if (value === "1") {
				return "固定邮件列表";
			} else if (value === "2") {
				return "Job任务邮件列表";
			} else if (value === "3") {
				return "固定角色邮件列表";
			}
		},
		formatFileType : function(value,row,index){
			if(value === "1"){
				return "CSV";
			}else if(value === "2"){
				return "XLS";
			}else if(value === "3"){
				return "XLSX";
			}else if(value === "4"){
				return "ZIP";
			}else if(value === "5"){
				return "DB";
			}
		},
		formatIsMapping : function(value){
			if(value === "1"){
				return "是";
			}else if(value === "2"){
				return "否";
			}else{
				return "";
			}
		},
		formatType : function(value){
			if(value === "1"){
				return "VARCHAR";
			}else if(value === "2"){
				return "INT";
			}else if(value === "3"){
				return "DOUBLE";
			}else{
				return "";
			}
		},
		trim : function(value){
			if (value == null){
				return "";
			}
			else if (value == undefined){
				return "";
			}
			return value;
		},
		formatTaskLogStatus : function(value){
			if (value === 1){
				return "正常";
			}
			else if (value === 2){
				return "异常";
			}
		},
		formatJobInputStatus : function(value){
			if (value === 0){
				return "未处理";
			}
			else if (value === 1){
				return "处理中";
			}
			else if (value === 2){
				return "处理完成";
			}
			else if (value === -1){
				return "异常";
			}
		},
		formatTaskLogType : function (value){
			if (value === 0){
				return "扫描到新上传文件";
			}
			else if (value === 1){
				return "下载文件(FTP)";
			}
			else if (value === 2){
				return "解密文件";
			}
			else if (value === 3){
				return "压缩文件";
			}
			else if (value === 4){
				return "上传文件(云端)";
			}
			else if (value === 5){
				return "收到回调(云端)";
			}
			else if (value === 6){
				return "下载文件(云端)";
			}
			else if (value === 7){
				return "解压文件";
			}
			else if (value === 8){
				return "加密文件";
			}
			else if (value === 9){
				return "上传文件(FTP)";
			}
			return "类型未知";
		},
		formatTaskInfoStatus : function(value){
			if (value === 0){
				return "未开始";
			}
			else if (value === 1){
				return "处理中";
			}
			else if (value === 2){
				return "已完成";
			}
			else if (value === -1){
				return "异常";
			}
		}
	};
	return formatter;
});
