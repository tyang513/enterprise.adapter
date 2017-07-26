define([ "./Logger", "jqueryui/jquery.ui.widget", "./extension" ], function() {
	return $.widget("app.dialogExt", {

		// default options
		options : {
			autoClose : true
		},

		// the constructor
		_create : function() {
			this._super();

			this._handled = false;
			this.options.onBeforeClose = _.bind(function() {
				var eventId = "cancel";
				var handler = this._cancelButton ? this._cancelButton.handler : undefined;

				if (handler && _.isFunction(handler)) {
					if (this.idlg && this.idlg.execute && _.isFunction(this.idlg.execute)) {
						this.idlg.execute(eventId, handler);
					}
					// return false;
				} else {
					// return false;
				}

			}, this);

			this.options.onClose = _.bind(function() {
				this.destroy();
			}, this);

			_.each(this.options.buttons, function(button) {
				var eventId = button.eventId;
				var handler = button.handler;
				button.handler = _.bind(function() {
					if (this.idlg && this.idlg.execute && _.isFunction(this.idlg.execute)) {
						this.idlg.execute(button.eventId, handler);
						if (this.options.autoClose) {
							this.dlg.dialog("close", true);
						}
					}
				}, this);

				if (eventId === "cancel") {
					this._cancelButton = button;
				}
			}, this);

			this.dlg = $("<div>").dialog(this.options);

			var fullName = this.options.className;
			var aa = fullName.split("/");
			var shortName = aa[aa.length - 1];
			require([ fullName ], _.bind(function() {
				this.dlg.dialog("open");
				this.innderDlg = this.dlg.find(".panel-body")[shortName](this.options.data);
				this.innderDlg.addClass("app-" + shortName);
				this.idlg = this.innderDlg.data("app-" + shortName);
			}, this));
		},

		_destroy : function() {
			if (this.idlg) {
				delete this.idlg;
			}

			if (this.innderDlg) {
				delete this.innderDlg;
			}

			if (this.dlg) {
				this.dlg.dialog('destroy');
				delete this.dlg;
			}

			delete this._cancelButton;
			delete this._handled;

			this._super();
		}
	});
});
