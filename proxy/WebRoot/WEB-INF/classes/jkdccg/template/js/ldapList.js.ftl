define([ "text!./templates/${listWidgetName}.html", "app/base/BaseContentList", "app/${bizAppPackage}/${entityPackage}/searchbar/${searchbarWidgetName}", "text!app/${bizAppPackage}/${entityPackage}/config/${serviceConfigName}.json" ], function(template, BaseContentList, searchBar, serviceConf) {
	app.serviceLocator.mergeConfig(serviceConf);
	return $.widget("app.${listWidgetName}", BaseContentList, {
		// default options
		options : {
			entity : '${lowerName}',
			entityName : '${codeName}',
			entityId : '${primaryKey}',
			
			openType : '${entityOpenType}',
			dialogConfig : {
				width : 500,
				height : 350,
				className : 'app/${bizAppPackage}/${entityPackage}/dialog/${dialogWidgetName}'
			},
			formConfig : {
				url : 'app/${bizAppPackage}/${entityPackage}/${formWidgetName}'
			}
		},

		templateString : template,
		
		_handleDelete : function(data) {
			this.logger.debug();
			app.messager.confirm(app.constants.CONFIRM_TITLE, app.constants.CONFIRM_DELETE + this.options.entityName + "?", _.bind(function(r) {
				if (r) {
					$.ajax(app.buildServiceData(this.options.entity + "/deleteById.do", {
						data : {
#foreach($po in $!{columnDatas})
#if (${po.ldapData.dnAttribute} == true)
							${po.dataName} : data.row.${po.dataName},
#end
#end							
						},
						context : this,
						success : function(data) {
							if (data) {
								var action = "删除操作执行";
								if (data.success) {
									app.messager.info(data.msg || action + "成功!");
								} else {
									app.messager.warn(data.msg || action + "失败!");
								}
							}
							this.refresh();
						}
					}));
				}
			}, this));
		}
		
	});
});
