package com.model.road.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Road;
import com.model.road.manager.RoadManager;

@Component("roadPR")
public class RoadPR{

    @Resource
	private RoadManager roadManager;

     
   /**                  
	* 分页查询信息，带有criteria
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void queryRoad(Page<Road> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    roadManager.queryRoad(page,parameter,criteria);
	}
	
	@DataProvider
	public Collection<Road> queryRoad1() throws Exception {
	    return roadManager.queryRoad1();
	}
	/**
	 * 数据保存，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveRoad(Map<String, Collection> dataItems) throws Exception {
	    roadManager.saveRoad(dataItems);
	 }
	
}
