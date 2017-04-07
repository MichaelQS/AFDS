package com.model.lei.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Session;
import org.kabeja.parser.ParseException;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;
import com.gensym.classes.ClassDefinition;
import com.gensym.classes.KbWorkspace;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Sequence;
import com.gensym.util.Symbol;

import com.model.domain.Lei;
import com.model.domain.Systems;

@Repository("leiDao")
public class LeiDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryLei(Page<Lei> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Lei.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String oid = (String) parameter.get("oid");
			if (StringUtils.isNotEmpty(oid)) {
				coreHql.append(" and a.system.oid = :oid ");
				args.put("oid", oid);
			}
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
	public Collection<Lei> queryLei(String oid) throws Exception {
		String hql = "from " + Lei.class.getName() + " a where 1=1 and a.system.oid = '" + oid +"'";
		return this.query(hql);
	}
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Lei detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setOid(UUID.randomUUID().toString());
			//detail.setOid(this.createClasses(detail.getLei()));
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
	public void updateData(Lei detail) throws Exception {
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
	public void deleteData(Lei detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	public void builtLei(Map<String, Object> parameter) throws Exception {
		 String hql = "from " + Lei.class.getName()+" a where 1=1 ";
		 if(null != parameter && !parameter.isEmpty()){
	        	String oid = (String) parameter.get("oid");
				if (StringUtils.isNotEmpty(oid)) {
					hql += " and a.system.oid = '" + oid + "'";
				}
	        }
		 Collection<Lei> details = this.query(hql);
		 if (null != details && details.size() > 0) {
		    	for(Lei item : details) {
		    		this.createClasses(item.getLei());
		    	}
			}
	 } 
	@Expose
	public static void createClasses(String lei) throws ClassNotFoundException,
			G2AccessException, ParseException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		KbWorkspace ws = (KbWorkspace) g2_connection
				.getItemWithUUID("e64999d4643311e695b9e005c57bb1d3");
		ClassDefinition classes = (ClassDefinition) g2_connection
				.createItem(Symbol.intern("CLASS-DEFINITION"));
		classes.setClassName(Symbol.intern(lei));
		Sequence s = new Sequence();
		s.add(Symbol.intern("ɲ��ϵͳ"));
		classes.setDirectSuperiorClasses(s);
		classes.transferTo(ws, 0, 0);
		classes.makePermanent();
		//Object text = g2_connection.callRPC(Symbol.intern("GETUUID"),
		//		new Object[] { classes });
		//String uuid = text.toString();
		//String id = uuid.substring(uuid.indexOf("\"") + 1, uuid.length() - 1);
		//return id;
	}
        
}