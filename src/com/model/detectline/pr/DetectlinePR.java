package com.model.detectline.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Detectline;
import com.model.detectline.manager.DetectlineManager;

@Component("detectlinePR")
public class DetectlinePR{

    @Resource
	private DetectlineManager detectlineManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryDetectline(Page<Detectline> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    detectlineManager.queryDetectline(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveDetectline(Map<String, Collection> dataItems) throws Exception {
	    detectlineManager.saveDetectline(dataItems);
	 }
	
}
