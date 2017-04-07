
package com.model.lru.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Lru;
import com.model.lru.dao.LruDao;

@Component("lruManager")
public class LruManager {
	
	@Resource
	private LruDao lruDao;
		
	/**                  
	* 鍒嗛〉鏌ヨ淇℃伅锛屽甫鏈塩riteria
	* 灏哻riteria杞崲涓轰竴涓狹ap
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryLru(Page<Lru> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    lruDao.queryLru(page,parameter,criteria);
	}
	public Collection<Lru> queryLru1(Map<String, Object> parameter) throws Exception {
		return lruDao.queryLru(parameter);
	}
	
	/**
	 * 鏁版嵁淇濆瓨锛屽澶氫釜鏁版嵁闆嗙殑鎿嶄綔锛屽寘鎷鍒犳敼
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveLru(Map<String, Collection> dataItems) throws Exception {
	    Collection<Lru> details =(Collection<Lru>) dataItems.get("dsLru");
		this.saveLru(details);
	 }
	 
	 
	 /**
	 * 閽堝鍗曚釜鏁版嵁闆嗘搷浣�鍖呮嫭澧炲垹鏀�
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveLru(Collection<Lru> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Lru item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					lruDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					lruDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										lruDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
