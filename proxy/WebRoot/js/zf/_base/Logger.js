define([ "jquery", "underscore", "./ConsoleLogWriter", "text!app/config/log-config.json" ], function($, _, consoleLogWriter, logConfig) {
	var Logger = function(client, level, writer) {

		// allow instantiation without "new" keyword
		if (!this._create) {
			return new Logger(client, level, writer);
		}

		// allow instantiation without initializing for simple
		// inheritance
		// must use "new" keyword (the code above always passes
		// args)
		if (arguments.length) {
			this._create(client, level, writer);
		}
	};

	Logger.prototype = {
		defaultLevel : "error",
		logLevel : {},
		defaultWriterClassName : "ConsoleLogWriter",
		defaultWriter : null,

		levels : {
			// log levels from 1 - 8
			finest : 1,
			finer : 2,
			fine : 3,
			debug : 4,
			info : 5,
			warn : 6,
			error : 7,
			off : 8,
			// end of log levels
			// the following is for the mapping between levels
			// and
			// methods
			dir : 3,
			entry : 3,
			exit : 3
		},

		/**
		 * @param client
		 * @param level
		 * @param writer
		 */
		_create : function(client, level, writer) {
			this.client = {};
			if (client) {
				this.client["widgetName"] = client.widgetName || "UnknownClass";
				this.client["id"] = client.uuid || "undifinedId";
			} else {
				this.client["declaredClass"] = "UnknownClass";
				this.client["id"] = "undifinedId";
			}
			// name of class, follow the "nom" naming style used
			// by dojo
			this.noc = this.client.widgetName;
			// name of class formated
			this.nocf = this.formatClassName(this.noc);
			// this.register(client);
			// input level > lookup level (> default level) >
			// fallback
			this.level = level || this.levels[this.lookupLogLevel(this.noc)] || 1;
			this.writer = writer || this.defaultWriter;
		},

		/**
		 * Look up the log level.
		 * 
		 * @param className
		 *            (string) the className
		 */
		lookupLogLevel : function(className) {
			var pattern = className;
			do {
				if (this.logLevel[pattern]) {
					return this.logLevel[pattern];
				}
				pattern = pattern.substring(0, pattern.lastIndexOf("."));
			} while (pattern.length > 0)
			// console.debug("[Logger.findLevel]" + className +
			// "'s
			// logger level
			// is not configured. Default level will be
			// applied.");
			return this.defaultLevel;
		},

		/**
		 * fine level
		 * 
		 * @param message
		 *            (string) Log content.
		 */
		fine : function(/* object */message /* ,... */) {
			// message:
			// Log content.
			if (this.level > this.levels.fine) {
				return;
			}

			this.log(this.fine.caller.nom, Array.prototype.slice.call(arguments), "fine");
		},

		/**
		 * debug level
		 * 
		 * @param message
		 *            (string) Log content.
		 */
		debug : function(/* object */message /* ,... */) {
			// message:
			// Log content.
			if (this.level > this.levels.debug) {
				return;
			}

			this.log(this.debug.caller.nom, Array.prototype.slice.call(arguments), "debug");
		},

		/**
		 * dir level
		 * 
		 * @param message
		 *            (string) Log content.
		 */
		dir : function(/* object */message /* ,... */) {
			// message:
			// Log content.
			if (this.level > this.levels.fine) {
				return;
			}

			this.log(this.dir.caller.nom, Array.prototype.slice.call(arguments), "dir");
		},

		/**
		 * info level
		 * 
		 * @param message
		 *            (string) Log content.
		 */
		info : function(/* object */message /* ,... */) {
			// message:
			// Log content.
			if (this.level > this.levels.info) {
				return;
			}

			this.log(this.info.caller.nom, Array.prototype.slice.call(arguments), "info");
		},

		/**
		 * warn level
		 * 
		 * @param message
		 *            (string) Log content.
		 */
		warn : function(/* object */message /* ,... */) {
			// message:
			// Log content.
			if (this.level > this.levels.warn) {
				return;
			}

			this.log(this.warn.caller.nom, Array.prototype.slice.call(arguments), "warn");
		},

		/**
		 * error level
		 * 
		 * @param message
		 *            (string) Log content.
		 */
		error : function(/* object */message /* ,... */) {
			// message:
			// Log content.
			if (this.level > this.levels.error) {
				return;
			}

			this.log(this.error.caller.nom, Array.prototype.slice.call(arguments), "error");
		},

		/**
		 * entry to function
		 * 
		 * @param message
		 *            (string) Log content.
		 */
		entry : function(/* object */message /* ,... */) {
			// message:
			// Log content.
			if (this.level > this.levels.fine) {
				return;
			}

			var nom = this.entry.caller.nom;
			if (this.level <= this.levels.finest) {
				this.log(nom, message, "dir", "entry");
			} else if (this.level <= this.levels.finer) {
				var args = Array.prototype.slice.call(arguments);
				args.unshift("entry");
				this.log(nom, args, "finer");
			} else {
				this.log(nom, "entry", "fine");
			}
		},

		/**
		 * exit of function
		 * 
		 * @param message
		 *            (string) Log content.
		 */
		exit : function(/* object */message /* ,... */) {
			// message:
			// Log content.
			if (this.level > this.levels.fine) {
				return;
			}

			var nom = this.exit.caller.nom;
			if (this.level <= this.levels.finest) {
				this.log(nom, message, "dir", "exit");
			} else if (this.level <= this.levels.finer) {
				var args = Array.prototype.slice.call(arguments);
				args.unshift("exit");
				this.log(nom, args, "finer");
			} else {
				this.log(nom, "exit", "fine");
			}
		},

		/**
		 * log message
		 * 
		 * @param nam
		 *            (string) Name of method.
		 * @param message
		 *            (string) Log content.
		 * @param level
		 *            (string) level of the message
		 * @param _type
		 *            type of the message
		 */
		log : function(/* string */nom, /* object */message, /* string */level, _type) {
			if (this.level > this.levels[level]) {
				return;
			}
			_type = _type || "";

			var msg = level === "dir" ? [ this.writer.formatMessage(this.formatMessageObj(nom, _type, level)), message ] : this.writer.formatMessage(this
					.formatMessageObj(nom, message, level));
			return this.writer[level](msg);
		},

		/**
		 * Format class name.
		 * 
		 * @param name
		 *            (string) classname
		 * @return fromated class name.
		 */
		formatClassName : function(/* string */name) {
			if (typeof name !== "string")
				return "";
			return name.substring(name.lastIndexOf(".") + 1);
		},

		/**
		 * Format message object.
		 * 
		 * @param nam
		 *            (string) Name of method.
		 * @param message
		 *            (string) Log content.
		 * @param level
		 *            (string) level of the message
		 */
		formatMessageObj : function(/* string */nom, /* object */message, /* string */level) {
			if (!nom) {
				nom = "UnknownMethod";
			}
			var msg = "";
			if (_.isArray(message)) {
				for ( var i = 0, l = message.length; i < l; i++) {
					var m = message.shift();
					if (_.isArray(m)) {
						msg += m.toString() + " ";
					} else {
						msg += m + " ";
					}
				}
			} else {
				msg = message || "@";
			}
			return {
				"clientId" : this.client.id,
				"className" : this.nocf,
				"methodName" : nom,
				"level" : level,
				"message" : msg
			};
		},

		/**
		 * Format object.
		 * 
		 * @param object
		 *            object to be formatted.
		 * @param recur
		 *            level of recurtion.
		 * @return the string format of the object.
		 */
		formatObject : function(object, recur) {
			// summary:
			// Traces object. Only partially implemented.

			// If it is IE and the object is a Document Object,
			// return.
			// Since,
			// Document not support in for..in..
			// if (dojo.isIE && object && object.xml) {
			// return "IE XML Document, can't be formated!";
			// }

			if (recur == null) {
				recur = 1;
			} else if (recur > 1) {
				// only allow call recursively two times.
				return object + "";
			}

			if (typeof object !== "object") {
				return object + '';
			} else if (object == null) {
				return object + '';
			}
			var html = [];
			var pairs = [];
			for ( var name in object) {
				try {
					pairs.push([ name, object[name] ]);
				} catch (e) {
					/* squelch */
				}
			}

			pairs.sort(function(a, b) {
				return a[0] < b[0] ? -1 : 1;
			});

			for ( var i = 0; i < pairs.length; ++i) {
				if (i == 0) {
					html.push('[');
				}
				var name = pairs[i][0], value = pairs[i][1];
				var str;
				if (i == pairs.length - 1) {
					str = name + " : " + this._valueToString(value, recur) + ']';
				} else {
					str = name + " : " + this._valueToString(value, recur) + ", ";
				}
				html.push(str);
			}
			// html.push(']');
			return html.join(this.writer.getLineSeparator());
		},

		/**
		 * value to string.
		 * 
		 * @value
		 * @recur level of recurtion. return string form of the value.
		 */
		_valueToString : function(value, recur) {
			if (typeof value == "function") {
				return "function(){...};";
			}
			if (typeof value === "object") {
				return this.formatObject(value, recur + 1);
			} else {
				return value + "";
			}
		}
	};

	Logger.init = function(url) {
		$.getJSON(url, function(data) {			
			app.logConfig = data;

			Logger.prototype.defaultLevel = app.logConfig.defaultLevel || Logger.prototype.defaultLevel;
			Logger.prototype.logLevel = app.logConfig.logLevel || Logger.prototype.logLevel;
			Logger.prototype.defaultWriterClassName = app.logConfig.defaultWriter || Logger.prototype.defaultWriterClassName;

			Logger.prototype.defaultWriter = consoleLogWriter;
		});
	};

	Logger.getLogger = function(/* object */client) {
		if (client.logger) {
			return;
		}
		return (client.logger = new Logger(client));
	};
	
	var app = window.app = window.app || {};
	app.logConfig = eval("(" + logConfig + ")");
	
	Logger.prototype.defaultLevel = app.logConfig.defaultLevel || Logger.prototype.defaultLevel;
	Logger.prototype.logLevel = app.logConfig.logLevel || Logger.prototype.logLevel;
	Logger.prototype.defaultWriterClassName = app.logConfig.defaultWriter || Logger.prototype.defaultWriterClassName;
	Logger.prototype.defaultWriter = consoleLogWriter;
	
	return Logger;
});
