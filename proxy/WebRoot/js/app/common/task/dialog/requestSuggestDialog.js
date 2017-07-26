define([ "text!./templates/requestSuggestDialog.html", "zf/base/BaseWidget", "jqueryplugins/jquery.form" ], function(template, BaseWidget) {
	return $.widget("app.requestSuggestDialog", BaseWidget, {
		// default options
		options : {
			ajaxLock : true
		},

		templateString : template,
		_create : function() {
			this._super();
			this._initCombogrid();

			this.requestSuggestForm.ajaxForm(app.buildServiceData("requestSuggestForm", {
				beforeSubmit : _.bind(function() {
					return this.requestSuggestForm.form('validate');
				}, this),
				context : this,
				global : true,
				success : function(data) {
					if (data.status == 200) {
						this.dialogCallback && this.dialogCallback();
					} else {
						app.messager.error("任务征询失败");
					}
				}
			}));

			$.ajax(app.buildServiceData("getUserInfo", {
				data : {},
				context : this,
				success : function(data) {
					if (data) {
						console.dir([ "data", data ]);
						this._userInfo = data;
						if (data.accreditUser == null) {
							this.loginName = data.user.loginName;
						}
					}
				},
				error : function(error) {
					app.messager.error('获取用户信息异常！');
				}
			}));

		},
		_initCombogrid : function() {
			this.searchUserInput.combogrid({
				panelWidth : 500,
				url : 'sales/getUsers.do',
				idField : 'umid',
				textField : 'username',
				fitColumns : true,
				mode : 'remote',
				columns : [ [ {
					field : 'umid',
					title : '用户名',
					width : 50,
					align : 'center'
				}, {
					field : 'username',
					title : '名字',
					width : 80,
					align : 'center'
				}, {
					field : 'email',
					title : '邮箱',
					width : 80,
					align : 'center'
				} ] ]
			});
			this.finTaskId.val(this.options.finTaskId);
		},
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				if (callback) {
					this.dialogCallback = callback;

					var grid = this.searchUserInput.combogrid('grid');
					if (grid == null || grid == undefined) {
						app.messager.warn("请选择征询人。");
						return;
					}
					var user = grid.datagrid('getSelected');
					if (user == null) {
						app.messager.warn("请选择征询人。");
						return;
					}

					this.finTaskId.val(this.options.finTaskId);
					if (this.loginName && this.loginName == user.umid) {
						app.messager.warn("征询人不能是自己。");
						return;
					}
					this.userId.val(user.umid);
					if (this.remark.val() != "") {
						this.memo.val(this.remark.val());
					}
					this.requestSuggestForm.submit();
				}
			} else if (eventId === "cancel") {
				callback();
			}
		}
	});
});
