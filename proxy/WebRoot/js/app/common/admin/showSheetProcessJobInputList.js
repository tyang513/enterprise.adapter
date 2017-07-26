define([ "text!./templates/showSheetProcessJobInputList.html", "zf/base/BaseWidget", "app/common/admin/searchbar/sheetProcessJobInputListSearchBar" ],
		function(template, BaseWidget) {
			return $.widget("app.showSheetProcessJobInputList", BaseWidget, {
				// default options
				options : {},
				templateString : template,
				// the constructor
				_create : function() {
					this._super();
					this.dicts = {};
					this._initData();
				},

				_initData : function() {

					var dicLoaded = function(data) {
						this.dicts = data;
						this.dataProcessJobInputGrid.datagrid(app.buildServiceData("querySheetProcessJobInput", {
							queryParams : {}, // 查询条件
							loadMsg : '数据加载中请稍后……'
						}));
					};
					// 缓存字典数据
					app.utils.getDicts('ApplicationType', _.bind(dicLoaded, this));
				},

				_formatterApplicationType : function(value, row, index) {
					return this._getLabel('ApplicationType', value);
				},

				_getLabel : function(dictName, value) {
					var label = null;
					var dict = this.dicts[dictName];
					if (dictName === 'ApplicationType') {
						for ( var i = 0; i < dict.length; i++) {
							if (dict[i].dicitemkey === value) {
								label = dict[i].dicitemvalue;
								break;
							}
						}
					}
					return label;
				},

				_formatterProcesstype : function(value, row, index) {
					if (value == 1) {
						return '后台注分附件处理';
					} else if (value == 2) {
						return '券卡信息处理';
					} else if (value == 3) {
						return '失败注分信息附件处理';
					} else if (value == 4) {
						return '重新上传后台注分附件处理';
					} else if (value == 5) {
						return '券卡作废处理';
					} else if (value == -1) {
						return '异常';
					}
				},

				_formatterStatus : function(value, row, index) {
					if (value == 0) {
						return '未处理';
					} else if (value == 1) {
						return '正在处理';
					} else if (value == 2) {
						return '处理完毕';
					} else {
						return '错误';
					}
				},

				_formatterOper : function(value, row, index) {
					if (row.status === -1 && row.retry === row.maxretry) {

						var html = "<a href='javascript:void(0);' class='easyui-linkbutton' data-options=\"plain:true,idValue:" + row.id
						+ "\" onClick=_reloadSheetProcessJob();>手动重试</a>";

						return app.utils.bind(html, this);
					} else {
						return '';
					}

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
				refresh : function() {
					this.logger.debug();
					this.dataProcessJobInputGrid.datagrid('reload');
				},

				_handleSearch : function(data) {
					this.logger.debug();
					var queryParams = this.searchBar.sheetProcessJobInputListSearchBar('value');
					this.dataProcessJobInputGrid.datagrid('options').queryParams = queryParams;
					this.dataProcessJobInputGrid.datagrid('load');
				},

				_handleRetry : function(data) {
					this.logger.debug();
					$.ajax(app.buildServiceData("reloadSheetProcessJobInput", {
						data : {
							id : data.row.id,
							status : data.row.status,
							maxretry : data.row.maxretry,
							retry : data.row.retry
						},
						type : 'POST',
						context : this,
						success : function(data) {
							if (data.success) {
								app.messager.info(data.success);
								this.refresh();
							} else
								app.messager.warn(data.error);
						}
					}));
				},

				_reloadSheetProcessJob : function(event) {
					this.logger.debug();
					var options = app.utils.parseOptions($(event));
					var idValue = options.idValue;

					this.dataProcessJobInputGrid.datagrid('selectRecord', idValue);
					var data = this.dataProcessJobInputGrid.datagrid("getSelected");
					
					$.ajax(app.buildServiceData("reloadSheetProcessJobInput", {
						data : {
							id : data.id,
							status : data.status,
							maxretry : data.maxretry,
							retry : data.retry
						},
						type : 'POST',
						context : this,
						success : function(data) {
							if (data.success) {
								app.messager.info(data.success);
								this.refresh();
							} else
								app.messager.warn(data.error);
						}
					}));
				}
			});
		});