package com.model.detect.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Detect;
import com.model.detect.manager.DetectManager;

@Component("detectPR")
public class DetectPR{

    @Resource
	private DetectManager detectManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryDetect(Page<Detect> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    detectManager.queryDetect(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveDetect(Map<String, Collection> dataItems) throws Exception {
	    detectManager.saveDetect(dataItems);
	 }
	
}
