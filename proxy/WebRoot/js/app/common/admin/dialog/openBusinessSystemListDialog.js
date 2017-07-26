define([ "text!./templates/openBusinessSystemListDialog.html", "zf/base/BaseWidget" ],
		function(template, BaseWidget) {
			return $.widget("app.openBusinessSystemListDialog", BaseWidget, {
				// default options
				options : {},

				templateString : template,

				_create : function() {
					this._super();
					this._initdatas();
				},
				
				_updateBusiness : function(callback) {
					var serverName = "";
					this.updateBusinessForm.ajaxForm(
							app.buildServiceData("updateBusinessSystem", {
							beforeSubmit : _.bind(function() {
								return this.updateBusinessForm.form('validate');
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
					this.updateBusinessForm.submit();
				},
				_initdatas : function() {
					if(this.options.mode =="edit"){
						this.id.val(this.options.data.row.id);	
						this.name.val(this.options.data.row.name);
						this.code.val(this.options.data.row.code);
						this.description.val(this.options.data.row.description);
						this.accessurl.val(this.options.data.row.accessurl);
						this.serverurl.val(this.options.data.row.serverurl);
					}
					
				},
				_confirmInput:function(){
					return true;
				},
				execute : function(eventId, callback) {
					if (eventId === "ok") {
						if(this._confirmInput()){
							this._updateBusiness(callback);	
						}					
					} else if (eventId === "cancel") {
						if (callback) {
							callback();
						}
					}
				}

			});
		});
