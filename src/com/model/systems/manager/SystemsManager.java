
package com.model.systems.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Systems;
import com.model.physics.dao.PhysicsDao;
import com.model.systems.dao.SystemsDao;
import com.model.feature.manager.FeatureManager;
import com.model.lei.manager.LeiManager;
import com.model.line.dao.LineDao;

@Component("systemsManager")
public class SystemsManager {
	
	@Resource
	private SystemsDao systemsDao;
	@Resource
	private FeatureManager featureManager;
	@Resource
	private LineDao lineDao;
	@Resource
	private PhysicsDao physicsDao;
	@Resource
	private LeiManager leiManager;
		
	/**                  
	
	* 系统查询方法
	* 
	* @author snow_fly
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void querySystems(Page<Systems> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    systemsDao.querySystems(page,parameter,criteria);
	}
	
	
	/**
	 * 系统保存方法
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveSystems(Map<String, Collection> dataItems) throws Exception {
	    Collection<Systems> details =(Collection<Systems>) dataItems.get("dsSystems");
		this.saveSystems(details);
	 }
	 
	 
	 /**
	 * 系统条目保存方法
	 * 
	 * @author snow_fly
	 * @param details
	 * @throws Exception
	 */
	 public void saveSystems(Collection<Systems> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Systems item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					systemsDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					systemsDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										featureManager.deleteFeature(item.getOid());
							    		leiManager.deleteLei(item.getOid());
										systemsDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
				featureManager.saveFeature(item.getFt());
				leiManager.saveLei(item.getLei());
			}
		}
	 }
	 public void deleteSystems(String oid) throws Exception {
		Collection<Systems> details = systemsDao.querySystems(oid);
		if (null != details && details.size() > 0) {
	    	for(Systems item : details) {
	    		featureManager.deleteFeature(item.getOid());
	    		leiManager.deleteLei(item.getOid());
	    		systemsDao.deleteData(item);
	    	}
		}
	}
	 
	
}
