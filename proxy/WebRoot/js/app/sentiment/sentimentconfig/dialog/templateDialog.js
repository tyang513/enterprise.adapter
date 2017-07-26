define([ "text!./templates/templateDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.templateDialog", BaseDialog, {
		// default options
		options : {
			entity : 'template'
		},

		templateString : template,
		
		
		_initCombobox : function() {
			app.utils.initComboboxByDict(this.mediaSourceCombobox, 'MediaSource',{
				value : (this.options.item && this.options.item.mediaSource !== undefined) ? this.options.item.mediaSource : undefined,
				isCheckAll : false,
				cached : true
			});
			this.effectDateInfo.datebox({
				onSelect: _.bind(function(date){
					this.expiryDateInfo.datebox('calendar').calendar({
						minDate : date
					});
					minDate : date
					
				},this)
			});
			this.expiryDateInfo.datebox({
				onSelect: _.bind(function(date){
					date.setDate(date.getDate()+1);
					this.effectDateInfo.datebox('calendar').calendar({
						maxDate : date
					});
					maxDate : date
					
				},this)
			});
		},
		
		_initData : function() {
			this._initCombobox();
			if(this.options.action === app.constants.ACT_CREATE) {
				this.div1.hide();
				this.div2.hide();
				this.statusInput.val(app.constants.SENTIMENT_UNENABLE);
				if(this.options.flag){
					this.categoryIdInput.val(this.options.categoryId);
				}
			} else if(this.options.action === app.constants.ACT_EDIT) {
				this.div1.show();
				this.div2.show();
				//this.options.item.ctime = app.formatter.formatTimeStamp1(this.options.item.ctime);
				this.versionSpan.text(this.options.item.version);
				this.codeSpan.text(this.options.item.templateCode);
				this.options.item.effectDate = app.formatter.formatTime(this.options.item.effectDate);
				this.options.item.expiryDate = app.formatter.formatTime(this.options.item.expiryDate);
			}
				
			
		},
		
		_saveData : function(callback, errorback) {
			if (!this.options.entity)
				return;
				this.editForm.ajaxForm(app.buildServiceData(this.options.entity + "/save.do", {
					data : {
						templateCode : this.options.action === app.constants.ACT_EDIT? this.options.item.templateCode:'',
						version : this.options.action === app.constants.ACT_EDIT? this.options.item.version:''
					},
					context : this,
					async:false,
					success : function(data) {
						if (data) {
							var action = "";
							if (!data.msg) {
								if (this.options.action === app.constants.ACT_CREATE) {
									action = "模板新建";
									model=app.constants.ACT_CREATE;
								} else if (this.options.action === app.constants.ACT_EDIT) {
									action = "模板修改";
									model=app.constants.ACT_EDIT;
								}
							}
							if (data.success) {
								app.messager.info(data.msg || action + "成功!");
							} else {
								app.messager.warn(data.msg || action + "失败!");
							}
						}
						callback && callback(data,model);
					},
					error : errorback
				}));
			
			this.editForm.submit();
		}
		
		
		
	});
});