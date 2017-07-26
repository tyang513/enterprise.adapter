define([ "text!./templates/openTaskOverDialog.html", "zf/base/BaseWidget" ],
		function(template, BaseWidget) {
			return $.widget("app.openTaskOverDialog", BaseWidget, {
				// default options
				options : {},

				templateString : template,

				_create : function() {
					this._super();
					this._initCombobox();
					this._initdatas();
					
				},
				
				_updateTaskOver : function(callback) {
					var servicename = '';
					if(this.options.mode == 'edit'){
						servicename = 'updateSalesTakeoverSetting';
					}else{
						servicename = 'saveSalesTakeoverSetting';
					}
					
					//var userumid = this.userumid.combogrid('getValue');
					//var targetuserumid = this.targetuserumid.combogrid('getValue');
					
					this.addOrEditTaskOverForm.ajaxForm(
							app.buildServiceData(servicename, {
							beforeSubmit : _.bind(function() {
								var userumid = this.userumid.combogrid('getValue');
								var targetuserumid = this.targetuserumid.combogrid('getValue');
								if(userumid === targetuserumid){
									app.messager.alert('', '被接管人不能与接管人相同,请重新选择!');
									return false;
								}
								return this.addOrEditTaskOverForm.form('validate');
							}, this),
							context : this,
							success : function(data){
								if(data && data.sucMessage){
									app.messager.info(data.sucMessage);
								}else{
									app.messager.warn(data.failMessage);
								}
								callback(data);
							},
							error : function(data){
								callback && callback();
							}
						}));
					this.addOrEditTaskOverForm.submit();
			
					/*$.ajax(app.buildServiceData(servicename, {
						data : {
							id : this.id.val(),
							userumid : userumid,
							targetuserumid : targetuserumid,
							starttime : this.starttime.datebox('getValue'),
							endtime : this.endtime.datebox('getValue')
						},
						context : this,
						success : function(data) {
							if (data.sucMessage) {
								app.messager.info(data.sucMessage);
								
							}else{
								app.messager.warn(data.failMessage);
							}
							callback();
						}
					}));*/
				},
				_initdatas : function() {
					if(this.options.mode =="edit"){	
						this.id.val(this.options.data.id);
						this.userumid.combogrid('setValue',this.options.data.userumid);
						this.targetuserumid.combogrid('setValue',this.options.data.targetuserumid);
						this.starttime.datebox('setValue',this.options.data.starttime);
						this.endtime.datebox('setValue',this.options.data.endtime);
					}
					
				},
				_initCombobox : function() {
					this.logger.debug();
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
					this.userumid.combogrid({
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
					this.starttime.datebox({
						onSelect: _.bind(function(date){
							this.endtime.datebox('calendar').calendar({
								minDate : date
							});
							
						},this)
					});
					this.endtime.datebox({
						onSelect: _.bind(function(date){
							this.starttime.datebox('calendar').calendar({
								maxDate : date
							});
							
						},this)
					});
					
				},
				_confirmInput:function(){
					return true;
				},
				execute : function(eventId, callback) {
					if (eventId === "ok") {
						if(this._confirmInput()){
							this._updateTaskOver(callback);	
						}
					} else if (eventId === "cancel") {
						if (callback) {
							callback();
						}
					}
				}

			});
		});
