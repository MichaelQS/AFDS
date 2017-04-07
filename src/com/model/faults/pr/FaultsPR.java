package com.model.faults.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Faults;
import com.model.faults.manager.FaultsManager;

@Component("faultsPR")
public class FaultsPR {

	@Resource
	private FaultsManager faultsManager;

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public void queryFaults(Page<Faults> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		faultsManager.queryFaults(page, parameter, criteria);
	}
	
	@DataProvider
	public Collection<Faults> queryFaults1(Map<String, Object> parameter
			) throws Exception {
		return faultsManager.queryFaults(parameter);
	}

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param parameter
	 * @throws Exception
	 */
	@DataProvider
	public Collection<Map<String, Object>> queryResults(
			Map<String, Object> parameter) throws Exception {
		return faultsManager.queryResults(parameter);
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
	public void saveFaults(Map<String, Collection> dataItems) throws Exception {
		faultsManager.saveFaults(dataItems);
	}

}
