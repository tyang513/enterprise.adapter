define([ "text!./templates/stmtCrawlBlockKeyWordsList.html", "app/base/BaseContentList", "app/sentiment/sentimentconfig/searchbar/stmtCrawlBlockKeyWordsSearchBar", "text!app/sentiment/sentimentconfig/config/stmtCrawlBlockKeyWordsService.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.stmtCrawlBlockKeyWordsList", BaseContentList, {
		// default options
		options : {
			entity : 'stmtCrawlBlockKeyWords',
			entityName : '爬虫屏蔽关键词',
			entityId : 'id',
			
			openType : 'dialog',
			dialogConfig : {
				width : 435,
				height : 180,
				className : 'app/sentiment/sentimentconfig/dialog/stmtCrawlBlockKeyWordsDialog'
			},
			formConfig : {
				url : 'app/sentiment/sentimentconfig/stmtCrawlBlockKeyWordsForm'
			}
		},

		templateString : template
	});
});
