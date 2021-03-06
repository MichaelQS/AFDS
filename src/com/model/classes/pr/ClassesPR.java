package com.model.classes.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Classes;
import com.model.classes.manager.ClassesManager;

@Component("classesPR")
public class ClassesPR{

    @Resource
	private ClassesManager classesManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryClasses(Page<Classes> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    classesManager.queryClasses(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveClasses(Map<String, Collection> dataItems) throws Exception {
	    classesManager.saveClasses(dataItems);
	 }
	
}
