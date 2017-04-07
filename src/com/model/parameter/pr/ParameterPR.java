package com.model.parameter.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Parameter;
import com.model.parameter.manager.ParameterManager;

@Component("parameterPR")
public class ParameterPR {

	@Resource
	private ParameterManager parameterManager;

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public void queryParameter(Page<Parameter> page,
			Map<String, Object> parameter, Criteria criteria) throws Exception {
		parameterManager.queryParameter(page, parameter, criteria);
	}

	/**
	 * 数据储存
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveParameter(Map<String, Collection> dataItems)
			throws Exception {
		parameterManager.saveParameter(dataItems);
	}

}
