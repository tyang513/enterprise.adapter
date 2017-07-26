define([ "text!./templates/secondaryIndexErrorRecordDetailDialog.html", "app/base/BaseDialog" ], function(template, BaseDialog) {
	return $.widget("app.secondaryIndexErrorRecordDetailDialog", BaseDialog, {
		// default options
		options : {
			entity : 'secondaryIndexErrorRecordDetail'
		},

		templateString : template,
		
		_initCombobox : function() {
			/*数据库保存数据为空，暂时注释--赵帅兵20141013*/
			/*this.statusCombobox.combobox({
				valueField : 'status',
				textField : 'label',
				data : [ {
					label : '未处理',
					status : '0'
				}, {
					label : '已处理',
					status : '1'
				}]
			});*/
			this.operatorTypeCombobox.combobox({
				valueField : 'status',
				textField : 'label',
				data : [ {
					label : '增加',
					status : '0'
				}, {
					label : '删除',
					status : '1'
				}]
			});	
		}
	});
});