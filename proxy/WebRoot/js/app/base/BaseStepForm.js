define([ "zf/base/BaseWidget" ], function(BaseWidget) {
	return $.widget("app.BaseStepForm", BaseWidget, {
		// default options
		options : {
			stepClass : ".stepPanel",
			defaultStep : 0,

			lazyLoad : false
		},

		templateString : '<div class="easyui-layout" data-options="fit:true" data-attach-point="layoutContainer"></div>',

		// the constructor
		_create : function() {
			this.options.autoParse = !this.options.lazyLoad;

			var templateString = '<div class="easyui-layout" data-options="fit:true">' + this.templateString + '</div>'
			this.templateString = templateString;
			this._super();

			this.stepPanels = this.element.find(this.options.stepClass);
			this.step = this.options.defaultStep;
		},

		_showStep : function(step) {
			this.stepPanels.hide();
			this.element.find(this.stepPanels[step]).show();
		},

		preStep : function() {
			this.step--;
			this._showStep(this.step);
		},

		nextStep : function() {
			this.step++;
			this._showStep(this.step);
		},

		firstStep : function() {
			this.step = 0;
			this._showStep(this.step);
		},

		lastStep : function() {
			this.step = this.stepPanels.length - 1;
			this._showStep(this.step);
		},

		_destroy : function() {
			delete this.stepPanels;
			this._super();
		}
	});
});
