
package com.model.detectlru.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Detectlru;
import com.model.detectlru.dao.DetectlruDao;

@Component("detectlruManager")
public class DetectlruManager {
	
	@Resource
	private DetectlruDao detectlruDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryDetectlru(Page<Detectlru> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    detectlruDao.queryDetectlru(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveDetectlru(Map<String, Collection> dataItems) throws Exception {
	    Collection<Detectlru> details =(Collection<Detectlru>) dataItems.get("dsDetectlru");
		this.saveDetectlru(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操�?包括增删�?
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveDetectlru(Collection<Detectlru> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Detectlru item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					detectlruDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					detectlruDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										detectlruDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
