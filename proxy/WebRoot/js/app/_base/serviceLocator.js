define([ "jquery", "underscore", "text!app/config/service.json" ], function($, _, serviceConfig) {
	return {
		DEFAULT_XHR_SYNC : false,
		DEFAULT_XHR_TIMEOUT : 60000,
		DEFAULT_XHR_PREVENT_CACHE : true,
		DEFAULT_XHR_HANDLE_AS : "json",

		serviceMap : $.extend({}, eval("(" + serviceConfig + ")")),

		load : function(callback) {
			app.getJSON("js/app/config/service.json", _.bind(function(data) {
				this.serviceMap = $.extend({}, data);
				callback();
			}, this));
		},

		mergeConfig : function(serviceConf) {
			this.serviceMap = $.extend(this.serviceMap, eval("(" + serviceConf + ")"));
		},

		buildServiceData : function(serviceName, options) {
			var serviceData = null;
			if (this.serviceMap[serviceName]) {
				var service = _.clone(this.serviceMap[serviceName]);
				if (options) {
					if (options.queryArgs) {
						service.url = this._buildParameterizedUrl(service, options);
					}

					if (!options.error) {
						options.error = function(jqXHR, textStatus, errorThrown) {
							if (this.logger) {
								this.logger.dir(jqXHR, textStatus, errorThrown);
							} else {
								console.dir([ "ajax error", jqXHR, textStatus, errorThrown ]);
							}
							if (jqXHR.status == 0) {
								// 需终止keepLive
								app.utils.stopKeepLive();
							} else {
								var msg = app.utils.getErrorMsg(this, jqXHR);
								app.messager.alert("检测到后端异常，请联系系统管理员", msg, "error");
							}
						};
					}

					if (options.JSONStringify || service.JSONStringify) {
						options.data = JSON.stringify(options.data);
					}

					if (options.context) {

						var context = options.context;
						if(options.ajaxLock == null || options.ajaxLock == undefined){
							options.ajaxLock = true;
						}
						if (context.options && options.ajaxLock && context.options.ajaxLock) {
							var _beforeSend = options.beforeSend;
							if (_beforeSend) {
								_beforeSend = _.bind(_beforeSend, context);
							}
							var _complete = options.complete;
							if (_beforeSend) {
								_complete = _.bind(_complete, context);
							}

							options.beforeSend = function(jqXHR, settings) {
								var ret = true;
								if (_beforeSend) {
									var ret = _beforeSend(jqXHR, settings);
								}
								if (ret) {
									context.lock();
								}
							};

							options.complete = function(jqXHR, textStatus) {
								context.unlock();
								if (_complete) {
									_complete(jqXHR, textStatus);
								}
							};
						}

					}
				}

				serviceData = $.extend(service, options);
			} else {
				console.dir([ "Warn: 没有找到[" + serviceName + "]的相关配置" ]);
			}

			return serviceData;
		},

		_buildParameterizedUrl : function(service, options) {
			// Substitute paramater holders in url with input parameter values.
			var url = service.url;
			if (options.queryArgs) {
				url = service.url.replace(/\$\{([^\s\}]+)\}/g, function(match, key) {
					// return encodeURIComponent(options.queryArgs[key]);
					return options.queryArgs[key];
				});
			}
			return url;
		},

		destroy : function() {

		}
	};
});
