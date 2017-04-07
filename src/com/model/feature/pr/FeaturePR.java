package com.model.feature.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Feature;
import com.model.feature.manager.FeatureManager;

@Component("featurePR")
public class FeaturePR {

	@Resource
	private FeatureManager featureManager;

	/**
	 * 功能查询方法
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public void queryFeature(Page<Feature> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		featureManager.queryFeature(page, parameter, criteria);
	}

	/**
	 * 功能保存方法
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveFeature(Map<String, Collection> dataItems) throws Exception {
		featureManager.saveFeature(dataItems);
	}

}
