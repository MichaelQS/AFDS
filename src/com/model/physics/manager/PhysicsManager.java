package com.model.physics.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Physics;
import com.model.line.dao.LineDao;
import com.model.physics.dao.PhysicsDao;

@Component("physicsManager")
public class PhysicsManager {

	@Resource
	private PhysicsDao physicsDao;
	@Resource
	private LineDao lineDao;
	/**
	 * lru查询方法
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryPhysics(Page<Physics> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		physicsDao.queryPhysics(page, parameter, criteria);
	}

	/**
	 * lru保存方法
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void savePhysics(Map<String, Collection> dataItems) throws Exception {
		Collection<Physics> details = (Collection<Physics>) dataItems
				.get("dsPhysics");
		this.savePhysics(details);
	}

	/**
	 * lru条目储存方法
	 * 
	 * @author snow_fly
	 * @param details
	 * @throws Exception
	 */
	public void savePhysics(Collection<Physics> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Physics item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					physicsDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					physicsDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					//lineDao.queryAndDeleteLine(item);
					physicsDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
				}
			}
		}
	}
	@Expose
	public void changeColor(Map<String, Object> parameter) throws Exception {
		physicsDao.changeColor(parameter);
	}
	@Expose
	public void builtPhysics(Map<String, Object> parameter) throws Exception {
		physicsDao.builtPhysics(parameter);
	}
}
