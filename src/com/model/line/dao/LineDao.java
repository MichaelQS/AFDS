package com.model.line.dao;

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
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Symbol;

import com.model.domain.Line;
import com.model.domain.Lru;

@Repository("lineDao")
public class LineDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryLine(Page<Line> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Line.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        }
		
		if (null != criteria) {
			ParseResult result = this.parseCriteria(criteria, true, "a");
			if (null != result) {
				coreHql.append(" and "+ result.getAssemblySql());
				args.putAll(result.getValueMap());
			}
		}

        
        String countHql = "select count(*) " + coreHql.toString();
        String hql = coreHql.toString();
		this.pagingQuery(page, hql, countHql, args);
	}
	
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Line detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setOid(UUID.randomUUID().toString());
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 数据修改
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Line detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.update(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 数据删除
	 * @param detail
	 * @throws Exception
	 */
	public void deleteData(Line detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	public void builtLine(Map<String, Object> parameter) throws Exception {
		String hql = "from " + Line.class.getName() + " a where 1=1 ";
		if (null != parameter && !parameter.isEmpty()) {
			String id = (String) parameter.get("id");
			if (StringUtils.isNotEmpty(id)) {
				hql += "and a.id = '" + id + "'";
			}
		}
		Collection<Line> details = this.query(hql);
		for (Line item : details) {
			this.connectLine(item);
		}
	}
	public void connectLine(Line item) throws ClassNotFoundException,G2AccessException {
			// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		String line = item.getLine();
		String toport = item.getToport();
		String fromport = item.getFromport();
		String tol = item.getTol()+"LRU";
		String froml =item.getFroml()+"LRU";
		Object text = g2_connection.callRPC(Symbol.intern("CONNECTLINE"), new Object[]{Symbol.intern(tol),Symbol.intern(froml)
		});
		
	}
	public Collection<Line> queryLine(Map<String, Object> parameter) throws Exception {
        String hql = "from " + Line.class.getName()+" a where 1=1 ";
    
        if(null != parameter && !parameter.isEmpty()){
        	String id = (String)parameter.get("id");
			 if(StringUtils.isNotEmpty(id)){
			 hql += " and a.id ='"+ id + "'";
			 }
        }
		return this.query(hql);
	}
        
}