define([ "text!./templates/cacheList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/cacheSearchBar" ], function(template, BaseWidget) {
	return $.widget("app.cacheList", BaseWidget, {
		// default options
		options : {},
		templateString : template,
		// the constructor
		_create : function() {
			this._super();
			this._initData();
		},

		_initData : function() {
			this.dataGrid.datagrid(app.buildServiceData("querycache", {
				queryParams : {
				}, // 查询条件
				loadMsg : '数据加载中请稍后……',
				onLoadSuccess:function(data){
				}
			}));
		},
		
		// ---------- event handler -------------
		_doAction : function(event, data) {
			this.logger.debug();
			console.dir([ event, data ]);
			var handlerName = "_handle" + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},
		_handleRefresh: function(data) {
			this.logger.debug();
			var rowdata = data.row;
			this._refresh(rowdata);
		},
		refresh : function() {
			this.logger.debug();
			this.dataGrid.datagrid('reload');
		},
		_refresh : function(rowdata){
				$.ajax({
					type : 'POST',
					url : 'cache/removeCache.do',
					dataType : 'json',
					data : {
						cachename : rowdata.cachename
					},
					context : this
				}).done(function(data) {
					if (data.message === "OK") {
						app.messager.info("缓存刷新成功！");
						return true;
					}else{
						app.messager.info(data.message);
						return false;
					}
				}).fail(function(error) {
					app.messager.error('缓存刷新发生异常.');
					return false;
				});
		}
		
	});
});
