package com.model.attr.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.gensym.classes.G2Window;
import com.gensym.classes.Item;
import com.gensym.classes.KbWorkspace;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Symbol;

import com.model.domain.Attr;
import com.model.domain.Param;

@Repository("attrDao")
public class AttrDao extends HibernateBaseDao {

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryAttr(Page<Attr> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Attr.class.getName()+" a where 1=1 ");
        
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
	 * 数据保存
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Attr detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 数据修改
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Attr detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.update(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 删除数据
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void deleteData(Attr detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	public Collection<Map<String, Object>> queryParamGraph(String attr, String time) throws Exception {
		Collection<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String hql = "from " + Param.class.getName()
				+ " a where 1=1 order by a.oid";
		Collection<Param> details = this.query(hql);
		Float t = Float.parseFloat((time.split(" "))[0]);
		if (null != details && details.size() > 0) {
			if(((List<Param>)details).get(0).getA().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getA()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getB().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getB()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getC().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getC()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getD().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getD()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getE().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getE()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getF().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getF()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getG().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getG()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getH().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getH()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getI().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getI()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getJ().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getJ()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getK().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getK()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			if(((List<Param>)details).get(0).getL().equals(attr)){
				for (int i = 1; i < details.size(); i++) {
					Map<String, Object> args = new HashMap<String,Object>();
					args.put("param", Float.parseFloat(((List<Param>)details).get(i).getL()));
					if((i*t)%300 == 0)
						args.put("time", i/6 + ":00");
					else
						args.put("time","");
					args.put("alert", i/6 + ":" + i%6 + "0");
					list.add(args);
				}
			}
			
		}
			
		return list;
	}

	public void setFile(String filename) throws ClassNotFoundException,
			G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		//String fileName = filename.toUpperCase();
		String file = "F:\\workplace\\AFDS\\web\\images\\" + filename;
		String path = "F:\\workplace\\AFDS\\web\\images";
		g2_connection.callRPC(Symbol.intern("SETFILE"),
				new Object[] { file,filename,path });
	}

	public void play() throws ClassNotFoundException,
			G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		G2Window gw = (G2Window) g2_connection
				.getItemWithUUID("2e4c1776423811d49aea00a0249c25a1");
		g2_connection.callRPC(Symbol.intern("PLAY"), new Object[] {gw});
	}
}