define([ "text!./templates/taskInfoSearchBar.html", "app/base/BaseSearchBar", "text!app/proxy/task/config/taskInfoSearchBar.json" ], function(template, BaseSearchBar, searchBarConfig) {
	app.config.searchBar = $.extend(app.config.searchBar, eval("(" + searchBarConfig + ")"));
	return $.widget("app.taskInfoSearchBar", BaseSearchBar, {
		// default options
		options : {
			enableFilter : true,
			searchBar : 'taskInfo',
			autoRender : false
		},

		templateString : template,
		value : function() {
			this.logger.debug();
			return {
				taskCode : $.trim(this.taskCode.val()),
				taskName : $.trim(this.taskName.val()),
				partnerFullName : $.trim(this.partnerFullName.val()),
				type : $.trim(this.type.val()),
				status : this._getStatus(),
				fileName : $.trim(this.fileName.val()),
				refDmpId : $.trim(this.refDmpId.val()),
				refCloudId : $.trim(this.refCloudId.val())
			};
		},
		_getStatus : function() {
			console.dir(['options', this.options.condition]);
			var status = "0,1,2";
			var _status = app.utils.getQueryStringFormUrl(this.options.condition, "status");
			if (_status === "unfinished") {
				status = "0,1";
			} else if (_status === "exception") {
				status = "-1";
			} else if (_status === "finished") {
				status = "2";
			}
			return status;
		}
	});
});
