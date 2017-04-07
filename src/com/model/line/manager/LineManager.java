
package com.model.line.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Line;
import com.model.line.dao.LineDao;

@Component("lineManager")
public class LineManager {
	
	@Resource
	private LineDao lineDao;
		
	/**                  
	* 鍒嗛〉鏌ヨ淇℃伅锛屽甫鏈塩riteria
	* 灏哻riteria杞崲涓轰竴涓狹ap
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryLine(Page<Line> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    lineDao.queryLine(page,parameter,criteria);
	}
	public Collection<Line> queryLine1(Map<String, Object> parameter) throws Exception {
		return lineDao.queryLine(parameter);
	}
	
	/**
	 * 鏁版嵁淇濆瓨锛屽澶氫釜鏁版嵁闆嗙殑鎿嶄綔锛屽寘鎷鍒犳敼
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveLine(Map<String, Collection> dataItems) throws Exception {
	    Collection<Line> details =(Collection<Line>) dataItems.get("dsLine");
		this.saveLine(details);
	 }
	 
	 
	 /**
	 * 閽堝鍗曚釜鏁版嵁闆嗘搷浣�鍖呮嫭澧炲垹鏀�
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveLine(Collection<Line> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Line item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					lineDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					lineDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										lineDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	@Expose
	public void builtLine(Map<String, Object> parameter) throws Exception {
		lineDao.builtLine(parameter);
	}
	 
	
}
