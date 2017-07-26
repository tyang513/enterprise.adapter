define([], function() {
	var messager = {

		confirm : $.messager.confirm,
		alert : $.messager.alert,
		prompt:$.messager.prompt,
		info : function(msg, detail) {
			this._show(msg, detail, 'info');
		},
		warn : function(msg, detail) {
			this._show(msg, detail, 'warn');
		},
		error : function(msg, detail) {
			this._show(msg, detail, 'error');
		},
		_show : function(msg, detail, type) {
			var _detail = detail;
			
			if (!this._msgDiv) {
				this._msgDiv = $('<div class="tip-msg"><span></span> <a href="javascript:void(0)"> 详细>>> </a></div>');
				this._msgDiv.appendTo($('body'));
			}
			
			var aDom = this._msgDiv.find('a');
			aDom.off("click");
			
			var alertDetail = function() {
				$.messager.alert(_detail.title,_detail.context);
			};
			
			
			this._msgDiv.find('a').on("click", alertDetail);	
			this._msgDiv.find('span').html(msg);			
			if(detail === undefined){
				aDom.hide();
			} else {
				aDom.show();
			}			

			var windowWidth = document.documentElement.clientWidth;
			var popupWidth = this._msgDiv.width();

			var color = '#008000';
			if (type === 'info') {
				color = '#008000';
			} else if (type === 'error') {
				color = '#CC0000';
			} else if (type === 'warn') {
				color = '#CC6633';
			}
			// 居中设置
			this._msgDiv.css({
				"background-color" : color,
				"opacity" : 1,
				"left" : windowWidth / 2 - popupWidth / 2
			});
			this._msgDiv.fadeIn("slow");

			var _hideMsg = _.bind(function() {
				this._msgDiv.fadeOut("slow");
			}, this);
			setTimeout(_hideMsg, 8000);
		}
	};
	return messager;
});
