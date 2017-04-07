
package com.model.road.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Road;
import com.model.faults.manager.FaultsManager;
import com.model.road.dao.RoadDao;

@Component("roadManager")
public class RoadManager {
	
	@Resource
	private RoadDao roadDao;
	@Resource
	private FaultsManager faultsManager;
		
	/**                  
	* 鍒嗛〉鏌ヨ淇℃伅锛屽甫鏈塩riteria
	* 灏哻riteria杞崲涓轰竴涓狹ap
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryRoad(Page<Road> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    roadDao.queryRoad(page,parameter,criteria);
	}
	
	public Collection<Road> queryRoad1() throws Exception {
	    return roadDao.queryRoad1();
	}
	/**
	 * 鏁版嵁淇濆瓨锛屽澶氫釜鏁版嵁闆嗙殑鎿嶄綔锛屽寘鎷鍒犳敼
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveRoad(Map<String, Collection> dataItems) throws Exception {
	    Collection<Road> details =(Collection<Road>) dataItems.get("dsRoad");
		this.saveRoad(details);
	 }
	 
	 
	 /**
	 * 閽堝鍗曚釜鏁版嵁闆嗘搷浣�鍖呮嫭澧炲垹鏀�
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveRoad(Collection<Road> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Road item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					roadDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					roadDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										faultsManager.deleteFaults(item.getRoadid());
										roadDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
				faultsManager.saveFaults(item.getFault());
			}
		}
	 }
	 
	 
	
}
