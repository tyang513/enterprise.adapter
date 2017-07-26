define([], function() {
	return {
		getData : function(processCode, taskCode, action, formData) {
			// var formatter = "_" + processCode +
			// _.string.capitalize(taskCode);
			var formatter = "_" + _.string.lowerFirst(processCode) + _.string.capitalize(taskCode);

			var data = formData;
			if (this[formatter] && _.isFunction(this[formatter])) {
				data = this[formatter](action, formData);
			} else {
				console.log("未找到对应的format data");
			}
			return data;
		},

		_merchantConfProcessSettleMerchantConf : function(action, formData) {
			var data = {
				taskId : formData.taskData.id,
				remark : formData.taskData.memo,
				sheetId : formData.taskData.sheetId
			};
			return data;
		},
		
		_merchantConfProcessSettleMerchantConfCheck : function(action, formData) {
			var data = {
				taskId : formData.taskData.id,
				remark : formData.taskData.memo,
				sheetId : formData.taskData.sheetId
			};
			return data;
		},
		_settleSheetProcessInvoice : function(action, formData){
			var data = {
					taskId : formData.taskData.id,
					remark : formData.taskData.memo,
					sheetId : formData.taskData.sheetId
				};
			return data;
		}
	};
});
