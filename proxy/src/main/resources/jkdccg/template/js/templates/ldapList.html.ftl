<table id="${uuid}_grid" class="listgrid" 
	data-options="border:false,striped:true,singleSelect:true,fit:true,fitColumns:true,pagination:true,toolbar:'#${uuid}_bar'"
	data-attach-point="dataGrid">
	<thead>
		<tr>
#foreach($po in $!{columnDatas})
#if ($po.viewData && $po.viewData.searchResult == true && ${po.ldapData.memberOf} == false)
			<th data-options="field:'$!po.dataName',width:60,align:'center'#if($po.dataName == $primaryKey),hidden:true#end#if($po.viewData.formatter),formatter:app.utils.$po.viewData.formatter#end" >$po.viewData.title</th>
#end
#end
		</tr>
	</thead>
</table>

<div id="${uuid}_bar" class="app-${searchbarWidgetName}"
	data-options="grid:'#${uuid}_grid'" data-attach-point="searchBar"
	data-attach-event="${searchbarAction}:_doAction"></div>
