define([ "text!./templates/openCalendarDialog.html", "zf/base/BaseWidget" ],
		function(template, BaseWidget) {
			return $.widget("app.openCalendarDialog", BaseWidget, {
				// default options
				options : {},

				templateString : template,

				_create : function() {
					this._super();
					this._initdatas();
				},
				
				_updateCalendar : function(callback) {
					var serverName = "";
					this.updateCalendarForm.ajaxForm(
							app.buildServiceData("editCalendar", {
							beforeSubmit : _.bind(function() {
								return this.updateCalendarForm.form('validate');
							}, this),
							context : this,
							success : function(data){
								if(data && data.sucMessage){
									app.messager.info(data.sucMessage);
								}else{
									app.messager.warn(data.failMessage);
								}
								callback(data);
							},
							error : function(data){
								callback && callback();
							}
						}));
					this.updateCalendarForm.submit();
				},
				_initdatas : function() {
					if(this.options.mode =="edit"){
						this.id.val(this.options.data.id);	
						this.date.datebox('setValue',app.formatter.formatTime(this.options.data.date));
						this.type.combobox('setValue',this.options.data.type);
					}
					
				},
				_confirmInput:function(){
					return true;
				},
				execute : function(eventId, callback) {
					if (eventId === "ok") {
						if(this._confirmInput()){
							this._updateCalendar(callback);	
						}					
					} else if (eventId === "cancel") {
						if (callback) {
							callback();
						}
					}
				}

			});
		});
