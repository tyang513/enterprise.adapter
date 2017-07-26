define([ "text!./templates/approveRecord.html", "app/base/BaseFormModule" ], function(template, BaseFormModule) {
	return $.widget("app.approveRecord", BaseFormModule, {
		
		options : {
			finSalesOrder : null
		},
		_initData : function() {
			var formType = this.formType;
			var data = {};
			data = {
						processId : this.formData.taskData.processInstanceId,
						orderId:this.formData.taskData.sheetId,
						processCode : this.formData.taskData.processcode
			};	
			var constants = app.constants;
//			data = {
//					processId : this.formData.taskData.finProcessInstanceId,
//					orderId:this.finSalesOrder.id,
//					processCode : this.formData.taskData.processcode
//			};		
			$.ajax(app.buildServiceData("getBysheetIdAndProcessId", {
				data : data,
				type: 'GET',
				context : this,
				success : function (data, textStatus, jqXHR) {
					var approveTable='<div><span style="font-weight: bold">审批记录</span><br/><br/><table width="100%">';
					var taskTable = '<div><span style="font-weight: bold">任务处理记录</span><br/><br/><table width="100%">';
					$.each(data, function( key, value ) {
						var approveResult="";
						var tipMsg = '';
						if(value.approve==constants.TASK_APPORVE)
							approveResult= "同意";
						else if(value.approve==constants.TASK_DISAPPORVE)
							approveResult= "不同意";
						else if(value.approve==8)
							approveResult= "未审批";
						else if(value.approve==constants.TASK_CONSULT)
							approveResult= "征求意见";
						else if(value.approve==constants.TASK_FORWARD)
							approveResult= "转发处理";
						else
							approveResult= "待审批";
						var endtimeResult="";
						if(value.endtime != null){
							endtimeResult = new Date(value.endtime).format('yy/MM/dd');
						}
						if(value.memo !=null && value.memo.replace(/(^\s*)|(\s*$)/g,'') !=''){
							tipMsg='<a href="javascript:void(0)" title="'+value.memo+'" class="easyui-tooltip"><img src="css/app/themes/orange/images/comment.gif"/></a>';
						}
						if(value.type == 1){
							approveTable += '<tr><td align="left" >'+value.assignername+'</td><td align="center">'+approveResult+tipMsg+'</td><td align="right">'
											+endtimeResult+'</td></tr>';
						}else if(value.type == 2){
							taskTable += '<tr><td align="left" width="70">'+value.taskname+'</td><td align="center">'+value.statusName+tipMsg+'</td><td align="right">'
							+endtimeResult+'</td></tr>';
						}
					});
					approveTable += '</table></div>';
					taskTable += '</table></div>';
					var result = '';
					if (approveTable.indexOf("<tr>") >-1 ){
						result += approveTable;
					}
					if (taskTable.indexOf("<tr>") >-1 ){
						result += '<br/>'+ taskTable;
					}
					$(result).appendTo(this.taskApprove);
			}
			}));
			
		},
		templateString : template,
		
		_create : function() {
			this._super();
			//this._initData();
		},

		// ---------------- interface -----------------
		onFormAction : function(data) {
			var eventId = data.eventId;
			if (eventId === "test") {
				
			}
		},
		validate : function() {
			
			return true;
		},

		value : function() {
			var obj = {};
			return obj;
		},

		render : function(formData) {
			this._super(formData);
			this._initData();//render方法执行后，整个form才有对象的值，所以，initdata方法放到render中执行而非create方法中

		},
		
		//--------------event handler ---------------
		
		_handleShowData : function(event){
			this.logger.debug();
			console.dir(["event", event]);
			var data = event.data;
			var processId = data.processId;
			
			if(data.formId === this.options.formId){//防止其他表单发的请求也处理了(同时打开多个tab),
				var data = {
						processId : processId
					};
					$.ajax(app.buildServiceData("getTaskByProcessId", {
						data : data,
						type: 'GET',
						context : this,
						success : function (data, textStatus, jqXHR) {
							
							var indexTask = 0;
							var iterateTable = "";
							iterateTable+='<div title="订单流程处理记录" collapsible="true" >';
								iterateTable += '<table name="taskList">';
								$.each(data, function( key, value ) {
									if(indexTask == 0){
										if(data.length>1){
											iterateTable +='<tr><td>当前任务:'+value.taskname+'</td></tr>'+
											"<tr><td>当前处理人:"+value.assignername+"</td></tr><tr><td><hr widht:4px/></td></tr>";
										}else{
											iterateTable +='<tr><td>当前任务:'+value.taskname+'</td></tr>'+
											"<tr><td>当前处理人:"+value.assignername+"</td></tr>";
										}
									}else{
										var approveResult="";
										if(value.approve==1)
											approveResult= "同意";
										else if(value.approve==2)
											approveResult= "不同意";
										var endtimeResult="";
										if(value.endtime != null){
											endtimeResult = new Date(value.endtime).format('yyyy-MM-dd');
										}
										iterateTable +="<tr><td>"+value.taskname+"</td>"+
										"<td>"+approveResult+"</td><td>"+endtimeResult+"</td></tr>";
									}
									indexTask++;
								});
								iterateTable += '</table>'; 
								iterateTable +='</div>';
								this.taskInfo.html("");
								$(iterateTable).appendTo(this.taskApprove);
					}
					}));
			}
		},
		
		_destroy : function() {
			this._super();
		}
	});
});
