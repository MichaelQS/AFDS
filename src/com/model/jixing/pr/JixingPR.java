package com.model.jixing.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Jixing;
import com.model.jixing.manager.JixingManager;

@Component("jixingPR")
public class JixingPR{

    @Resource
	private JixingManager jixingManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryJixing(Page<Jixing> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    jixingManager.queryJixing(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveJixing(Map<String, Collection> dataItems) throws Exception {
	    jixingManager.saveJixing(dataItems);
	 }
	
}
