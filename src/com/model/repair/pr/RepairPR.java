package com.model.repair.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Repair;
import com.model.repair.manager.RepairManager;

@Component("repairPR")
public class RepairPR{

    @Resource
	private RepairManager repairManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryRepair(Page<Repair> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    repairManager.queryRepair(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveRepair(Map<String, Collection> dataItems) throws Exception {
	    repairManager.saveRepair(dataItems);
	 }
	
}
