
package com.model.param.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Param;
import com.model.param.dao.ParamDao;

@Component("paramManager")
public class ParamManager {
	
	@Resource
	private ParamDao paramDao;
		
	/**                  
	* 数据查询
	* 
	* @author snow_fly
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryParam(Page<Param> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    paramDao.queryParam(page,parameter,criteria);
	}
	
	
	/**
	 * 数据储存
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveParam(Map<String, Collection> dataItems) throws Exception {
	    Collection<Param> details =(Collection<Param>) dataItems.get("dsParam");
		this.saveParam(details);
	 }
	 
	 
	 /**
	 * 单条数据储存
	 * 
	 * @author snow_fly
	 * @param details
	 * @throws Exception
	 */
	 public void saveParam(Collection<Param> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Param item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					paramDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					paramDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										paramDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 @Expose
	 public void deleteParam() throws Exception{
		 paramDao.deleteParam();
	 }
	 
	
}
