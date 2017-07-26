define([ "text!./templates/mailTemplateSearchDialog.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.mailTemplateSearchDialog", BaseWidget, {
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
				var rowData = this.mailTemplateListGrid.datagrid("getSelected");
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
			this.mailTemplateListGrid.datagrid('load', {
				codeOrTitle : value
			});
		},

		_initData : function() {
			this.logger.debug();
			this.searchbox.searchbox({
				searcher : _.bind(this._doSearch, this)
			});

			this.mailTemplateListGrid.datagrid(app.buildServiceData("getMailTemplates"));
		}
	});
});
