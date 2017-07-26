define([ "text!./templates/testRequestForm.html", "zf/base/BaseWidget", "app/base/BaseForm"], function(template, BaseWidget) {
	return $.widget("app.testRequestForm", BaseWidget, {
		
		options : {},
		
		templateString : template,
		
		_create : function() {
			this._super();
			this.render();
		},
		
		render : function(formData) {
			formData = {
				
			};
			this.baseForm.BaseForm({
				viewType : {
					taskcode : "test",
					processcode : "test"
				}
			});
			this.baseForm.BaseForm("render", formData);
		},
		
		_onFormAction : function(event, data){
			console.dir(["requestForm data", data]);
		},
		_destroy : function() {
			this._super();
		}
		
	});
});
