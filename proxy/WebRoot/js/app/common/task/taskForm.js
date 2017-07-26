define([ "text!./templates/taskForm.html", "zf/base/BaseWidget", "app/common/task/taskFormDataFormatter", "app/base/BaseTabForm" ], function(template,
		BaseWidget, dataFormatter) {
	return $.widget("app.taskForm", BaseWidget, {
		// default options
		options : {
			ajaxLock : true,
			lockMsg : '数据处理中，请稍候  ...',

			mode : 'edit'
		},

		templateString : template,

		dataFormatter : dataFormatter,

		_create : function() {
			this._super();
			this.taskData = this.options.taskData;
			this.render();
		},

		_getSubmitServiceName : function() {
			return _.string.lowerFirst(this.taskData.processcode) + this.taskData.taskcode + "Submit";
		},

		_getAjaxData : function(eventData, callback, errorback) {
			this.logger.debug();
			console.dir([ "eventData", eventData ]);
			var action = eventData.eventId;
			var service = eventData.service;
			if (service === undefined) {
				service = this._getSubmitServiceName();
			}
			var processCode = this.taskData.processcode;
			var taskCode = this.taskData.taskcode;

			var formData = this.baseTabForm.BaseTabForm("value");
			formData.taskData = this.taskData;
			var postData = this.dataFormatter.getData(processCode, taskCode, action, formData);

			console.dir([ "postData", postData ]);
			return app.buildServiceData(service, {
				data : postData,
				context : this,
				success : callback,
				error : errorback
			});
		},

		_onFormAction : function(event, data) {
			var action = data.eventId;
			
			if (action === "submit") {
				// step3 : 标准的处理结果
				var callback = function(data) {
					console.dir([ "succ data", data ]);
					if (data.msg) {
						if (data.success) {
							app.messager.info(data.msg);
							app.utils.closeTab(this);
						} else {
							app.messager.warn(data.msg);
						}
					}
				};

				var errorback = function(error) {
					console.dir([ "error data", error ]);
					app.messager.error("提交失败.");
				};

				// step1: 验证各组件是否render
				var result = this.baseTabForm.BaseTabForm("validate");
				if (!result) {
					// app.messager.error("验证失败.");
					return;
				}
				var ajaxData = this._getAjaxData(data, callback, errorback);

				// step2: post data to server
				$.ajax(ajaxData);
				console.dir([ "ajaxData", ajaxData ]);

			} else if (action === "view") {
				this._viewSheet();
			} else if (action === "cancel") {
				app.utils.closeTab(this);
			} else if(action === "reject") {
				this._rejectApproveForm();
			} else if(action === "reloadForm") {
				this._reload();
			}
		},
		
		_reload : function() {
			this.logger.debug();
			/** 重新检索数据 */
			$.ajax(app.buildServiceData("queryMyTaskByProcessId", {
				data : {
					processInstanceId : this.taskData.processInstanceId
				},
				context : this,
				success : function(result) {
					if(result) {
						console.dir([ "result", result ]);
						this.taskData = result;
						this.baseTabForm.BaseTabForm('destroy');
						this.render();
					}
				},
				error : function(error) {
					console.dir([ "error data", error ]);
				}
			}));
		},		

		render : function() {
			this.logger.debug();
			$.ajax(app.buildServiceData("getSheetInfo", {
				data : {
					sheetId : this.taskData.sheetId,
					sheetType : this.taskData.sheetType
				},
				context : this,
				success : function(formData) {
					if (formData.msg) {
						app.messager.alert('信息', formData.msg, 'error');
						app.utils.closeTab(this);
						return;
					}
					formData.taskData = this.taskData;
					formData.taskId = this.taskData.id;
					this.formData = formData;
					
					/** 如果当前任务没有接收人，则设置为view，控制“结算商户规则配置”权限 */
					var taskCode = this.taskData.taskcode;
//					if(this.taskData.taskcode === app.constants.SUBJECTUM_MERCHANT_CONF_JOB) {
//						if(this.taskData.assignerumid === null || this.taskData.assignerumid === '') {
//							taskCode = taskCode + "1";
//						}
//					}
					/** 结算单流程，判断是否为车主商城或B2C，用于显示TAB:调整金额 */
					if(this.taskData.sheetType === app.constants.SHEET_TYPE_SETTLE_SHEET) {
						if(formData.settleSheet.categoryName === '车主商城') {
							taskCode = taskCode + "1";
						}
					}
					
					var formConf = {
						readOnly : (this.options.mode === 'view'),
						viewType : {
							taskcode : taskCode,
							processcode : this.taskData.processcode
						}
					};

					this.baseTabForm.BaseTabForm(formConf);
					
					this.baseTabForm.BaseTabForm("render", formData);
				},
				error : function(error) {
					console.dir([ "error data", error ]);
				}
			}));
		},
		
		_viewSheet : function() {
			this.logger.debug();
			var sheetData = this.baseTabForm.BaseTabForm("getSheetData");
			var obj = {
				"title" : sheetData.sheetName + "--" + (sheetData.sheet.code === undefined ? sheetData.sheet.sheetCode : sheetData.sheet.code),
				"url" : sheetData.className,
				"action" : app.constants.ACT_VIEW
			};
			obj[sheetData.sheetObjectName] = sheetData.sheet;
			app.utils.openTab(this, obj);
		},
		
		// 结算主体商户IT复核岗 回退
//		_rejectApproveForm : function() {
//			var taskCode = app.constants.SUBJECTUM_MERCHANT_CONF_JOB;
//			var remark = this.formData.taskData.memo;
//			var taskId = this.formData.taskId;
//			$.ajax(app.buildServiceData("merchantConfProcessSettleMerchantSettleConfRollback", {
//				context : this,
//				global : true,
//				data : {
//					finTaskId : taskId,
//					taskCode : taskCode,
//					memo : remark
//				},
//				success : function(data) {
//					if (data.success) {
//						app.messager.info(data.msg);
//						app.utils.closeTab(this);
//					} else {
//						app.messager.error("IT复核岗回退失败");
//					}
//				}
//			}));
//		},
		
		_destroy : function() {
			this._super();
		}
	});
});
