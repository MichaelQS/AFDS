package com.model.repairresult.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Repairresult;
import com.model.repairresult.manager.RepairresultManager;

@Component("repairresultPR")
public class RepairresultPR{

    @Resource
	private RepairresultManager repairresultManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryRepairresult(Page<Repairresult> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    repairresultManager.queryRepairresult(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveRepairresult(Map<String, Collection> dataItems) throws Exception {
	    repairresultManager.saveRepairresult(dataItems);
	 }
	
}
