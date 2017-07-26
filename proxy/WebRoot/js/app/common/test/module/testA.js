define([ "text!./templates/testA.html", "zf/base/BaseWidget" ], function(template, BaseWidget) {
	return $.widget("app.testA", BaseWidget, {
		
		options : {},
		
		templateString : template,
		
		_create : function() {
			this._super();
		},
		getMessage : function(){
			return "你已经成功调用了testA中的方法";
		},
		_close : function(event){
			this._trigger("close",event,{});
		},
		_destroy : function() {
			this._super();
		}
	});
});
