package com.model.parameter.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Parameter;
import com.model.parameter.dao.ParameterDao;

@Component("parameterManager")
public class ParameterManager {

	@Resource
	private ParameterDao parameterDao;

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryParameter(Page<Parameter> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		parameterDao.queryParameter(page, parameter, criteria);
	}

	/**
	 * 数据储存
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveParameter(Map<String, Collection> dataItems)
			throws Exception {
		Collection<Parameter> details = (Collection<Parameter>) dataItems
				.get("dsParam");
		this.saveParameter(details);
	}

	/**
	 * 单条数据储存
	 * 
	 * @author snow_fly
	 * @param details
	 * @throws Exception
	 */
	public void saveParameter(Collection<Parameter> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Parameter item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					parameterDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					parameterDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					parameterDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
				}
			}
		}
	}

}
