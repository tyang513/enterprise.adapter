define([ "text!./templates/userSearchDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.userSearchDialog", BaseWidget, {
		// default options
		options : {},

		templateString : template,

		_create : function() {
			this._super();
			this._initData();
		},

		// -------------------interface------------------
		execute : function(eventId, callback) {
			if (eventId === "ok") {
				var rowData = this.userListGrid.datagrid("getSelected");
				if (callback && rowData) {
					callback(rowData);
				}
			} else if (eventId === "cancel") {
				if (callback) {
					callback();
				}
			}
		},

		_doSearch : function(value) {
			this.userListGrid.datagrid('load', {
				umidOrUsername : value
			});
		},

		_initData : function() {
			this.logger.debug();
			this.searchbox.searchbox({
				searcher : _.bind(this._doSearch, this)
			});

			this.userListGrid.datagrid(app.buildServiceData("getUsers"));
		}
	});
});
