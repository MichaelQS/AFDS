package com.model.detectlru.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Detectlru;
import com.model.detectlru.manager.DetectlruManager;

@Component("detectlruPR")
public class DetectlruPR{

    @Resource
	private DetectlruManager detectlruManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryDetectlru(Page<Detectlru> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    detectlruManager.queryDetectlru(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveDetectlru(Map<String, Collection> dataItems) throws Exception {
	    detectlruManager.saveDetectlru(dataItems);
	 }
	
}
