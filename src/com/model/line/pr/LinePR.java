package com.model.line.pr;

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
import com.gensym.classes.Item;
import com.gensym.classes.KbWorkspace;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Symbol;

import com.model.domain.Line;
import com.model.line.manager.LineManager;

@Component("linePR")
public class LinePR {

	@Resource
	private LineManager lineManager;

	/**
	 * 查询方法
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	@DataProvider
	public void queryLine(Page<Line> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		lineManager.queryLine(page, parameter, criteria);
	}
	@DataProvider
	public Collection<Line> queryLine1(Map<String, Object> parameter) throws Exception {
		return lineManager.queryLine1(parameter);
	}
	/**
	 * 储存方法
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@DataResolver
	public void saveLine(Map<String, Collection> dataItems) throws Exception {
		lineManager.saveLine(dataItems);
	}

}