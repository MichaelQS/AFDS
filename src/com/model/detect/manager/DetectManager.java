
package com.model.detect.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Detect;
import com.model.domain.Tfaults;
import com.model.detect.dao.DetectDao;

@Component("detectManager")
public class DetectManager {
	
	@Resource
	private DetectDao detectDao;
		
	/**                  
	* 鍒嗛〉鏌ヨ淇℃伅锛屽甫鏈塩riteria
	* 灏哻riteria杞崲涓轰竴涓狹ap
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryDetect(Page<Detect> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    detectDao.queryDetect(page,parameter,criteria);
	}
	
	
	/**
	 * 鏁版嵁淇濆瓨锛屽澶氫釜鏁版嵁闆嗙殑鎿嶄綔锛屽寘鎷鍒犳敼
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveDetect(Map<String, Collection> dataItems) throws Exception {
	    Collection<Detect> details =(Collection<Detect>) dataItems.get("dsDetect");
		this.saveDetect(details);
	 }
	 
	 
	 /**
	 * 閽堝鍗曚釜鏁版嵁闆嗘搷浣�鍖呮嫭澧炲垹鏀�
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveDetect(Collection<Detect> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Detect item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					detectDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					detectDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										detectDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 @Expose
	 public void builtDetect(Map<String, Object> parameter) throws Exception {
		 detectDao.builtDetect(parameter);
		 }
	 
	 public Collection<Detect> queryDetectOnTfaults(Collection<Tfaults> details) throws Exception {
		    return detectDao.queryDetectOnTfaults(details);
		}
}
