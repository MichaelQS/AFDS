package com.model.physics.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;
import com.gensym.classes.Entity;
import com.gensym.classes.Item;
import com.gensym.classes.KbWorkspace;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Symbol;

import com.model.domain.Feature;
import com.model.domain.Line;
import com.model.domain.Lru;
import com.model.domain.Physics;

@Repository("physicsDao")
public class PhysicsDao extends HibernateBaseDao {

	/**
	 * lru数据库查询方法
	 * 
	 * @author snow_fly
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryPhysics(Page<Physics> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreHql = new StringBuffer("from "
				+ Physics.class.getName() + " a where 1=1 ");

		if (null != parameter && !parameter.isEmpty()) {
			String origin = (String) parameter.get("origin");
			if (StringUtils.isNotEmpty(origin)) {
				coreHql.append(" and a.origin= :origin ");
				args.put("origin", origin);
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

	/**
	 * 保存方法
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Physics detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setOid(this.createLRU(detail.getLru(), detail.getX(),
					detail.getY(), detail.getOrigin(), detail.getSystem(),
					detail.getFeature()));
			if (StringUtils.isNotEmpty(detail.getFeature())){
				detail.setLru(detail.getLru() + "_" + detail.getSystem() + "_" + detail.getFeature());
			}else
				detail.setLru(detail.getLru() + "_" + detail.getSystem());
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	public void builtPhysics(Map<String, Object> parameter) throws Exception {
		String hql = "from " + Lru.class.getName() + " a where 1=1 ";
		if (null != parameter && !parameter.isEmpty()) {
			String id = (String) parameter.get("id");
			if (StringUtils.isNotEmpty(id)) {
				hql += "and a.id = '" + id + "'";
			}
		}
		Collection<Lru> details = this.query(hql);
		for (Lru item : details) {
			this.createlru(item);
		}
	}

	/**
	 * 修改方法
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Physics detail) throws Exception {
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
	public void deleteData(Physics detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			this.deleteLRU(detail.getOid());
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	public void createlru(Lru item) throws ClassNotFoundException,
			G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		Item ws = g2_connection.getItemWithUUID("149b891a9fdb11e58284c454446acd24");
		KbWorkspace subws = (KbWorkspace) g2_connection.callRPC(Symbol.intern("GETS"),
				new Object[] { ws });
		String cla = item.getText();
		String sys = item.getSystem();
		String fea = item.getFeature();
		Object lru = g2_connection.createItem(Symbol.intern(cla));
		//Object text = g2_connection.callRPC(Symbol.intern("GETUUID"),
		//		new Object[] { lru });
		//String uuid = text.toString();
		//String id = uuid.substring(uuid.indexOf("\"") + 1, uuid.length() - 1);
		//if (StringUtils.isNotEmpty(fea)){
			((Entity) lru).setNames(Symbol.intern(item.getKe()+"LRU"));
		//}else
		//	((Entity) lru).setNames(Symbol.intern(cla + "_" + sys));
		String[] arr = item.getLoc().split(" ");
		String m = arr[0];
		String n = arr[1];
		Double x = Double.parseDouble(m);
		Double y = Double.parseDouble(n);
		int a = (new Double(x)).intValue();
		int b = (new Double(y)).intValue();
		((Item) lru).transferTo(subws, a, b);
		((Item) lru).makePermanent();
	}
	/**
	 * G2中lru生成方法
	 * 
	 * @author snow_fly
	 * @param cla
	 * @param x
	 * @param y
	 * @param wspace
	 * @param sys
	 * @param fea
	 * @throws Exception
	 */
	public String createLRU(String cla, Double x, Double y, String wspace,
			String sys, String fea) throws ClassNotFoundException,
			G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		Item ws = g2_connection.getItemWithUUID(wspace);
		KbWorkspace subws = (KbWorkspace) g2_connection.callRPC(Symbol.intern("GETS"),
				new Object[] { ws });
		Object lru = g2_connection.createItem(Symbol.intern(cla));
		Object text = g2_connection.callRPC(Symbol.intern("GETUUID"),
				new Object[] { lru });
		String uuid = text.toString();
		String id = uuid.substring(uuid.indexOf("\"") + 1, uuid.length() - 1);
		if (StringUtils.isNotEmpty(fea)){
			((Entity) lru).setNames(Symbol.intern(cla + "_" + sys + "_" + fea));
		}else
			((Entity) lru).setNames(Symbol.intern(cla + "_" + sys));
		int a = (new Double(x)).intValue();
		int b = (new Double(y)).intValue();
		((Item) lru).transferTo(subws, a, b);
		((Item) lru).makePermanent();
		return id;
	}

	/**
	 * G2中lru删除方法
	 * 
	 * @author snow_fly
	 * @param oid
	 * @throws Exception
	 */
	public void deleteLRU(String oid) throws ClassNotFoundException,
			G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		Item lru = g2_connection.getItemWithUUID(oid);
		lru.delete(true, true);
	}
	public void changeColor(Map<String, Object> parameter) throws ClassNotFoundException, G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		String oid = (String)parameter.get("oid");
		Item lru = g2_connection.getItemWithUUID(oid);
		g2_connection.callRPC(Symbol.intern("LRUCOLOR"), new Object[]{
			lru
		});	
	}
	/**
	 * 删除workspace中相关的LRU
	 * 
	 * @author snow_fly
	 * @param item
	 * @throws Exception
	 */
	public void queryAndDeleteLru(String oid) throws Exception{
		String hql = "from " + Physics.class.getName() + " a where 1=1 and a.origin = '" + oid + "'";
		Collection<Physics> details = this.query(hql);
		if (null != details && details.size() > 0) {
			for(Physics detail: details){
				this.deleteData(detail);
			}
		}	
		}
}