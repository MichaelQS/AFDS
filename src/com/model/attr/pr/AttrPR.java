package com.model.attr.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Attr;
import com.model.attr.manager.AttrManager;

@Component("attrPR")
public class AttrPR{

    @Resource
	private AttrManager attrManager;

     
   /**                  
	* 数据查询
	* 
	* @author snow_fly
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryAttr(Page<Attr> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    attrManager.queryAttr(page,parameter,criteria);
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
	 public void saveAttr(Map<String, Collection> dataItems) throws Exception {
	    attrManager.saveAttr(dataItems);
	 }

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param 
	 * @throws Exception
	 */
	@DataProvider
	public Collection<Map<String, Object>> queryParamGraph(String attr, String time) throws Exception {
		return attrManager.queryParamGraph(attr, time);
	}
		
	
}
