define([], function() {
	// summary:
	// Firebug console wrapper to write log.

	var isIE = !!/msie [\w.]+/.exec(navigator.userAgent.toLowerCase());
	if (isIE) {
		var _nullFn = function() {
		};

		window.console = $.extend({
			debug : _nullFn,
			log : _nullFn,
			info : _nullFn,
			warn : _nullFn,
			error : _nullFn,
			dir : _nullFn
		}, window.console || {});
	}

	return {
		debug : function(message) {
			console.log(message);
		},

		dir : function(message) {
			console.dir(message);
		},

		info : function(message) {
			console.info(message);
		},

		warn : function(message) {
			console.warn(message);
		},

		error : function(message) {
			console.error(message);
		},

		fine : function(message) {
			console.info(message);
		},

		finer : function(message) {
			console.info(message);
		},

		finest : function(message) {
			console.info(message);
		},

		entry : function(message) {
			console.info(message);
		},

		exit : function(message) {
			console.info(message);
		},

		formatMessage : function(messageObj) {
			return (new Date()).toLocaleString() + " [" + messageObj.className + "_" + messageObj.clientId + "." + messageObj.methodName + "]["
					+ messageObj.level + "]" + messageObj.message;
		},

		getLineSeparator : function() {
			return "\n";
		}

	};
});
