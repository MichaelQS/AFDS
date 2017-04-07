package com.model.feature.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Feature;
import com.model.feature.dao.FeatureDao;
import com.model.line.dao.LineDao;
import com.model.physics.dao.PhysicsDao;

@Component("featureManager")
public class FeatureManager {

	@Resource
	private FeatureDao featureDao;
	@Resource
	private LineDao lineDao;
	@Resource
	private PhysicsDao physicsDao;
	/**
	 * 功能查询方法
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryFeature(Page<Feature> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		featureDao.queryFeature(page, parameter, criteria);
	}

	/**
	 * 功能储存方法
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveFeature(Map<String, Collection> dataItems) throws Exception {
		Collection<Feature> details = (Collection<Feature>) dataItems
				.get("dsFeature");
		this.saveFeature(details);
	}

	/**
	 * 功能条目储存方法
	 * 
	 * @author snow_fly
	 * @param details
	 * @throws Exception
	 */
	public void saveFeature(Collection<Feature> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Feature item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					featureDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					featureDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					//lineDao.queryAndDeleteLine(item.getOid());
					//physicsDao.queryAndDeleteLru(item.getOid());
					featureDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
				}
			}
		}
	}
	public void deleteFeature(String oid) throws Exception {
		Collection<Feature> details = featureDao.queryFeature(oid);
		if (null != details && details.size() > 0) {
			for (Feature item : details) {
				featureDao.deleteData(item);
			}
		}
	}

}
