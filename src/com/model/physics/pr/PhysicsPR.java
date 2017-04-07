package com.model.physics.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.gensym.classes.Entity;
import com.gensym.classes.ImageDefinition;
import com.gensym.classes.Item;
import com.gensym.classes.KbWorkspace;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Symbol;

import com.model.domain.Physics;
import com.model.physics.manager.PhysicsManager;

@Component("physicsPR")
public class PhysicsPR {

	@Resource
	private PhysicsManager physicsManager;

	/**
	 * lru查询方法
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public void queryPhysics(Page<Physics> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		physicsManager.queryPhysics(page, parameter, criteria);
	}

	/**
	 * lru保存方法
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void savePhysics(Map<String, Collection> dataItems) throws Exception {
		physicsManager.savePhysics(dataItems);
	}

}
