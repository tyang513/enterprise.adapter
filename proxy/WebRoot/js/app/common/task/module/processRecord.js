define([ "text!./templates/processRecord.html", "app/base/BaseFormModule" ], function(template, BaseFormModule) {
	return $.widget("app.processRecord", BaseFormModule, {

		options : {
			pagination:true
		},
		templateString : template,
		render : function(formData) {
			this._super(formData);
			this._initData();
		},
		_initData : function() {
			var constants = app.constants;
			var formType = this.formType;
			var data = {};

			if (formType === app.constants.SHEET_SUBJECTUM) {
				data.sheetId = this.subjectum.code;
			}
			
			if (formType === app.constants.SHEET_SETTLE_SHEET) {
				data.sheetId = this.settleSheet.sheetCode;
			}
			$.ajax(app.buildServiceData("getBysheetId", {
				data : data,
				type : 'GET',
				context : this,
				success : function(data, textStatus, jqXHR) {
					if ($.isEmptyObject(data)) {// 返回的是个对象{},不能用length方法判断，没数据就隐藏这个模板
						//this._showModule(false);
					}
					var iterateTable = '';
					$.each(data, function(keyList, valueList) {
						var tempList = valueList;
						var value = tempList[tempList.length - 1];
						iterateTable += ' <div>';
						iterateTable += '<div><span style="font-weight: bold">'
							+ value.processname
							+ '处理记录</span><br><br><label>当前任务:</label><span>'
							+ value.taskname
							+ '</span><br></div>'
							+ "<div><span> 当前处理人:"
							+ (value.assignerumid != null && value.assignerumid != "" ? value.assignername : "<a title='"
									+ value.candidateuser + "'>查看候选人</a>") + "</span></div><div class='line'></div><table width='100%'>";
						
						$.each(tempList, function(key, value) {
							var approveResult = "";
							var tipMsg = '';
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
								iterateTable += "<tr><td align='left'>" + value.taskname + "</td>" + "<td align='center'>" + approveResult + tipMsg
										+ "</td><td align='right'>" + endtimeResult + "</td></tr>";
							} else {
								iterateTable += "<tr><td align='left'>" + value.taskname + "</td>" + "<td align='center'>" + value.statusName + tipMsg
										+ "</td><td align='right'>" + endtimeResult + "</td></tr>";
							}

						});

						iterateTable += '</table></div>';
						iterateTable += '<br/>';
					});
					$(iterateTable).appendTo(this.taskInfo);
				}
			}),this);
			
			this.logger.debug();
			this.dataGrid.datagrid(app.buildServiceData("queryprocessInstanceManage", {
				pagination:this.options.pagination,
				queryParams : {sheetid : this.subjectum === undefined ? this.settleSheet.sheetCode : this.subjectum.code},
				onLoadSuccess : _.bind(function(data) {
//					if(data.total==0){
//						this._showModule(false);
//						return;
//					}
				}, this)
				
			}));
			app.utils.bindDatagrid(this, this.dataGrid);
		},
		_formatAct : function(value, row, index) {
			var remark = row.remark == null ?'':row.remark;
			remark = remark.replace(/\n/g, ' ');
			var html = "<a href='javascript:void(0);' class='easyui-linkbutton' data-options=\"starttime:"+row.starttime+",plain:true,id:" + row.id
					+",remark:'" + remark
					+ "'\" onClick=_viewDetail();>查看</a>";
			return app.utils.bind(html, this);
		},
		_viewDetail:function(event){
			var options = app.utils.parseOptions($(event));
			var constants = app.constants;
			this.logger.debug();
			var processId = options.id;
			var remark=options.remark;
			var starttime = options.starttime;
			var formTy = this.formType;// 单子的类型
			var orderInfo = '';
			if(formTy === constants.SHEET_SUBJECTUM){
				orderInfo = this.formData.subjectum;
			} else if(formTy === constants.SHEET_SETTLE_SHEET){
				orderInfo = this.formData.settleSheet;
			}
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : '查看流程记录',
				width : 600,
				height : 300,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/common/task/dialog/processHistoryDialog',
				modal : true,
				data : {
					processId : processId,
					formTy : formTy,
					remark:remark,
					finSalesOrder : orderInfo,
					starttime:starttime
				}

			});
		}
	});
});
