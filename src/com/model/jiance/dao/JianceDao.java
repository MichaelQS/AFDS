package com.model.jiance.dao;

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
import com.gensym.classes.ImageDefinition;
import com.gensym.classes.Item;
import com.gensym.classes.KbWorkspace;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Sequence;
import com.gensym.util.Structure;
import com.gensym.util.Symbol;

import com.model.domain.Jiance;

@Repository("jianceDao")
public class JianceDao extends HibernateBaseDao {

	/**
	 * 鍚屾椂涔熸敮鎸佹櫘閫氱被鍨嬫煡璇紝鍦ㄦ暟鎹被鍨嬪拰鏃ユ湡绫诲瀷鏀寔鍖洪棿鏌ヨ
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryJiance(Page<Jiance> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Jiance.class.getName()+" a where 1=1 ");
        
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
	 * 鏁版嵁娣诲姞
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Jiance detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			//detail.setOid(this.createJiance(detail.getClasses(), detail.getEvent()));
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 鏁版嵁淇敼
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Jiance detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.update(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 鏁版嵁鍒犻櫎
	 * @param detail
	 * @throws Exception
	 */
	public void deleteData(Jiance detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	/**
	 * G2中系统的生成方法
	 * 
	 * @author snow_fly
	 * @param name
	 * @param path
	 * @throws Exception
	 */
	public String createJiance()
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
				.getItemWithUUID("968845aa816611e68723e005c57bb1d3");
		Item workspace = g2_connection.createItem(Symbol
				.intern("GEDP-TEMPLATE-DIAGRAM"));
		workspace.transferTo(ws, 0, 0);
		workspace.makePermanent();
		Object text = g2_connection.callRPC(Symbol.intern("GETUUID"),
				new Object[] { workspace });
		String uuid = text.toString();
		String id = uuid.substring(uuid.indexOf("\"") + 1, uuid.length() - 1);
		/*KbWorkspace subws = (KbWorkspace) g2_connection.callRPC(Symbol.intern("GETSUBWS"),
				new Object[] {workspace});
		g2_connection.callRPC(Symbol.intern("SETJIANCE"),
				new Object[] {event, Symbol.intern(classes), workspace });
		Item eventpost = g2_connection.createItem(Symbol
				.intern("EVENTPOST"));
		eventpost.transferTo(subws, 500, 0);
		eventpost.makePermanent();
		Item pointenter = g2_connection.createItem(Symbol
				.intern("POINTENTER"));
		pointenter.transferTo(subws, 0, 0);
		pointenter.makePermanent();
		g2_connection.callRPC(Symbol.intern("SETPOINTENTER"),
				new Object[] {eventpost, event});*/
		return id;
	}

        
}