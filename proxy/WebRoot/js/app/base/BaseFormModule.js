define([ "zf/base/BaseWidget" ], function(BaseWidget) {
	return $.widget("app.BaseFormModule", BaseWidget, {
		// default options
		options : {
			readOnly : false,
			notNull : false,
			showMode : 'show', // auto, show, hide
			formData : null,
			formId : null,
			formType : undefined,

			// callbacks
			action : null
		},

		templateString : '',

		_create : function() {
			this._super();

			this.formType = null;
			this._detectionBrowseVersion();
		},
		// 解决IE6-IE8 下不支持css3 奇偶选择器问题
		_detectionBrowseVersion : function(){
			if(!$.support.leadingWhitespace){   // 检测是否是IE6--IE8
				var tableEventTd = this.element.find(".form-module .formtable>tbody>tr>td:nth-child(even)");
				var tableOddTd = this.element.find(".form-module .formtable>tbody>tr>td:nth-child(odd)");
				var tableEventTd3 = this.element.find(".form-module .formtable3>tbody>tr>td:nth-child(even)");
				var tableOddTd3 = this.element.find(".form-module .formtable3>tbody>tr>td:nth-child(odd)");
				if(tableEventTd.length > 0){
					$(tableEventTd).addClass("tableEvenTd");
				}
				if(tableOddTd.length > 0){
					$(tableOddTd).addClass("tableOddTd");
				}
				if(tableEventTd3.length > 0){
					$(tableEventTd3).addClass("tableEventTd3");
				}
				if(tableOddTd3.length > 0){
					$(tableOddTd3).addClass("tableOddTd3");
				}
			}
		},
		_showModule : function(module, show) {
			if (_.isBoolean(module)) {
				show = module;
				module = this;
			}
			var data = {
				actionDijit : "form",
				eventId : "showModule",
				show : show,
				module : module
			};
			this._trigger("action", null, data);
		},

		_dataChange : function(formData) {
			var data = {
				actionDijit : "form",
				eventId : "dataChange",
				formData : formData,
				module : this
			};
			this._trigger("action", null, data);
		},

		_getModuleData : function(module, callback) {
			var data = {
				actionDijit : "form",
				eventId : "getModuleData",
				module : module,
				callback : callback
			};
			this._trigger("action", null, data);
		},

		onFormAction : function(data) {
			var eventId = data.eventId;
			if (eventId === "dataChange") {
				this.formData = data.formData;
			} else if(eventId === "onSelect"){
				console.dir(["Tab Selected", this]);
				this.formData = data.formData;
				this.formData.isSeleced = true;
				this.render(this.formData);
			}
		},

		_checkFormData : function() {
			if (this.formData.formType) {
				this.formType = this.formData.formType;
				if (this.formData.formType === app.constants.SHEET_SUBJECTUM) {
					this.subjectum = this.formData.subjectum;
				}else if (this.formData.formType === app.constants.SHEET_RECON_BATCH) {
					this.reconBatch = this.formData.reconBatch;
				}else if (this.formData.formType === app.constants.SHEET_SETTLE_TEMPLATE) {
					this.settleTemplate = this.formData.settleTemplate;
				} else if (this.formData.formType === app.constants.RECON_CONFIG){
					this.reconConfig= this.formData.reconConfig;
				}else if (this.formData.formType === app.constants.MERCHANT_RECONFILECONFIG){
					this.reconConfig= this.formData.merchantReconFileConfig;
				}else if(this.formData.formType === app.constants.SHEET_SETTLE_BATCH){
					this.settleBatch = this.formData.settleBatch;
				}else if (this.formData.formType === app.constants.SHEET_SETTLE_SHEET) {
					this.settleSheet = this.formData.settleSheet;
				} 
			} else {
				this.logger.warn("using self check formType!!");
			}
		},

		// 支持外部数据
		render : function(formData) {
			this.formData = formData || this.formData;
			this._checkFormData();
		},

		validate : function() {
			return true;
		},

		value : function() {
			return null;
		},

		data : function() {
			return null;
		},
		
		setSeletedTab : function(module){
			var formModule = module.data(module.attr("class"));
			this._showModule(formModule, true);
		}
	});
});
