
package com.model.repair.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Repair;
import com.model.repair.dao.RepairDao;

@Component("repairManager")
public class RepairManager {
	
	@Resource
	private RepairDao repairDao;
		
	/**                  
	* 鍒嗛〉鏌ヨ淇℃伅锛屽甫鏈塩riteria
	* 灏哻riteria杞崲涓轰竴涓狹ap
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryRepair(Page<Repair> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    repairDao.queryRepair(page,parameter,criteria);
	}
	
	
	/**
	 * 鏁版嵁淇濆瓨锛屽澶氫釜鏁版嵁闆嗙殑鎿嶄綔锛屽寘鎷鍒犳敼
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveRepair(Map<String, Collection> dataItems) throws Exception {
	    Collection<Repair> details =(Collection<Repair>) dataItems.get("dsRepair");
		this.saveRepair(details);
	 }
	 
	 
	 /**
	 * 閽堝鍗曚釜鏁版嵁闆嗘搷浣�鍖呮嫭澧炲垹鏀�
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveRepair(Collection<Repair> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Repair item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					repairDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					repairDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										repairDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 public void save(String result) throws Exception {
		 Repair item = new Repair();
		 item.setEvent(result);
		 repairDao.saveData(item);
	 }
	 @Expose
	 public void delete() throws Exception {
		 Collection<Repair> details = repairDao.queryRepair();
		 if (null != details && details.size() > 0) {
		    	for(Repair item : details) {
		    		 repairDao.deleteData(item);
		    	}
		 }
		
	 }
	
}
