define([ "text!./templates/stmtCrawlKeyWordsList.html", "app/base/BaseContentList", "app/sentiment/sentimentconfig/searchbar/stmtCrawlKeyWordsSearchBar", "text!app/sentiment/sentimentconfig/config/stmtCrawlKeyWordsService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.stmtCrawlKeyWordsList", BaseContentList, {
		// default options
		options : {
			entity : 'stmtCrawlKeyWords',
			entityName : '爬虫关键词',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 435,
				height : 230,
				className : 'app/sentiment/sentimentconfig/dialog/stmtCrawlKeyWordsDialog'
			},
			formConfig : {
				url : 'app/sentiment/sentimentconfig/stmtCrawlKeyWordsForm'
			}
		},

		templateString : template,
		_handleDisable : function(data){			
			this.logger.debug();
			var status;
			if(data.row.status==app.constants.SENTIMENT_ENABLE ){
				status = app.constants.SENTIMENT_DISABLE;
				app.messager.confirm("提示","是否将爬虫关键词的状态改为禁用？",_.bind(function(r){
					if(r){
						this._enableOrDisableStmtCrawlKeyWords(status,data);
					}},this));
				
			}else if(data.row.status==app.constants.SENTIMENT_DISABLE){
				app.messager.warn("该爬虫关键词已禁用");
			}else if(data.row.status == app.constants.SENTIMENT_UNENABLE){
				app.messager.warn("该爬虫关键词的状态为未启用，不能改为禁用");
			}
		},
        _handleEnable : function(data){
        	this.logger.debug();
        	var status;
			if(	data.row.status==app.constants.SENTIMENT_UNENABLE || data.row.status==app.constants.SENTIMENT_DISABLE ){
				status =app.constants.SENTIMENT_ENABLE;
				this._enableOrDisableStmtCrawlKeyWords(status,data);
			}else{
				app.messager.warn("该爬虫关键词已启用");
			}
		},
		_enableOrDisableStmtCrawlKeyWords :function(status,data){
			$.ajax(app.buildServiceData("stmtCrawlKeyWords/enableOrDisableStmtCrawlKeyWords.do", {
				data : {
					id: data.row.id,
					status: status
				},
				context : this,
				global : true,
				success : function(data) {
					this._initData();
				}
			}));
		}
	});
});
