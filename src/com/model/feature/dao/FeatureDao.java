package com.model.feature.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;
import com.gensym.classes.ImageDefinition;
import com.gensym.classes.Item;
import com.gensym.classes.ItemList;
import com.gensym.classes.KbWorkspace;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Sequence;
import com.gensym.util.Structure;
import com.gensym.util.Symbol;

import com.model.domain.Feature;
import com.model.domain.Line;
import com.model.domain.Physics;
import com.model.domain.Systems;

@Repository("featureDao")
public class FeatureDao extends HibernateBaseDao {

	/**
	 * 功能数据库查询方法
	 * 
	 * @author snow_fly
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryFeature(Page<Feature> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreHql = new StringBuffer("from "
				+ Feature.class.getName() + " a where 1=1 ");

		if (null != parameter && !parameter.isEmpty()) {
			String oid = (String) parameter.get("oid");
			if (StringUtils.isNotEmpty(oid)) {
				coreHql.append(" and a.system.oid = :oid ");
				args.put("oid", oid);
			}
		}

		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, "a");
			if (null != result) {
				coreHql.append(" and " + result.getAssemblySql());
				args.putAll(result.getValueMap());
			}
		}

		String countHql = "select count(*) " + coreHql.toString();
		String hql = coreHql.toString();
		this.pagingQuery(page, hql, countHql, args);
	}
	public Collection<Feature> queryFeature(String oid) throws Exception {
		String hql = "from " + Feature.class.getName() + " a where 1=1 and a.system.oid = '" + oid +"'";
		return this.query(hql);
	}
	/**
	 * 保存方法
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Feature detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			//detail.setOid(this.createFeature(detail.getFeature(),
			//		detail.getInformation()));
			detail.setOid(UUID.randomUUID().toString());
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 修改方法
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Feature detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.update(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 删除方法
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void deleteData(Feature detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			//this.deleteFeature(detail.getOid(), detail.getFeature());
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * G2中功能生成方法
	 * 
	 * @author snow_fly
	 * @param name
	 * @param path
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public String createFeature(String name, String path)
			throws ClassNotFoundException, G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		KbWorkspace ws = (KbWorkspace) g2_connection
				.getItemWithUUID("1846956dc5f111e4abb60021ccc83f7d");
		KbWorkspace mapws = (KbWorkspace) g2_connection
				.getItemWithUUID("a488bf870c6011e6b628e005c57bb1d3");
		Item workspace = g2_connection.createItem(Symbol
				.intern("PHYSICS"));
		workspace.transferTo(mapws, 0, 0);
		workspace.makePermanent();
		KbWorkspace subws = (KbWorkspace) g2_connection.callRPC(Symbol.intern("GETSUB"),
				new Object[] { workspace });
		Object text = g2_connection.callRPC(Symbol.intern("GETUUID"),
				new Object[] { workspace });
		String uuid = text.toString();
		String id = uuid.substring(uuid.indexOf("\"") + 1, uuid.length() - 1);
		ImageDefinition image = (ImageDefinition) g2_connection
				.createItem(Symbol.intern("IMAGE-DEFINITION"));
		subws.setNames(Symbol.intern(name));
		image.setNames(Symbol.intern(name + "IMAGE"));
		image.transferTo(ws, 0, 0);
		image.makePermanent();
		Sequence s = new Sequence();
		Structure a = new Structure();
		a.addAttribute(Symbol.intern("IMAGE-NAME"), Symbol.intern(name + "IMAGE"));
		s.add(a);
		g2_connection.callRPC(Symbol.intern("CHANGEIMAGE"), new Object[] {
			subws, image, path,s});
		g2_connection.callRPC(Symbol.intern("SHOWWORKSPACE"),
				new Object[] { subws });
		return id;
	}

	/**
	 * G2中功能删除方法
	 * 
	 * @author snow_fly
	 * @param oid
	 * @throws Exception
	 */
	public void deleteFeature(String oid, String feature) throws ClassNotFoundException,
			G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		Item ws = g2_connection.getItemWithUUID(oid);
		Item image = g2_connection.getUniqueNamedItem(Symbol.intern("IMAGE-DEFINITION"), Symbol.intern(feature + "IMAGE"));
		ws.delete(true, true);
		image.delete(true, true);
	}
	
}