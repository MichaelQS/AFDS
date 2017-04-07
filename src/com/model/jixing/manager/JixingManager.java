
package com.model.jixing.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Jixing;
import com.model.feature.manager.FeatureManager;
import com.model.jixing.dao.JixingDao;
import com.model.systems.manager.SystemsManager;

@Component("jixingManager")
public class JixingManager {
	
	@Resource
	private JixingDao jixingDao;
	@Resource
	private SystemsManager systemsManager;
		
	/**                  
	* 鍒嗛〉鏌ヨ淇℃伅锛屽甫鏈塩riteria
	* 灏哻riteria杞崲涓轰竴涓狹ap
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryJixing(Page<Jixing> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    jixingDao.queryJixing(page,parameter,criteria);
	}
	
	
	/**
	 * 鏁版嵁淇濆瓨锛屽澶氫釜鏁版嵁闆嗙殑鎿嶄綔锛屽寘鎷鍒犳敼
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveJixing(Map<String, Collection> dataItems) throws Exception {
	    Collection<Jixing> details =(Collection<Jixing>) dataItems.get("dsJixing");
		this.saveJixing(details);
	 }
	 
	 
	 /**
	 * 閽堝鍗曚釜鏁版嵁闆嗘搷浣�鍖呮嫭澧炲垹鏀�
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveJixing(Collection<Jixing> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Jixing item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					jixingDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					jixingDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										systemsManager.deleteSystems(item.getOid());
										jixingDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
				systemsManager.saveSystems(item.getSy());
			}
		}
	 }
	 
	 
	
}
