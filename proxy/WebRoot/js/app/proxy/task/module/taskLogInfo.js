define([ "text!./templates/taskLogInfo.html", "app/base/BaseFormModule"], function(template,
		BaseFormModule, searchBar) {
	return $.widget("app.taskLogInfo", BaseFormModule, {
		// default options
		options : {},

		templateString : template,
		// ---------------- interface -----------------
		onFormAction : function(data) {
			this.logger.debug();
			console.dir([ 'data', data ]);
			var eventId = data.eventId;
			if (eventId === "editSettleSheet") {
			}
		},
		render : function(formData) {
			this._super(formData);
			console.dir([ 'formData', formData ]);
			this._initData();
		},
		_initData : function() {
			
			this.dataGrid.datagrid(app.buildServiceData("taskLog/list.do", {
				queryParams : {
					taskCode : this.formData.taskInfo.taskCode
				},
				onLoadSuccess : _.bind(function(rowData) {
				}, this)
			}));
			app.utils.bindDatagrid(this, this.dataGrid);
		},
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');
		}
	});
});