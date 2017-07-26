package com.td.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.td.admin.entity.SysDic;
import com.td.admin.entity.SysDicItem;
import com.td.admin.mapper.SysDicItemMapper;
import com.td.admin.mapper.SysDicMapper;
import com.td.common.util.UIUtil;

@Service
public class DictService {
	
	@Autowired
	private SysDicMapper sysDicMapper;

	@Autowired
	private SysDicItemMapper sysDicItemMapper;

    /**
     * 获取所有的数据字典
     * @return
     */
	public String getAllDicts(SysDic dict,Long rows,Long page, List<String> dicNameList){
		Map<String,Object> params = new HashMap<String, Object>(); 
		params.put("id", dict.getId());
		params.put("name", dict.getName());
		params.put("description", dict.getDescription());
		params.put("systemcode", dict.getSystemcode());
		Long total = sysDicMapper.selectTotal(params);
		params.put("beginIndex", (page-1)*rows);
		params.put("rows", rows);
		if(dicNameList != null && dicNameList.size() > 0) {
			params.put("dicNameList", dicNameList);
		}
		
		String jsonData = UIUtil.getEasyUiGridJson(total,UIUtil.getJSONFromList(sysDicMapper.selectAllDicts(params)));
		return jsonData;
	}

	/**
	 * 根据dicKey获取字典记录
	 * @param dicKey
	 * @return
	 */
	public SysDic getDictByDicKey(Long dicKey){
		return sysDicMapper.selectByDicKey(dicKey);
	}

	/**
	 * 根据name获取字典记录
	 * @param dicKey
	 * @return
	 */
	public SysDic getDictByName(String name){
		return sysDicMapper.selectByName(name);
	}	
	
	/**
	 * 根据记录ID删除数据字典
	 * @param id
	 * @return
	 */
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)	
	public int deleteDictByPrimaryKey(Long id){
		// 先删除字典项
		SysDicItem item = new SysDicItem();
		item.setId(0l);
		item.setDicid(id);
		doDeleteDicItems(item);
		// 然后删除字典
		int n = sysDicMapper.deleteByPrimaryKey(id);		
		return n;
	}
	
	/**
	 * 增加数据字典
	 * @param record
	 * @return
	 */
	public int  saveDict(SysDic record){
		return sysDicMapper.insert(record);
	}

	/**
	 * 修改数据字典
	 * @param record
	 * @return
	 */
	public int  updateDict(SysDic record){
		return sysDicMapper.updateByPrimaryKeySelective(record);
	}
	
	
	
	///////////////////////////////////// 字典项操作 //////////////////////////////////////////
	
	/**
	 * 根据dicid和parentId获取对应的字典项
	 * @param dicid
	 * @param parentId
	 * @return
	 */
	public List<SysDicItem> getDictItems(Long dicid,Long parentId){
		SysDicItem item = new SysDicItem();
		item.setDicid(dicid);
		item.setParentid(parentId);
		return sysDicItemMapper.getDictItems(item);
	}

	/**
	 * 根据dicKey和parentDictItemId获取对应的字典项
	 * @param dicKey	字典英文名
	 * @param parentDicItemKey	父字典项的key(注意不是ID)
	 * @return
	 */
	public List<SysDicItem> getDictItemsByKey(String dicKey,String parentDicItemKey){
		Map<String,String> params = new HashMap<String, String>();
		params.put("dicKey", dicKey);
		params.put("parentDicItemKey", parentDicItemKey);
		return sysDicItemMapper.getDictItemsByKey(params);
	}	
	
	/**
	 * 新增数据字典项
	 * @param record
	 * @return
	 */
	public int  saveDictItem(SysDicItem record){
		return sysDicItemMapper.insert(record);
	}

	/**
	 * 根据条件获取字典项记录
	 * @param dicId	
	 * @param dicId	
	 * @param dicItemKey
	 * @return
	 */
	public SysDicItem getDictItemByBusinessKey(Long dicId,Long parentid ,String dicItemKey){
		SysDicItem params = new SysDicItem();
		params.setDicid(dicId);
		params.setParentid(parentid);
		params.setDicitemkey(dicItemKey);
		return sysDicItemMapper.selectByBusinessKey(params);
	}

	/**
	 * 修改数据字典项
	 * @param record
	 * @return
	 */
	public int  updateDictItem(SysDicItem record){
		return sysDicItemMapper.updateByPrimaryKey(record);
	}

	/**
	 * 根据记录ID删除数据字典项
	 * @param id
	 * @return
	 */
	@Transactional(value = "transactionManager", readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void deleteDictItemByPrimaryKey(Long id){
		SysDicItem item = sysDicItemMapper.selectByPrimaryKey(id);
		doDeleteDicItems(item);
	}
	
	private void doDeleteDicItems(SysDicItem item){
		if(item==null){
			return ;
		}
		SysDicItem params = new SysDicItem();
		params.setDicid(item.getDicid());
		params.setParentid(item.getId());
		List<SysDicItem> items = sysDicItemMapper.selectChildrenByDicIdAndParentId(params);
		for(SysDicItem i2:items){
			doDeleteDicItems(i2);
		}
		sysDicItemMapper.deleteByPrimaryKey(item.getId());
	}
    
	/**
	 * 获取产品信息三级类别列表
	 * @param finDicItem
	 * @return List
	 */
	public List<SysDicItem> findAllCategoryList(SysDicItem finDicItem){
		return sysDicItemMapper.findAllCategoryList(finDicItem);
	}
	
	
	/**
	 * 根据数据字典KEY查询数据字典树形结构
	 * @param finDicItem
	 * @return List
	 */
	public List<SysDicItem> findDicItemForTree(String dicName){
		List<SysDicItem> resultList = new ArrayList<SysDicItem>();
		resultList.addAll(sysDicItemMapper.findByDicName(dicName));
		return resultList;
	}
	
public List<SysDicItem> getDictItemsByDicName(String dicName) {
		
		return sysDicItemMapper.getDictItemsByDicName(dicName);
	}
}
