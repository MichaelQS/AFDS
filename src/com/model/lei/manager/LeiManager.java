
package com.model.lei.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Lei;
import com.model.lei.dao.LeiDao;

@Component("leiManager")
public class LeiManager {
	
	@Resource
	private LeiDao leiDao;
		
	/**                  
	* 鍒嗛〉鏌ヨ淇℃伅锛屽甫鏈塩riteria
	* 灏哻riteria杞崲涓轰竴涓狹ap
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryLei(Page<Lei> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    leiDao.queryLei(page,parameter,criteria);
	}
	
	
	/**
	 * 鏁版嵁淇濆瓨锛屽澶氫釜鏁版嵁闆嗙殑鎿嶄綔锛屽寘鎷鍒犳敼
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveLei(Map<String, Collection> dataItems) throws Exception {
	    Collection<Lei> details =(Collection<Lei>) dataItems.get("dsLei");
		this.saveLei(details);
	 }
	 
	 
	 /**
	 * 閽堝鍗曚釜鏁版嵁闆嗘搷浣�鍖呮嫭澧炲垹鏀�
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveLei(Collection<Lei> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Lei item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					leiDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					leiDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										leiDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 public void deleteLei(String oid) throws Exception {
		 Collection<Lei> details = leiDao.queryLei(oid);
			if (null != details && details.size() > 0) {
		    	for(Lei item : details) {
		    		leiDao.deleteData(item);	
				}
			}
	}
	 @Expose
	 public void builtLei(Map<String, Object> parameter) throws Exception {
		 leiDao.builtLei(parameter);
	 } 
	
}
