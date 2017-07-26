define([ "text!./templates/processCurrentStep.html", "app/base/BaseFormModule" ], function(template, BaseFormModule) {
	return $.widget("app.processCurrentStep", BaseFormModule, {

		options : {
			// sub events
			subEvents : {
				"app/processrecord/showdata" : "_handleShowData"
			},
			finSalesOrder : null
		},
		_initData : function() {
			
//			if(this.formData.subjectum.status==="-3"){
//				this._showModule(false);
//				return;
//			}
			
			var constants = app.constants;
			var formType = this.formType;
			var data = {};

			var status="";
			//组装审批链查询条件
			var templateCodes = new Array();// 组合查询条件
			if (formType === app.constants.SHEET_SUBJECTUM) {
				data.sheetId = this.subjectum.code;
				status = this.subjectum.status;
				templateCodes.push(app.constants.MERCHANT_CONF_TEMPLATE);
			} else if (formType === app.constants.SHEET_SETTLE_SHEET) {
				data.sheetId = this.settleSheet.sheetCode;
				status = this.settleSheet.status;
				templateCodes.push(app.constants.SETTLE_SHEET_APPLY_TEMPLATE);
			}
			
			data.templateCode=templateCodes;
			
			$.ajax(app.buildServiceData("getBysheetId2", {
				data : data,
				type : 'GET',
				context : this,
				success : function(data, textStatus, jqXHR) {
					if ($.isEmptyObject(data.progressRecord)) {// 返回的是个对象{},不能用length方法判断，没数据就隐藏这个模板
						this._showModule(false);
						return;
					}
					
					//组装默认审批链
					var defaultApproveChainList={};
					if (formType === app.constants.SHEET_SUBJECTUM) {
						for(var i in data.MerchantConfTemplate){
							var te={};
							te.taskname=data.MerchantConfTemplate[i].taskname.split('：')[0];
							te.assignername=data.userApproveChainSteps[i].approverName;
							te.taskcode=data.MerchantConfTemplate[i].taskcode;
							te.isUsed=false;
							defaultApproveChainList[te.taskcode]=te;
						}
					} else if(formType === app.constants.SHEET_SETTLE_SHEET) {
						for(var i in data.SettleSheetTemplate){
							var te={};
							te.taskname=data.SettleSheetTemplate[i].taskname.split('：')[0];
							te.assignername=data.userApproveChainSteps[i].approverName;
							te.taskcode=data.SettleSheetTemplate[i].taskcode;
							te.isUsed=false;
							defaultApproveChainList[te.taskcode]=te;
						}
					}
					//取得当前审批链
					var objTemp;
					var objt;
					var objlist={};
					for(var t in data.progressRecord){
						objTemp=data.progressRecord[t];
						objt=t;
					}
					objlist[objt]=objTemp;
					var iterateTable = '';
					$.each(objlist, function(keyList, valueList) {
						var tempList = valueList;
						if(tempList !== undefined){
						var value = tempList[tempList.length - 1];
						iterateTable += ' <div>';
						iterateTable += '<div><span style="font-weight: bold">'
							+ value.processname
							+ '处理记录</span><br><br>'+
							((status==="-2")?'':(
									'<label>当前任务:</label><span>'
									+ value.taskname
									+ '</span>'
							))
							+
							'<br></div>'
							+ "<div>" +
							((status==="-2")?'':(
									"<span> 当前处理人:"
									+ (value.assignerumid != null && value.assignerumid != "" ? value.assignername : "<a title='"
											+ value.candidateuserStr + "'>查看候选人</a>") + "</span>"
							))
									 +
											"</div>" +
											((status==="-2")?"":"<br/><div class='custom-line' style='margin-left: 70px;'></div><div class='line'></div>") +
											"<table width='100%' style='margin-top: 20px;'>";
						
						var approveTable='<div><span style="font-weight: bold">审批记录</span><br/><br/><table width="100%">';
						var taskTable = '<div><span style="font-weight: bold">任务处理记录</span><br/><br/><table width="100%">';
						$.each(tempList, function(key, value) {
							//遍历审批链，设置属性isUsed属性
							for(var de in defaultApproveChainList){
								if(defaultApproveChainList[de].taskcode==value.taskcode){
									defaultApproveChainList[de].isUsed=true;
								}
							}
							
							var approveResult = "";
							var tipMsg = '';
							var assignername=((value.assignername=="")?"":value.assignername);
							if(assignername==''&&defaultApproveChainList[value.taskcode]!=null){
								assignername=defaultApproveChainList[value.taskcode].assignername;
							}
							if (value.approve == constants.TASK_APPORVE)
								approveResult = "同意";
							else if (value.approve == constants.TASK_DISAPPORVE)
								approveResult = "不同意";
							else if (value.approve == constants.TASK_CONSULT)
								approveResult = "征求意见";
							else if (value.approve == constants.TASK_FORWARD)
								approveResult = "转发处理";
							else
								approveResult = "待审批";
							var endtimeResult = "";
							if (value.endtime != null) {
								endtimeResult = new Date(value.endtime).format('yy/MM/dd');
							}
							if (value.memo != null && value.memo.replace(/(^\s*)|(\s*$)/g, '') != '') {
								tipMsg = '<a href="javascript:void(0)" title="' + value.memo
										+ '" class="easyui-tooltip"><img src="css/app/themes/orange/images/comment.gif"/></a>';
							}
							if (value.type == 1) {
								approveTable += "<tr><td align='left' style='padding: 2px;'>" + value.taskname + "</td>" + "<td align='center'>" + approveResult + tipMsg
										+ "</td><td align='right' width='60'>" +assignername+((endtimeResult=="")?"":"("+ endtimeResult+")") +  "</td></tr>";
							} else {
								taskTable += "<tr><td align='left' style='padding: 2px;'>" + value.taskname + "</td>" + "<td align='center'>" + value.statusName + tipMsg
										+ "</td><td align='right' width='60'>" +assignername+((endtimeResult=="")?"":"("+ endtimeResult+")") + "</td></tr>";
							}

						});
						
						//拼接没有执行的流程信息
						//审批不通过不显示
						if(status!== "-2"){
							for(var def in defaultApproveChainList){
								if(defaultApproveChainList[def].isUsed==false){
									iterateTable+='<tr><td align="left"  style="padding: 2px;">'+defaultApproveChainList[def].taskname+'</td><td align="center">待处理</td><td align="right" width="60">'
									+defaultApproveChainList[def].assignername+'</td></tr>';
								}
							}
						}
						approveTable += '</table></div>';
						taskTable += '</table></div>';
						if (approveTable.indexOf("<tr>") >-1 ){
							iterateTable += approveTable;
						}
						
						if (taskTable.indexOf("<tr>") >-1 ){
							iterateTable += '<br/>'+ taskTable;
						}
						iterateTable += '</table></div>';
						iterateTable += '<br/>';
					}
					});
					$(iterateTable).appendTo(this.taskInfo);
				}
			}));

		},
		templateString : template,

		_create : function() {
			this._super();
			// this._initData();
		},

		// ---------------- interface -----------------
		onFormAction : function(data) {
			var eventId = data.eventId;
			if (eventId === "test") {

			}
		},
		validate : function() {

			return true;
		},

		value : function() {
			var obj = {};
			return obj;
		},

		render : function(formData) {
			this._super(formData);
			this._initData();// render方法执行后，整个form才有对象的值，所以，initdata方法放到render中执行而非create方法中

		},

		// --------------event handler ---------------

		_handleShowData : function(event) {
			this.logger.debug();
			console.dir([ "event", event ]);
			var data = event.data;
			var processId = data.processId;

			if (data.formId === this.options.formId) {// 防止其他表单发的请求也处理了(同时打开多个tab),
				var data = {
					processId : processId
				};
				$.ajax(app.buildServiceData("getTaskByProcessId", {
					data : data,
					type : 'GET',
					context : this,
					success : function(data, textStatus, jqXHR) {

						var indexTask = 0;
						var iterateTable = "";
						iterateTable += '<div title="订单流程处理记录" collapsible="true" >';
						iterateTable += '<table name="taskList">';
						$.each(data, function(key, value) {
							if (indexTask == 0) {
								if (data.length > 1) {
									iterateTable += '<tr><td>当前任务:'
											+ value.taskname
											+ '</td></tr>'
											+ "<tr><td>当前处理人:"
											+ (value.assignerumid != null && value.assignerumid != "" ? value.assignername : "<a title='"
													+ value.candidateUserName + "'>查看候选人</a>") + "</td></tr><tr><td><hr widht:4px/></td></tr>";
								} else {
									iterateTable += '<tr><td>当前任务:'
											+ value.taskname
											+ '</td></tr>'
											+ "<tr><td>当前处理人:"
											+ (value.assignerumid != null && value.assignerumid != "" ? value.assignername : "<a title='"
													+ value.candidateUserName + "'>查看候选人</a>") + "</td></tr>";
								}
							} else {
								var approveResult = "";
								if (value.approve == 1)
									approveResult = "同意";
								else if (value.approve == 2)
									approveResult = "不同意";
								var endtimeResult = "";
								if (value.endtime != null) {
									endtimeResult = new Date(value.endtime).format('yyyy-MM-dd');
								}
								iterateTable += "<tr><td>" + value.taskname + "</td>" + "<td>" + approveResult + "</td><td>" + endtimeResult + "</td></tr>";
							}
							indexTask++;
						});
						iterateTable += '</table>';
						iterateTable += '</div>';
						this.taskInfo.html("");
						$(iterateTable).appendTo(this.taskInfo);
					}
				}));
			}
		},

		_destroy : function() {
			this._super();
		}
	});
});
