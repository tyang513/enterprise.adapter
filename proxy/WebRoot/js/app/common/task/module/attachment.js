define([ "text!./templates/attachment.html", "app/base/BaseFormModule", "jqueryplugins/jquery.form" ], function(template, BaseFormModule) {

	return $.widget("app.attachment", BaseFormModule, {
		// default options
		options : {
			sheetId : null, // orderId, redeemId, or cancelSheetId
			sheetType : null, // orderType, redeemType, or
			showValidateStatus : false,// 显示状态
			showErrorMsg : false,// 显示错误信息

			view : "default", // default, simple
			uploadOptions : {
				showTypeCombobox : false,
				showDescription : true,
				showPromptTitle : ''
			},
			typeList : []
		},

		templateString : template,

		_create : function() {
			this._super();
		},

		// ---------------- interface -----------------
		onFormAction : function(data) {
			this.logger.debug();
			console.dir([ 'data', data ]);
			var eventId = data.eventId;
			if (eventId === "uploadAttachment") {
				this._openFileUploadDialog();
			}
		},

		render : function(formData) {
			this._super(formData);
			this.formData = formData;

			if (!this.options.sheetId || !this.options.sheetType) { // TODO:
				// 设置sheetId和sheetType
				if (this.formData.formType === app.constants.SHEET_SUBJECTUM) {
					this.options.sheetId = this.formData.subjectum.id;
					this.options.sheetType = app.constants.SHEET_TYPE_SUBJECTUM;
				} else if (this.formData.formType === app.constants.SHEET_SETTLE_SHEET) {
					this.options.sheetId = this.formData.settleSheet.id;
					this.options.sheetType = app.constants.SHEET_TYPE_SETTLE_SHEET;
				} else if (this.formData.formType === app.constants.SHEET_RECON_BATCH) {
					this.options.sheetId = this.formData.reconBatch.id;
					this.options.sheetType = app.constants.SHEET_TYPE_RECON_BATCH;
					if (this.formData.reconBatch.status === "8") {
						this.options.readOnly = true;
					}
				} else if (this.formType === app.constants.ADJUST_AMOUNT_SHEET) {
					this.options.sheetId = this.formData.sheetId;
					this.options.sheetType = app.constants.SHEET_TYPE_ADJUST_SHEET;
					this.options.readOnly = this.formData.readOnly;

				} else if (this.formData.formType === app.constants.SHEET_SETTLE_BATCH) {
					this.options.sheetId = this.formData.settleBatch.id;
					this.options.sheetType = app.constants.SHEET_TYPE_SETTLE_BATCH;
					if (this.formData.settleBatch.status === app.constants.SETTLE_BATCH_STATUS_RECOGNIZED
							|| this.formData.settleBatch.status === app.constants.SETTLE_BATCH_STATUS_PENDING_SETTLEMENT
							|| this.formData.settleBatch.status === app.constants.SETTLE_BATCH_STATUS_ALREADY_SETTLEMENT) {
						this.options.readOnly = true;
					}
				}
			}

			if (!this.options.sheetId || !this.options.sheetType) {
				this.logger.error("sheetId与sheetType不能为空");
				return;
			}

			this._loadData();
			this._initData();

			// 按钮下移 start
			// if(this.options.readOnly) {
			// this.editBtn.hide();
			// }
			// 按钮下移 end
		},

		validate : function() {
			// var ret = true;
			// if (!this.options.readOnly && this.options.notNull) {
			// app.messager.warn('请至少上传附件！');
			// ret = false;
			// }
			// return ret;
			if (this.options.notNull) {
				if (this.attachmentGrid.datagrid("getRows").length <= 0) {
					app.messager.warn("请至少上传附件！");
					return false;
				}
			}
			return true;
		},

		value : function() {
			this.logger.debug();
			var val = {
				attachments : this.attachmentGrid.datagrid('getRows')
			};
			return val;
		},

		// -----------------functions-------------------------

		_initData : function() {
			var gridInfo = this._buildGridInfo();
			this.attachmentGrid.datagrid(gridInfo);
		},

		_openFileUploadDialog : function() {
			var data = $.extend(this.options.uploadOptions, {
				sheetId : this.options.sheetId,
				sheetType : this.options.sheetType,
				typeList : this.options.typeList,
				attachmentRows : this.attachmentGrid.datagrid("getRows")
			});

			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : "上传附件",
				autoClose : false,
				buttons : [ {
					text : '上传',
					eventId : "ok",
					handler : _.bind(function(data) {
						dlg.dialogExt("destroy");
						if (data.success) {
							app.messager.info('附件上传成功！');
							this.attachmentGrid.datagrid("reload");
						} else {
							app.messager.warn(data.msg);
						}

						this._loadData();
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt("destroy");
					}
				} ],
				width : 280,
				height : 270,
				closed : false,
				cache : false,
				className : 'app/common/task/dialog/attachmentDialog',
				data : data,
				modal : true
			});
		},

		// -----------------------------------接口--------------------------------------------
		openFileUploadDialog : function() {
			this._openFileUploadDialog();
		},

		_buildGridInfo : function() {
			var gridInfo = {};

			if (this.options.view === "simple") {
				gridInfo = {
					width : 280,
					rownumbers : false,
					singleSelect : true,
					columns : [ [ {
						field : 'id',
						hidden : true
					}, {
						field : 'name',
						title : '附件名称',
						width : 250,
						align : 'center',
						formatter : app.formatter.formatdownloadAttachment1
					} ] ]
				};
			} else if (this.options.view === "validate") {
				gridInfo = {
					width : 680,
					rownumbers : false,
					singleSelect : true,
					nowrap : false,
					nowrap : false,
					columns : [ [ {
						field : 'id',
						hidden : true
					}, {
						field : 'name',
						title : '附件名称',
						width : 250,
						align : 'center',
						formatter : app.formatter.formatdownloadAttachment1
					}, {
						field : 'validateStatusName',
						title : '校验状态',
						width : 50,
						formatter : app.formatter.formatterAttachmentType,
						align : 'center'
					}, {
						field : 'errormsg',
						title : '校验错误信息',
						width : 350,
						align : 'center'
					} ] ]
				};
			} else {
				gridInfo = {
					width : 730,
					rownumbers : false,
					singleSelect : true,
					border : false,
					nowrap : false,
					columns : [ [ {
						field : 'id',
						hidden : true
					}, {
						field : 'name',
						title : '附件名称',
						width : 150,
						align : 'center',
						formatter : _.bind(this._formatdownloadAttachment, this)
					}, {
						field : 'type',
						title : '类型',
						width : 50,
						align : 'center',
						formatter : app.formatter.formatterAttachmentType
					}, {
						field : 'ctime',
						title : '上传时间',
						width : 80,
						align : 'center',
						formatter : app.formatter.formatTime
					}, {
						field : 'submitUserName',
						title : '上传人',
						width : 80,
						align : 'center'
					}, {
						field : 'description',
						title : '说明',
						width : 100,
						align : 'center',
						formatter : function(value) {
							// if (value && value.length > 10) {
							// var valueSub = value.substr(0, 10) + "...";
							// var valueHtml = "<span title='" + value + "' +
							// alt='" + value + "'>" + valueSub + "</span>";
							// return valueHtml
							// }
							return "<div style='word-break:break-all;white-space:normal;text-align:center;'>" + value + "</div>";

							// return value;
						}
					} ] ]
				};
			}

			if (this.options.showValidateStatus) {
				gridInfo.columns[0].push({
					field : 'validateStatusName',
					title : '校验状态',
					width : 50,
					formatter : app.formatter.formatterAttachmentType,
					align : 'center'
				});
				gridInfo.width += 50;
			}

			if (this.options.showErrorMsg) {
				gridInfo.columns[0].push({
					field : 'errormsg',
					title : '校验错误信息',
					width : 350,
					align : 'center',
					formatter : _.bind(this._formatErrorMsgTip, this)
				});
				gridInfo.width += 350;
			}

			if (!this.options.readOnly) {
				gridInfo.columns[0].push({
					field : 'act',
					title : '操作',
					width : 150,
					align : 'center',
					formatter : _.bind(this._formatAct, this)
				});
				gridInfo.width += 60;
			}

			return gridInfo;
		},

		_formatdownloadAttachment : function(value, row, index) {
			if (!value) {
				value = "";
			}
			return '<a href="sysAttachment/downloadAttachmentById.do?id=' + row.id + '" target="blank" style="color:blue">' + value + '</a>';
		},

		_formatAct : function(value, row, index) {
			var html = "<a href='javascript:void(0);' class='easyui-linkbutton' data-options=\"plain:true,id:" + row.id + "\" onClick=_delete();>删除</a>";
			return app.utils.bind(html, this);
		},

		_formatErrorMsgTip : function(value, row, index) {
			var titleMsg = '';
			if (row.errormsg && row.errormsg != 'null') {
				titleMsg = row.errormsg;
			}

			var html = "<span title='" + titleMsg + "'>" + value + "<span>";
			return app.utils.bind(html, this);
		},

		_getQueryData : function() {
			var data = {};
			if (this.formType === app.constants.SHEET_SUBJECTUM) {
				data = {
					sheetId : this.subjectum.id,
					typeList : this.options.typeList,
					sheetType : app.constants.SHEET_TYPE_SUBJECTUM
				};
				this.options.sheetId = this.subjectum.id;
			} else if (this.formType === app.constants.SHEET_RECON_BATCH) {
				data = {
					sheetId : this.reconBatch.id,
					typeList : this.options.typeList,
					sheetType : app.constants.SHEET_TYPE_RECON_BATCH
				};
				this.options.sheetId = this.reconBatch.id;
			} else if (this.formType === app.constants.SHEET_SETTLE_SHEET) {
				data = {
					sheetId : this.settleSheet.id,
					typeList : this.options.typeList,
					sheetType : app.constants.SHEET_TYPE_SETTLE_SHEET
				};
				this.options.sheetId = this.settleSheet.id;
			} else if (this.formType === app.constants.ADJUST_AMOUNT_SHEET) {
				data = {
					sheetId : this.options.sheetId,
					typeList : this.options.typeList,
					sheetType : app.constants.SHEET_TYPE_ADJUST_SHEET
				};
			} else if (this.formType === app.constants.SHEET_SETTLE_BATCH) {
				data = {
					sheetId : this.settleBatch.id,
					typeList : this.options.typeList,
					sheetType : app.constants.SHEET_TYPE_SETTLE_BATCH
				};
			} else {
				this.logger.warn("using self check formType!!");
			}

			return data;
		},

		_loadData : function() {
			$.ajax(app.buildServiceData("queryAttachmentBySheetId", {
				data : this._getQueryData(),
				context : this,
				success : function(data) {
					this.attachmentGrid.datagrid('loadData', data);

					if (data && data.rows && data.rows.length > 0) {
						if (this.options.view !== "validate") {
							var errorMsg = '';
							for (var i = 0; data.length > i; i++) {
								if (data[i].errormsg && errorMsg.indexOf(data[i].errormsg) < 0) {
									errorMsg += '附件：' + data[i].name + '，校验错误信息：' + data[i].errormsg + '。';
								}
							}

							if (errorMsg != '') {
								this.attachmentErrorMsg.text(errorMsg);
								this.attachmentErrorMsg.show();
							}
						}

					} else {
						this.attachmentErrorMsg.text('');
						this.attachmentErrorMsg.hide();
					}
				},
				error : function(error) {
					app.messager.error('附件查询异常！');
				}
			}));
		},

		_delete : function(event) {
			this.logger.debug();
			var options = app.utils.parseOptions($(event));
			var id = options.id;
			app.messager.confirm("提示", "确认要删除附件吗？", _.bind(function(b) {
				if (b) {
					$.ajax(app.buildServiceData("delAttachmentById", {
						data : {
							id : id
						},
						context : this,
						success : function(data) {
							if (data.success) {
								app.messager.info('附件删除成功！');
							} else {
								app.messager.warn(data.msg);
							}
							this._loadData();
						},
						error : function(error) {
							app.messager.error('附件删除异常！');
						}
					}));
				}
			}, this));

		},

		_destroy : function() {
			this._super();
		}
	});
});
