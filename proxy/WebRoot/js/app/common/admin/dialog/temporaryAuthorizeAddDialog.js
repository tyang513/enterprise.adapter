define([ "text!./templates/temporaryAuthorizeAddDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.temporaryAuthorizeAddDialog", BaseWidget, {
		// default options
		options : {
		},

		templateString : template,

		_create : function() {
			this._super();
			this._initData();
		},
		_initData : function() {
			this._initCombobox();
		},
		_initCombobox : function() {
			this.logger.debug();
			/*
			$.ajax(app.buildServiceData("getUsers", {
				data : {},
				context : this,
				success : function(data) {
					this.targetuserumid.combobox({
						data : data,
						panelHeight : 'auto',
						valueField : 'id',
						textField : 'umid'
					});
				},
				error : function(error) {
					app.messager.error('获取授权用户信息异常！');
				}
			}));*/
			this.targetuserumid.combogrid({
				  panelWidth:500,
				  url:'sales/getUsers.do',
				  idField:'umid',
				  textField:'username',
				  fitColumns:true,
				  mode:'remote',
				  columns:[[{ field : 'umid', title : '用户名', width : 50, align : 'center'},
				            { field : 'username', title : '名字', width : 80, align : 'center'},
				            { field : 'email', title : '邮箱', width : 80, align : 'center' }]]
			  });
		},
		_submitForm : function(callback) {

			var targetuserumid = this.targetuserumid.combobox('getValue');
			var starttime = this.starttime.datebox("getValue");
			var endtime = this.endtime.datebox("getValue");
			var data;
			data = {
					starttime :starttime,
					endtime:endtime,
					targetuserumid:targetuserumid,
					type:1//1.用户授权
			};
			console.dir([ "data", data ]);
			$.ajax(app.buildServiceData("saveSalesTakeoverSettingPerson", {
				data : data,
				context : this,
				success : function(data) {
					if(data && data.successMsg){
						app.messager.info(data.successMsg);
						if(this._okCallback && _.isFunction(this._okCallback)){
							this._okCallback(data);//需要调用callback，是为了执行父窗口的handler，让父窗口关闭
						}
					}else{
						app.messager.warn(data.warnInfo);
					}
					//location.reload();
				},
				error : function(error) {
					app.messager.info(error.msg);
				}
			}));
		},
		_confirmInput:function(callback){
			var ret = true;
			var targetuserumid = this.targetuserumid.combobox('getValue');
			var starttime = this.starttime.datebox("getValue");
			var endtime = this.endtime.datebox("getValue");
			
			if(targetuserumid === null || targetuserumid === "" ){
				app.messager.warn("接管人姓名不能为空！");
				ret = false;
				return ret;
			}
			if(starttime === null || starttime === "" ){
				app.messager.warn("开始时间不能为空！");
				ret = false;
				return ret;
			}
			if(endtime === null || endtime === "" ){
				app.messager.warn("结束时间不能为空！");
				ret = false;
				return ret;
			}
			return ret;
		},
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				this._okCallback = callback;
				if(this._confirmInput(callback)){
					this._submitForm(callback);
				}
				
			} else if (eventId === "cancel") {
				if (callback) {
					callback();
				}
			}
		}
	});
});
