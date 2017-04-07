
package com.model.classes.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Classes;
import com.model.classes.dao.ClassesDao;

@Component("classesManager")
public class ClassesManager {
	
	@Resource
	private ClassesDao classesDao;
		
	/**                  
	* 鍒嗛〉鏌ヨ淇℃伅锛屽甫鏈塩riteria
	* 灏哻riteria杞崲涓轰竴涓狹ap
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryClasses(Page<Classes> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    classesDao.queryClasses(page,parameter,criteria);
	}
	
	
	/**
	 * 鏁版嵁淇濆瓨锛屽澶氫釜鏁版嵁闆嗙殑鎿嶄綔锛屽寘鎷鍒犳敼
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveClasses(Map<String, Collection> dataItems) throws Exception {
	    Collection<Classes> details =(Collection<Classes>) dataItems.get("dsClasses");
		this.saveClasses(details);
	 }
	 
	 
	 /**
	 * 閽堝鍗曚釜鏁版嵁闆嗘搷浣�鍖呮嫭澧炲垹鏀�
	 * 
	 * @param details
	 * @throws Exception
	 */
	 @Expose
	 public void saveClasses(Collection<Classes> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Classes item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					classesDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					classesDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										classesDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	 
	
}
