<div class="datagrid-toolbar-tool"></div>
<div class="datagrid-toolbar-filter" style="display: none;">
	<div class="criteria-panel">
		<form data-attach-point="searchForm">
#foreach($po in $!{columnDatas})
#if ($po.dataName != $primaryKey && $po.viewData && $po.viewData.filter == true && ${po.ldapData.memberOf} == false)
			<div class="fitem">
				<label>${po.viewData.title}</label>
				<input name="${po.dataName}" type="text" maxlength="${po.charmaxLength}" class="${po.classType}">
			</div>
#end
#end
      </form>		
	</div>
</div>