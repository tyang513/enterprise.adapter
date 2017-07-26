define([ "text!./templates/attachmentDialog.html", "zf/base/BaseWidget", "jqueryplugins/jquery.form" ], function(template, BaseWidget) {
	return $.widget("app.attachmentDialog", BaseWidget, {
		// default options
		options : {
			ajaxLock : true,
			lockMsg : '文件上传中，请稍候  ...',
			uploadService : 'uploadAttachment',
			showTypeCombobox : false,
			showDescription : true,
			typeList : []
		},

		templateString : template,

		_create : function() {
			this._super();
			this.uploading = false;
			this._initData();
		},

		_initData : function() {
			this.logger.debug();
			var dataInfo = {
				sheetId : this.options.sheetId,
				sheetType : this.options.sheetType,
				typeList : this.options.typeList,
			};
			if (this.options.attachmentRows == null || this.options.attachmentRows.length ==0) {
				$.ajax(app.buildServiceData("queryAttachmentBySheetId", {
					data : dataInfo,
					ajaxLock : false,
					context : this,
					success : function(data) {
						if (data && data.rows && data.rows.length > 0) {
							this.options.attachmentRows = data.rows;
						}
					}
				}));
			}

			this.sheetIdInput.val(this.options.sheetId);
			this.sheetTypeInput.val(this.options.sheetType);

			this.uploadAttachmentForm.ajaxForm(app.buildServiceData(this.options.uploadService, {
				context : this,
				success : function(data) {
					this.uploading = false;
					this.dialogCallback && this.dialogCallback(data);
				}
			}));
			this._initCombobox();
		},

		/**
		 * 初始化所有页面可见的下拉框
		 */
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.attachmentType, "attachmentType", {
				loadFilter : _.bind(function(data) {
					var dicts = [];
					for (var i = 0; i < data.length; i++) {
						if (_.indexOf(this.options.typeList, parseInt(data[i].dicitemkey)) != -1) {
							dicts.push(data[i]);
						}
					}
					return dicts;
				}, this),
				onLoadSuccess : _.bind(function(data) {
					console.dir([ 'data', data ]);
					if (data.length == 1) {
						this.attachmentType.combobox('setValue', data[0].dicitemkey);
					}
				}, this),
				onSelect : _.bind(function(record) {
					console.log(record);
					if (record != null && record.dicitemvalue == "对账文件") {
						this.contentMessage.show();
					} else {
						this.contentMessage.hide()
					}

					if (record != null) {
						var attachmentRows = this.options.attachmentRows;
						console.dir([ "attachmentRows", attachmentRows ]);
						for (var i = 0; i < attachmentRows.length; i++) {
							var attachment = attachmentRows[i];
							// if(attachment.type ==
							// this.attachmentType.combobox('getValue')){
							if (record.dicitemvalue == "对账文件") {
								if (attachment.type === 3) {
									this.uploadMessage.html("已存在对账文件,可能会造成对账错误");
									this.uploadMessage.show();
								} else {
									this.uploadMessage.hide();
								}
							} else if (record.dicitemvalue == "对账结果文件") {
								if (attachment.type === 4) {
									this.uploadMessage.html("已存在对账结果文件,可能会造成对账结果错误");
									this.uploadMessage.show();
								} else {
									this.uploadMessage.hide();
								}
							} else {
								this.uploadMessage.hide();
							}
							// }
						}
					}
				}, this)
			});
		},

		_validate : function() {
			// 判断文件是否被移除
			var fileSize = app.utils.getFileSize($(this.uploadAttachmentForm.find(":file")[0]));

			if (!$(this.uploadAttachmentForm.find(":file")[0]).val()) {
				app.messager.warn("请选择要上传的附件");
				return false;
			} else {
				var fileName = $(this.uploadAttachmentForm.find(":file")[0]).val().match(/[^\/]*$/)[0];// 获得文件名
				if (fileName.length > 80) {
					app.messager.warn("附件名称不能大于80个字符，请重新上传附件!");
					return false;
				}
				if (this.attachmentType.combobox('getValue') == "3") {
					if (fileName.indexOf(".xlsx", fileName.length - 5) == -1 && fileName.indexOf(".xls", fileName.length - 4) == -1) {
						app.messager.warn("对账附件必须是以(.xlsx或.xls)结尾的Excel文件!");
						return false;
					}
				}

				if (this.attachmentType.combobox('getValue') === "6") {
					if (fileName.indexOf(".txt", fileName.length - 4) == -1 && fileName.indexOf(".csv", fileName.length - 4) == -1) {
						app.messager.warn("数据补抽附件必须是以(.txt或.csv)结尾的文本文件!");
						return false;
					}
				}
				if(fileName.indexOf(".") == -1) {
					app.messager.warn("文件不正确,不能上传成功!");
					return false;
				}
			}
			return this.uploadAttachmentForm.form('validate');
		},

		execute : function(eventId, callback) {
			if (this.uploading) {
				app.messager.warn("附件上传中,请稍后...");
				return false;
			}
			if (eventId === "ok") {
				if (callback) {
					if (this._validate()) {
						this.uploading = true;
						this.dialogCallback = callback;
						this.uploadAttachmentForm.submit();
					}
				}
			} else if (eventId === "cancel") {
				callback();
			}
		}

	});
});
