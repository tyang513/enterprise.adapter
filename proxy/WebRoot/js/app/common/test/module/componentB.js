define([ "text!./templates/componentB.html", "app/base/BaseFormModule" ], function(template, BaseWidget) {
	return $.widget("app.componentB", BaseWidget, {
		
		options : {},
		
		templateString : template,
		
		_create : function() {
			this._super();
		},

		// ---------------- interface -----------------
		onFormAction : function(data) {
			var eventId = data.eventId;
			if (eventId === "test") {
				
			}
		},
		validate : function() {
			
			return true;
		},

		value : function() {
			var obj = {};
			return obj;
		},

		render : function(formData) {
			this._super(formData);

		},
		_destroy : function() {
			this._super();
		}
	});
});
