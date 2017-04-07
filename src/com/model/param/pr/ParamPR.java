package com.model.param.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Param;
import com.model.param.manager.ParamManager;

@Component("paramPR")
public class ParamPR{

    @Resource
	private ParamManager paramManager;

     
   /**                  
	* 数据查询
	* 
	* @author snow_fly
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryParam(Page<Param> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    paramManager.queryParam(page,parameter,criteria);
	}
	
	
	/**
	 * 数据储存
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveParam(Map<String, Collection> dataItems) throws Exception {
	    paramManager.saveParam(dataItems);
	 }
	
}
