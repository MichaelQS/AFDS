
package com.model.repairresult.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Repairresult;
import com.model.repairresult.dao.RepairresultDao;

@Component("repairresultManager")
public class RepairresultManager {
	
	@Resource
	private RepairresultDao repairresultDao;
		
	/**                  
	* 分页查询信息，带有criteria
	* 将criteria转换为一个Map
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryRepairresult(Page<Repairresult> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    repairresultDao.queryRepairresult(page,parameter,criteria);
	}
	
	
	/**
	 * 数据保存，对多个数据集的操作，包括增删改
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveRepairresult(Map<String, Collection> dataItems) throws Exception {
	    Collection<Repairresult> details =(Collection<Repairresult>) dataItems.get("dsRepairresult");
		this.saveRepairresult(details);
	 }
	 
	 
	 /**
	 * 针对单个数据集操作 包括增删改
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveRepairresult(Collection<Repairresult> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Repairresult item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					repairresultDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					repairresultDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										repairresultDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
