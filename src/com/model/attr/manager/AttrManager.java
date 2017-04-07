
package com.model.attr.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Attr;
import com.model.attr.dao.AttrDao;

@Component("attrManager")
public class AttrManager {
	
	@Resource
	private AttrDao attrDao;
		
	/**                  
	* 数据查询
	* 
	* @author snow_fly
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryAttr(Page<Attr> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    attrDao.queryAttr(page,parameter,criteria);
	}
	
	
	/**
	 * 数据储存
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveAttr(Map<String, Collection> dataItems) throws Exception {
	    Collection<Attr> details =(Collection<Attr>) dataItems.get("dsAttr");
		this.saveAttr(details);
	 }
	 
	 
	 /**
	 * 单条数据储存
	 * 
	 * @author snow_fly
	 * @param details
	 * @throws Exception
	 */
	 public void saveAttr(Collection<Attr> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Attr item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					attrDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					attrDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										attrDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 public Collection<Map<String, Object>> queryParamGraph(String attr, String time) throws Exception {
		 return attrDao.queryParamGraph(attr, time);
		 }
	 @Expose
	 public void setFile(String filename) throws Exception {
		 attrDao.setFile(filename);
		 }
	 @Expose
	 public void play() throws Exception {
		 attrDao.play();
		 }
	
}
