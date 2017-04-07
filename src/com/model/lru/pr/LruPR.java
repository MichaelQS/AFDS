package com.model.lru.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Lru;
import com.model.lru.manager.LruManager;

@Component("lruPR")
public class LruPR{

    @Resource
	private LruManager lruManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryLru(Page<Lru> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    lruManager.queryLru(page,parameter,criteria);
	}
	@DataProvider
	public Collection<Lru> queryLru1(Map<String, Object> parameter) throws Exception {
		return lruManager.queryLru1(parameter);
	}
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveLru(Map<String, Collection> dataItems) throws Exception {
	    lruManager.saveLru(dataItems);
	 }
	
}
