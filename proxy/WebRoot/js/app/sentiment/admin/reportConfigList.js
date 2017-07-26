define([ "text!./templates/reportConfigList.html", "app/base/BaseContentList", "app/sentiment/admin/searchbar/reportConfigSearchBar", "text!app/sentiment/admin/config/reportConfigService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.reportConfigList", BaseContentList, {
		// default options
		options : {
			entity : 'reportConfig',
			entityName : '报表配置',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 750,
				height : 420,
				className : 'app/sentiment/admin/dialog/reportConfigDialog'
			},
			formConfig : {
				url : 'app/sentiment/admin/reportConfigForm'
			}
		},

		templateString : template,
		_handleExecute : function(data) {
			this.logger.debug();
			var row = data.row;
			var sqlTemplate = row.sqlStr;
			if(sqlTemplate === null || sqlTemplate === "") {
				app.messager.warn("未配置SQL模板！");
				return ;
			}
			var rule = "${param";
			var k=0,sum=0;
			k=sqlTemplate.indexOf(rule);
			while(k>-1) {
				sum+=1;
				k=sqlTemplate.indexOf(rule,k+1);
			}
			/**
			 * 1.验证是否需要输入动态文件名及邮箱地址
			 * 	1.1 邮件类型为"job任务邮件列表"时，需输入邮件内容	
			 * 2.是否使用动态文件名
			 */
			if( sum === 0 && row.emailType !== app.constants.REPORT_CONFIG_EMAIL_TYPE_JOB_TASK && 
					row.isUseDynamicFileName !== app.constants.LOGIC_BASIS_STATUS_YES) {
				$.ajax(app.buildServiceData(this.options.entity + "/reportExecute.do", {
					data : row,
					context : this,
					success : function(data) {
						if (data) {
							if (data.success) {
								app.messager.info(data.msg);
							} else {
								app.messager.warn(data.msg);
							}
						}
					}
				}));
			} else {
				row.paramCount = sum;
				this._openReportExecuteDialog({
					item : row,
					action : app.constants.ACT_EDIT
				});
			}
		},
		
		_openReportExecuteDialog : function(data) {
			var dlg = $("<div>").dialogExt({
				data : data,
				title : "报表执行",
				width : 620,
				height : 200,
				closed : false,
				autoClose : false,
				cache : false,
				className : 'app/sentiment/admin/dialog/reportExecuteDialog',
				modal : true,
				buttons : [ {
					text : '确认',
					eventId : "ok",
					handler : _.bind(function(data) {
						if (data) {
							if (data.success) {
								dlg.dialogExt('destroy');
								app.messager.info(data.msg);
							} else {
								app.messager.warn(data.msg);
							}
						}
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt('destroy');
					}
				} ]
			});
		},
		
	});
});
