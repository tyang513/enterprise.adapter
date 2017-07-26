define([ "text!./templates/test.html", "zf/base/BaseWidget", "app/common/test/module/testA", "app/common/test/module/testECharts","app/common/test/searchbar/searchBarTest",
		"app/base/BaseForm", "app/common/test/module/salesHierarchy"], function(template, BaseWidget) {
	return $.widget("app.test", BaseWidget, {

		options : {},

		templateString : template,

		_create : function() {
			this._super();
			this._initLayout();

			this._initData();
		},
		_initLayout : function() {
			this._initGridLayout();
		},

		_initData : function() {
//			this.fancyboxDiv.fancybox();
		},

		/* ******************* 引用模板对象 *************** */

		_changeTitle : function() {
			this.title.html("title 值被该改变了");
		},
		
		_showImage : function() {
			$.fancybox.open('http://www.sinaimg.cn/dy/slidenews/1_img/2014_50/2841_521452_452901.jpg');
		},

		/* ******************* button事件绑定 *************** */

		_buttonClick : function(event) {
			var options = app.utils.parseOptions($(event.currentTarget));
			app.messager.alert("提示", options.param);
		},

		/* ******************* 事件传递参数 *************** */

		_createHtml : function() {
			for ( var i = 1; i < 4; i++) {
				var html = "<a href='javascript:void(0);' class='easyui-linkbutton' data-options='plain:true,index:" + i + "' "
						+ "data-attach-event='click:_clickIndexButton'>按钮" + i + "</a>";
				this.bttonNode.append($(html));
			}
			this._parse(this.bttonNode);

		},
		_clickIndexButton : function(event) {
			var options = app.utils.parseOptions($(event.currentTarget));
			app.messager.alert("提示", "你点击了第" + options.index + "个按钮");
		},
		_deleteButton : function() {
			this.bttonNode.html("");
		},

		/* ******************* 两个类之间的引用 *************** */

		_callTestAMethod : function() {
			var msg = this.testA.testA("getMessage");
			app.messager.alert("来自testA消息", msg, "info");
		},

		/* ******************* datagrid 使用 *************** */

		_initGridLayout : function() {
			this.testDataGrid.datagrid({
				fit : true,
				fitColumns : true,
				singleSelect : true,
				striped : true,
				columns : [ [
						{
							field : 'code',
							title : '身份證',
							width : 100
						},
						{
							field : 'name',
							title : '姓名',
							width : 100
						},
						{
							field : 'sex',
							title : '性别',
							width : 100,
							formatter : function(value, row, index) {
								if (value == "1") {
									return "男";
								} else {
									return "女";
								}
							}
						},
						{
							field : 'address',
							title : '地址',
							width : 100
						},
						{
							field : 'operate',
							title : '操作',
							align : "center",
							width : 100,
							formatter : _.bind(function(value, row, index) {
								// var html = "<a href='#'
								// class='easyui-linkbutton'
								// data-options=\"plain:true,rowIndex:"+index+"\"
								// onclick=_deleteRowData();>删除</a>";
								// return app.utils.bind(html, this);

								var html = "<img src='images/icon/quankachuku.jpg' style='cursor:pointer' data-options=\"plain:true,rowIndex:" + index
										+ "\" onClick=_deleteRowData();>"
								return app.utils.bind(html, this);
							}, this)
						} ] ]
			})
		},
		_initGridData : function() {
			var data = [ {
				code : "213245",
				name : "张三",
				sex : "0",
				address : "上海"
			}, {
				code : "3343",
				name : "李四",
				sex : "1",
				address : "上海"
			}, {
				code : "5343",
				name : "王五",
				sex : "0",
				address : "上海"
			} ]

			this.testDataGrid.datagrid("loadData", data);
		},
		_deleteRowData : function(event) {
			// var options = app.utils.parseOptions($(event)); //注意
			// this.testDataGrid.datagrid("deleteRow",options.index);
//			setTimeout(_.bind(function() {
//				var selectRow = this.testDataGrid.datagrid("getSelected");
//				var index = this.testDataGrid.datagrid("getRowIndex", selectRow);
//				this.testDataGrid.datagrid("deleteRow", index);
//			}, this), 50);
			
			var selectRow = this.testDataGrid.datagrid("getSelected");
			if(this.tip){
				this.tip.tooltip("destroy")
				this.tip = null;
			}
			var _self = this;
			this.tip = $("<div></div>").tooltip({
				position: 'right',
				content: '<div class="app-testA" data-attach-point="testA" >ddd</div>',
				onShow: function(){
					_self._parse($(".tooltip-content"),{
						templateString : '<div class="app-testA" data-attach-point="testA" data-attach-event="testaclose:_onClose">ddd</div>'
					});
					$(this).tooltip('tip').css({
						top : $(event).offset().top,
						left : $(event).offset().left
//						backgroundColor: '#666',
//						borderColor: '#666'
					});
				}
			});
			this.tip.tooltip("show");
		},
		_onClose : function(){
			if(this.tip){
				this.tip.tooltip("destroy")
				this.tip = null;
			}
		},
		_doAction : function(event, data) {
			var handlerName = "_handle" + _.string.capitalize(data.action);
			if (this[handlerName] && _.isFunction(this[handlerName])) {
				this[handlerName](data);
			}
		},
		_handleView : function(data) {
			app.messager.alert('信息', '姓名：' + data.row.name, 'info');
		},
		_handleSearch : function(data) {
			this._initGridData();
		},

		/* ******************* dialog 使用 *************** */

		_showDialogTest : function(event) {
			var dlg = $("<div>").dialogExt({
				title : "dialog测试",
				autoClose : false,
				buttons : [ {
					text : '确定',
					eventId : "ok",
					handler : _.bind(function(data) {
						dlg.dialogExt("destroy");
						app.messager.alert('信息', 'dialog回传的参数为：' + data, 'info');
					}, this)
				}, {
					text : '取消',
					eventId : "cancel",
					handler : function() {
						dlg.dialogExt("destroy");
					}
				} ],
				width : 400,
				height : 200,
				closed : false,
				cache : false,
				// className : 'app/common/dialog/attachmentDialog',
				className : 'app/common/test/dialog/dialogTest',
				data : {
					sheetId : "CSxxxxx0001",
					sheetType : "CS"
				},
				modal : true
			});
		},

		/* ******************* formModule 使用 *************** */

		_showFormModuleTest : function() {
			this._publish("app/openTab", {
				"title" : "test 名称",
				"url" : "app/common/test/testRequestForm",
				"taskData" : ""
			});
		},

		/* ******************* field组件 使用 *************** */

		_onInitSaleType : function() {
			this.logger.debug();

			app.getDictionary('orderTypeDIL', _.bind(function(data) {
				this.saleType.combobox('loadData', data);
			}, this));

			this.partner.partner('option', 'value', 6);
			this.partner.partner('option', 'readonly', true);
		},

		_onSelect : function(event, data) {
			console.dir([ event, data ]);
		},

		/* ******************* div删除按钮 使用 *************** */

		_onCreateDiv : function() {
			this.logger.debug();
			var template = "<div style=\"margin-top:20px;\">"
					+ "<a class=\"closeable\" href=\"javascript:void(0)\" data-attach-event=\"click:_onDeleteDiv\">删除</a>" + "<div>Div ${index}</div></div>";
			console.dir([ template ]);

			var count = 5;
			var contentHtml = [];
			for ( var i = 0; i < count; i++) {
				contentHtml.push(app.utils.parseTemplate(template, {
					index : i
				}));
			}

			$(contentHtml.join('')).appendTo(this.contentDiv);
			this._parse(this.contentDiv);
		},

		_onDeleteDiv : function(event) {
			this.logger.debug();
			var toDeleteDiv = $(event.currentTarget).parent();
			toDeleteDiv.remove();
		},

		/* ******************* 文档下载 使用 *************** */

		_onDownload : function() {
			this.logger.debug();

			var ajaxData = app.buildServiceData("downloadFormExcel", {
				data : {
					sheetid : "SO20131018006",
					sheettype : "SO"
				}
			});
			app.utils.download(ajaxData);
		},

		/* ******************* 附件上传 使用 *************** */

		_openFileUploadDialog : function() {
			// 打开对话框
			var dlg = $("<div>").dialogExt({
				title : "批量上传附件",
				autoClose : false,
				buttons : [],
				width : 500,
				height : 360,
				closed : false,
				cache : false,
				className : 'app/common/uploader',
				data : {
					autoUpload : false,
					formData : {
						sheetid : "SO20131113001",
						sheettype : "SO",
						type : 1
					}
				},
				modal : true
			});
		},

		_onInitUpload : function() {
			this.logger.debug();
			this._openFileUploadDialog();
		},
		_initComboTree : function(){
//			this.categoryName.combotree({
//				url : 'category/initCategoryTree.do',
//				multiple:true,
//				cascadeCheck:true,
//				animate:true,
//				lines:true,
//				onLoadSuccess : function(){
//					console.log("dddd");
//				}
//			});
		},
		_combotreeTest : function(){
			
		},
		_onSetCC : function(){
			this.logger.debug();
			this.datebox.datebox('calendar').calendar({
				minDate : '2014-4-18'
			});
		},
		_addTooltip : function(){
			var _html = '<input data-attach-point="testTip" type="text"></input> '+
						'<a href="javascript:void(0)" data-attach-point="testTip" class="easyui-linkbutton" data-options="plain:true" data-attach-event="click:_query">确定</a>';
			var _self = this;
			this.tip = this.tipBtn.tooltip({
				position: 'right',
				content: _html,
				onShow: function(){
					var t = $(this);        
					_self._parse(t.tooltip('tip'));
					t.tooltip('tip').unbind().bind('mouseenter', function(){           
						t.tooltip('show');                    
					}).bind('mouseleave', function(){             
						t.tooltip('destroy');           
					});
				}
			});
			this.tip.tooltip("show");
		},
		_query : function(){
			console.dir(["this.testTip", this.testTip]);
			this.tip.tooltip('destroy');  
		},
		
		
		// tabForm
		_onOpenTabForm : function() {
			this.logger.debug();
			
			var finSalesCancelSheet = {
				id : 'CS20131125002',
				cancelType : 'CS'
			};
			this._publish("app/openTab", {
				"title" : "作废单--" + finSalesCancelSheet.id,
				"url" : "app/common/test/tabForm",
				"mode" : mode,
				"finSalesCancelSheet" : finSalesCancelSheet
			});
		},
		
		_destroy : function() {
			this._super();
		}

	});
});
