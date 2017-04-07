package com.model.lei.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Lei;
import com.model.lei.manager.LeiManager;

@Component("leiPR")
public class LeiPR{

    @Resource
	private LeiManager leiManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryLei(Page<Lei> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    leiManager.queryLei(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveLei(Map<String, Collection> dataItems) throws Exception {
	    leiManager.saveLei(dataItems);
	 }
	
}
