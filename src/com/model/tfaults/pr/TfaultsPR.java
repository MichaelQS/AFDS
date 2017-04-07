package com.model.tfaults.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Tfaults;
import com.model.tfaults.manager.TfaultsManager;

@Component("tfaultsPR")
public class TfaultsPR {

	@Resource
	private TfaultsManager tfaultsManager;

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public void queryTfaults(Page<Tfaults> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		tfaultsManager.queryTfaults(page, parameter, criteria);
	}

	/**
	 * 数据保存
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveTfaults(Map<String, Collection> dataItems) throws Exception {
		tfaultsManager.saveTfaults(dataItems);
	}

}
