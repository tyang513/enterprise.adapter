define([ "text!./templates/changeUserPasswordDialog.html", "zf/base/BaseWidget" ],
		function(template, BaseWidget) {
			return $.widget("app.changeUserPasswordDialog", BaseWidget, {
				// default options
				options : {
					submituserumid : null
				},

				templateString : template,

				_create : function() {
					this._super();
					this._initData();
				},
				
				_initData : function() {
					console.dir(["data", this.options.data])
					//this.userName.text(this.options.data.submituserumid);
				    this.repassword.validatebox({
				        required: true,
				        validType: 'repassword',
						rules : {
							repassword : {
								validator : _.bind(function(inputValue) {
									var isValid = false;
									if(this.newpassword.val() && this.newpassword.val() === inputValue) {
										isValid = true;
									}
									return isValid;
								}, this),
								message : "两次输入密码不匹配"
							}
						}
				    });
					
					this.passwordForm.ajaxForm(
							app.buildServiceData("changeUserPassword", {
							context : this,
							success : function(data){
								
								if(data && data.errorMsg) {
									app.messager.error(data.errorMsg);
								} else {
									app.messager.info('修改用户密码成功！');
									if (this._okCallback) {
										this._okCallback(data);
									}
								}
							},
							error : function(data){
								if(data && data.errorMsg) {
									app.messager.error(data.errorMsg);
								} else {
									app.messager.error('修改用户密码异常！');
								}
								
							}
						}));
				},
				
				execute : function(eventId, callback) {
					if (eventId === "ok") {
						if (callback) {
							if (this._validate()) {
								this._okCallback = callback;
								this.passwordForm.submit();
							}
						}
						
					} else if (eventId === "cancel") {
						if (callback) {
							callback();
						}
					}
				},
				
				_validate : function() {
					return this.passwordForm.form('validate');
				},
				
				_destroy : function() {
					this.logger.debug();
					this._super();
				}

			});
		});
