define([ "text!./templates/componentA.html","app/base/BaseSheetSummary" ], function(template, BaseWidget) {
	return $.widget("app.componentA", BaseWidget, {
		
		options : {},
		
		templateString : template,
		
		_create : function() {
			this._super();
		},

		// ---------------- interface -----------------
		onFormAction : function(data) {
			console.dir(["component data", data]);
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
