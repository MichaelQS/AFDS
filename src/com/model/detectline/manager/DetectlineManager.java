
package com.model.detectline.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Detectline;
import com.model.detectline.dao.DetectlineDao;

@Component("detectlineManager")
public class DetectlineManager {
	
	@Resource
	private DetectlineDao detectlineDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryDetectline(Page<Detectline> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    detectlineDao.queryDetectline(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveDetectline(Map<String, Collection> dataItems) throws Exception {
	    Collection<Detectline> details =(Collection<Detectline>) dataItems.get("dsDetectline");
		this.saveDetectline(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveDetectline(Collection<Detectline> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Detectline item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					detectlineDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					detectlineDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										detectlineDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
