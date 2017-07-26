define([ "app/base/BaseContentList" ], function(BaseContentList) {
	return $.widget("app.BaseContentListForHBase", BaseContentList, {
		// default options
		options : {},

		// ---------------functions------------------
		_initData : function() {
			this.logger.debug();
			this._pageSizeChanging = false;
			this.dataGrid.datagrid(app.buildServiceData(this.options.entity + "/list.do", {
				queryParams : this.searchBar[this.options.entity + "SearchBar"]('value'), // 查询条件
				onClickRow : _.bind(this._onClickRow, this),
				onBeforeLoad : _.bind(this._onBeforeLoad, this),
				onLoadSuccess : _.bind(this._onLoadSuccess, this),
				loadMsg : this.options.loadMsg
			}));
			app.utils.bindDatagrid(this, this.dataGrid);

			// 处理pager
			var pager = this.dataGrid.datagrid('getPager');
			pager.pagination({
				navigator : true,
				// pageList : [ 2, 5, 50, 100 ],
				layout : [ 'list', 'sep', 'first', 'prev', 'next' ],
				displayMsg : "",
				onBeforeNavigate : _.bind(this._onBeforeNavigate, this),
				onChangePageSize : _.bind(this._onChangePageSize, this)
			});
		},

		_onBeforeLoad : function(param) {
			this.logger.debug();
			if (this._pageSizeChanging) {
				this._pageSizeChanging = false;
				return true;
			}
			param.navigator = this._navigator;
			if (param.navigator === 'next') {
				param.startRowKey = this._nextLink;
			} else if (param.navigator === 'prev') {
				var rows = this.dataGrid.datagrid('getData').rows;
				param.endRowKey = rows[0].rowkey;
			}
			return true;
		},

		_onBeforeNavigate : function(pageNumber, pageSize, navigator) {
			this.logger.debug();
			this._navigator = navigator;
		},

		_onChangePageSize : function() {
			this.logger.debug();
			this._pageSizeChanging = true;
		},

		_onLoadSuccess : function(data) {
			this.logger.debug();
			console.dir([ "data", data ]);
//			var pager = this.dataGrid.datagrid('getPager');
//			var next = pager.find("span.pagination-next").parent().parent().parent();
//			next.linkbutton({
//				disabled : (data.next === undefined)
//			});

			this._nextLink = data.next;
		},
		
		_handleSearch : function(data) {
			this._navigator = null;
			this._super(data);
		}
	});
});
