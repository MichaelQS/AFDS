package com.model.jiance.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Jiance;
import com.model.jiance.manager.JianceManager;

@Component("jiancePR")
public class JiancePR{

    @Resource
	private JianceManager jianceManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryJiance(Page<Jiance> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    jianceManager.queryJiance(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveJiance(Map<String, Collection> dataItems) throws Exception {
	    jianceManager.saveJiance(dataItems);
	 }
	
}
