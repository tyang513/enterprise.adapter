define([], function() {
	var security = {
		_config : {},
		_loaded : false,
		loadConfig : function() {

		},
		getPermission : function() {

		},

		_getPermissionIds : function(resourceId) {
			var ids = [];
			if (!this._config[resourceId]) {
				_.each(this._config._data, function(resource) {
					if (resource.resourceDesc.indexOf(resourceId) === 0) {
						ids.push(resource.resourceDesc);
					}
				})
			}
			return ids;
		},

		_buildPermissionTree : function(rows) {

		},

		_authorize : function(resourceId, defaultChildren) {
			console.dir([ "authorize", resourceId, defaultChildren ]);
			var resources = defaultChildren;
			var ids = this._getPermissionIds(resourceId);
			resources = [];
			_.each(defaultChildren, function(resource) {
				if (_.indexOf(ids, resourceId + '/' + resource.eventId) > -1) {
					resources.push(resource);
				}
			});

			console.dir([ "enable authorize", this._config[resourceId], ids, resources ]);
			return resources;
		},
		authorize : function(resourceId, defaultChildren, callback, errorback) {
			if (!this._loaded) {
				$.ajax(app.buildServiceData("getUserAcl", {
					data : {},
					success : _.bind(function(data) {
						// this._buildPermissionTree(data);
						this._config._data = data;
						this._loaded = true;

						var resources = this._authorize(resourceId, defaultChildren);
						callback && callback(resources);
					}, this),
					error : function(error) {
						if (errorback) {
							errorback(error);
						} else {
							app.messager.error('获取用户权限异常！');
						}
					}
				}));
			} else {
				var resources = this._authorize(resourceId, defaultChildren);
				callback && callback(resources);
			}
		},

		authorizeForm : function(viewType) {
			var data = app.config.taskViewConfig;
			var taskDijits = [];
			if (viewType && viewType.processcode && viewType.taskcode) {
				taskDijits = data[viewType.processcode] && data[viewType.processcode][viewType.taskcode];
			}
			return taskDijits;
		}
	};
	return security;
});
