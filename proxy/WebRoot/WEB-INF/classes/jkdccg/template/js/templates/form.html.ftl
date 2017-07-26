<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form data-attach-point="editForm" class="ui-form" method="post">  
			<input type="hidden" name="${primaryKey}">
			<div class="ui-edit">
#foreach($po in $!{columnDatas})
#if ($po.dataName != $primaryKey && $po.viewData && $po.viewData.form == true)  	   
				<div class="fitem">
					<label>${po.viewData.title}：</label>
					<input name="${po.dataName}" type="text"#if($po.viewData.dictionary) data-attach-point="${po.dataName}Combobox"#end maxlength="${po.charmaxLength}" class="${po.classType}" data-options="${po.optionType}" missingMessage="请填写${po.viewData.title}">
					#if($po.nullable == 'N')<span class="required">*</span>#end
				</div>
#end
#end
			</div>
		</form>
	</div>
	<div data-options="region:'south',border:false" style="height:40px;">
		<a data-attach-event="click:_onSave" class="easyui-linkbutton">保存</a>
		<a data-attach-event="click:_onCancel" class="easyui-linkbutton">取消</a>
	</div>
</div>
