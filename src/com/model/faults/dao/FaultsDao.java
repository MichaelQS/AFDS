package com.model.faults.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;
import com.gensym.classes.Entity;
import com.gensym.classes.G2Window;
import com.gensym.classes.Item;
import com.gensym.classes.KbWorkspace;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Sequence;
import com.gensym.util.Structure;
import com.gensym.util.Symbol;

import com.model.domain.Faults;
import com.model.domain.Feature;
import com.model.domain.Road;
import com.model.domain.Systems;
import com.model.domain.Parameter;
import com.model.domain.Repair;
import com.model.domain.Repairresult;
import com.model.domain.Tfaults;
import com.model.parameter.dao.ParameterDao;
import com.model.repairresult.dao.RepairresultDao;
import com.model.tfaults.dao.TfaultsDao;

@Repository("faultsDao")
public class FaultsDao extends HibernateBaseDao {

	@Resource
	private ParameterDao parameterDao;
	@Resource
	private RepairresultDao repairresultDao;
	@Resource
	private TfaultsDao tfaultsDao;

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryFaults(Page<Faults> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreHql = new StringBuffer("from "
				+ Faults.class.getName() + " a where 1=1 ");

		if (null != parameter && !parameter.isEmpty()) {
			
			 String roadid = (String)parameter.get("roadid");
			 if(StringUtils.isNotEmpty(roadid)){
			 coreHql.append(" and a.roadid ='"+ roadid + "'");
			 }//else{
			 //coreHql.append(" and a.roadid = null"); 
			 //}
			 
			String classes = (String) parameter.get("classes");
			if (StringUtils.isNotEmpty(classes)) {
				coreHql.append(" and a.classes ='" + classes + "'");
			}// else {
			//coreHql.append(" and a.classes = null");
			//}
			String event = (String) parameter.get("event");
			if (StringUtils.isNotEmpty(event)) {
				coreHql.append(" and a.event ='" + event + "'");
			}
			/*
			 * String origin = (String)parameter.get("origin");
			 * if(StringUtils.isNotEmpty(origin)){
			 * coreHql.append(" and a.origin ='"+ origin + "'"); }else{
			 * coreHql.append(" and a.origin = null"); }
			 */
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
	public Collection<Faults> queryFaults(String roadid) throws Exception {
		String hql = "from " + Faults.class.getName() + " a where 1=1 and a.roadid ='"+ roadid + "'";
		return this.query(hql);
	}
	public Collection<Faults> queryFaultsByRoadid(String roadid, String param) throws Exception {
		String hql = "from " + Faults.class.getName() + " a where 1=1 and a.classes = '" + param + "' and a.roadid ='"+ roadid + "'";
		return this.query(hql);
	}
	public Collection<Faults> queryFaultsByRoadid1(String roadid) throws Exception {
		String hql = "from " + Faults.class.getName() + " a where 1=1 and a.parentnode = null and a.roadid ='"+ roadid + "'";
		return this.query(hql);
	}
	public Collection<Faults> queryFaultsByRoadid2(String roadid) throws Exception {
		String hql = "from " + Faults.class.getName() + " a where 1=1 and a.roadid ='"+ roadid + "'";
		return this.query(hql);
	}
	
	public Collection<Faults> queryFaults(Map<String, Object> parameter) throws Exception{
		String hql = "from " +  Road.class.getName() + " a where 1=1 ";
		String id = (String)parameter.get("id");
		if(StringUtils.isNotEmpty(id)){
			hql += " and a.id ='"+ id + "'";
		}
		String param = "";
		String lru = (String)parameter.get("lru");
		if(StringUtils.isNotEmpty(lru)){
			param = lru;
		}
		Collection<Road> details = this.query(hql);
		Collection<Faults> collection = new ArrayList<Faults>();
		for(Road item: details){
			Collection<Faults> faults = this.queryFaultsByRoadid(item.getRoadid(),param);
			collection.addAll(faults);
		}
		return collection;
	}
	
	public Collection<Faults> queryFaults1(Map<String, Object> parameter) throws Exception{
		String hql = "from " +  Road.class.getName() + " a where 1=1";
		String id = (String)parameter.get("id");
		if(StringUtils.isNotEmpty(id)){
			hql += " and a.id ='"+ id + "'";
		}
		Collection<Road> details = this.query(hql);
		Collection<Faults> collection = new ArrayList<Faults>();
		for(Road item: details){
			Collection<Faults> faults = this.queryFaultsByRoadid1(item.getRoadid());
			collection.addAll(faults);
		}
		return collection;
	}
	public Collection<Faults> queryFaults2(Map<String, Object> parameter) throws Exception{
		String hql = "from " +  Road.class.getName() + " a where 1=1";
		String id = (String)parameter.get("id");
		if(StringUtils.isNotEmpty(id)){
			hql += " and a.id ='"+ id + "'";
		}
		Collection<Road> details = this.query(hql);
		Collection<Faults> collection = new ArrayList<Faults>();
		for(Road item: details){
			Collection<Faults> faults = this.queryFaultsByRoadid2(item.getRoadid());
			collection.addAll(faults);
		}
		return collection;
	}

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public Collection<Map<String, Object>> queryResults(
			Collection<Faults> fault) throws Exception {
		
		Collection<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		/*String hql = "from " + Faults.class.getName()
				+ " a where 1=1 and a.parentnode = null";
		if (null != parameter && !parameter.isEmpty()) {
			String system = (String) parameter.get("system");
			if (system != null) {
				hql += " and a.system = '" + system + "'";
			} else {
				hql += " and a.system = null";
			}
			String feature = (String) parameter.get("feature");
			if (feature != null) {
				hql += " and a.feature = '" + feature + "'";
			} else {
				hql += " and a.feature = null";
			}
			String origin = (String) parameter.get("origin");
			if (origin != null) {
				hql += " and a.origin = '" + origin + "'";
			} else {
				hql += " and a.origin = null";
			}
		}*/
		Collection<Faults> details = fault;//this.query(hql);
		if (details != null && details.size() > 0) {
			for (Faults item : details) {
				String result = "";
				result = this.queryFaultsByParentnode(item, result);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("result", result.substring(4));
				map.put("roadid", item.getRoadid());
				list.add(map);
			}
		}

		return list;
	}

	/**
	 * 查询故障路径
	 * 
	 * @author snow_fly
	 * @param item
	 * @throws Exception
	 */
	public String queryFaultsByParentnode(Faults item, String result) {
		result += "————" + item.getEvent() + "(" + item.getClasses() + ")";
		String hql = "from " + Faults.class.getName()
				+ " a where 1=1 and a.parentnode = '" + item.getOid() + "'";
		Collection<Faults> details = this.query(hql);
		if (details != null && details.size() > 0) {
			result = this.queryFaultsByParentnode(
					((List<Faults>) details).get(0), result);
		}
		return result;
	}

	/**
	 * 查询故障事件
	 * 
	 * @author snow_fly
	 * @param item
	 * @throws Exception
	 */
	public String queryEvent(Faults item) {
		String result = "";
		String hql = "from " + Faults.class.getName()
				+ " a where 1=1 and a.parentnode = '" + item.getOid() + "'";
		Collection<Faults> details = this.query(hql);
		if (details != null && details.size() > 0) {
			result += ((List<Faults>) details).get(0).getEvent();
		}
		return null;
	}

	/**
	 * 保存数据
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Faults detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 修改数据
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void updateData(Faults detail) throws Exception {
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
	public void deleteData(Faults detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	/**
	 * 将由G2中传递的所属类参数传递给临时参数库
	 * 
	 * @author snow_fly
	 * @param classes
	 * @throws Exception
	 */
	public void paramChange(String classes) throws Exception {
		String hql = "from " + Parameter.class.getName() + " a where 1=1";
		Collection<Parameter> details = this.query(hql);
		Parameter param = ((List<Parameter>) details).get(0);
		param.setClasses(classes);
		parameterDao.updateData(param);
	}

	public void builtFaults(Collection<Map<String, Object>> list)
			throws ClassNotFoundException, G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		List<String> arr = new ArrayList<String>();
		if (list != null && list.size() > 0) {
			for (Map<String, Object> item : list) {
				String[] result = item.get("result").toString().split("————");
				int num = result.length;
				for (int i = 0; i < num; i++) {
					if (arr.contains(result[i])) {
						if (i == 0) {
						} else {
							KbWorkspace ws = (KbWorkspace) g2_connection.getItemWithUUID("33802e400b5411e69e94e005c57bb1d3");
							Object view = g2_connection.createItem(Symbol.intern("VIEW"));
							//((Entity) view).setNames(Symbol.intern(result[i]));
							((Item) view).transferTo(ws, 0, 0);
							((Item) view).makePermanent();
							String name = result[i-1].substring(0,result[i-1].indexOf("("));
							String cla = result[i-1].substring(result[i-1].indexOf("(")+1,result[i-1].indexOf(")"));
							System.out.print(name + "\n");
							System.out.print(cla + "\n");
							Object event = g2_connection.callRPC(Symbol.intern("GETEVENT"), new Object[]{
								Symbol.intern(result[i])
							});
							g2_connection.callRPC(Symbol.intern("SETVIEW"), new Object[]{
								name,Symbol.intern(cla),view
							});
							g2_connection.callRPC(Symbol.intern("CONNECTVIEWANDEVENT"), new Object[]{
								Symbol.intern("CDG-CONNECTED-TO"),Symbol.intern("电流管"),view,event
							});
							//System.out.print("建立view:" + result[i] + "\n");
							//System.out.print("连接event:" + result[i-1]
							//		+ "和view:" + result[i] + "\n");
						}
					} else {
						arr.add(result[i]);
						if (i == 0) {
							KbWorkspace ws = (KbWorkspace) g2_connection.getItemWithUUID("33802e400b5411e69e94e005c57bb1d3");
							Object event = g2_connection.createItem(Symbol.intern("EVENT"));
							((Entity) event).setNames(Symbol.intern(result[i]));
							((Item) event).transferTo(ws, 100, 0);
							((Item) event).makePermanent();
							String name = result[i].substring(0,result[i].indexOf("("));
							String cla = result[i].substring(result[i].indexOf("(")+1,result[i].indexOf(")"));
							g2_connection.callRPC(Symbol.intern("SETEVENT"), new Object[]{
								name,Symbol.intern(cla),event
							});
							System.out.print(name + "\n");
							System.out.print(cla + "\n");
							//System.out.print("建立event:" + result[i] + "\n");
						} else {
							KbWorkspace ws = (KbWorkspace) g2_connection.getItemWithUUID("33802e400b5411e69e94e005c57bb1d3");
							Object view = g2_connection.createItem(Symbol.intern("VIEW"));
							//((Entity) view).setNames(Symbol.intern(result[i-1]));
							((Item) view).transferTo(ws, 0, 0);
							((Item) view).makePermanent();
							String vname = result[i-1].substring(0,result[i-1].indexOf("("));
							String vcla = result[i-1].substring(result[i-1].indexOf("(")+1,result[i-1].indexOf(")"));
							System.out.print(vname + "\n");
							System.out.print(vcla + "\n");
							Object event = g2_connection.createItem(Symbol.intern("EVENT"));
							((Entity) event).setNames(Symbol.intern(result[i]));
							((Item) event).transferTo(ws, 100, 0);
							((Item) event).makePermanent();
							String ename = result[i].substring(0,result[i].indexOf("("));
							String ecla = result[i].substring(result[i].indexOf("(")+1,result[i].indexOf(")"));
							System.out.print(ename + "\n");
							System.out.print(ecla + "\n");
							g2_connection.callRPC(Symbol.intern("SETEVENT"), new Object[]{
								ename,Symbol.intern(ecla),event
							});
							g2_connection.callRPC(Symbol.intern("SETVIEW"), new Object[]{
								vname,Symbol.intern(vcla),view
							});
							g2_connection.callRPC(Symbol.intern("CONNECTVIEWANDEVENT"), new Object[]{
								Symbol.intern("CDG-CONNECTED-TO"),Symbol.intern("电流管"),view,event
							});
							//System.out.print("建立view:" + result[i - 1]
							//		+ "————event:" + result[i] + "\n");
						}
					}
				}
			}
		}
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
		String system = (String)parameter.get("system");
		String feature = (String)parameter.get("feature");
		String result = (String)parameter.get("result");
		String[] items = result.toString().split("————");
		int num = items.length;
		for(int i =0; i < num; i++){
			String cla = items[i].substring(items[i].indexOf("(")+1,items[i].indexOf(")"));
			String name = "";
			if (StringUtils.isNotEmpty(feature)){
				name = cla + "_" + system + "_" + feature;
			}else
				name = cla + "_" + system;
			g2_connection.callRPC(Symbol.intern("CHANGECOLOR"), new Object[]{
				Symbol.intern(name)
			});
			System.out.print(name + "\n");
		}
	}
	public void reColor(Map<String, Object> parameter) throws ClassNotFoundException, G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		String name = (String)parameter.get("name");
		g2_connection.callRPC(Symbol.intern("RECOLOR"), new Object[]{
			Symbol.intern(name)
		});	
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void changeG2Color(
			Map<String, Object> parameter) throws Exception {
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		KbWorkspace ws = (KbWorkspace) g2_connection.getItemWithUUID("6be9b7db129111e68853e005c57bb1d3");
		String origin = (String)parameter.get("origin");
		Map<String, Object> args = this.querySysAndFeaByoid(origin);
		String system = (String) args.get("system");
		String feature = (String) args.get("feature");
		String sql = "select distinct a.classes from moxing_faults a where 1=1 and a.origin = '" + origin + "'";
		Session session = this.getSessionFactory().openSession();
		try {
			Query query = session.createSQLQuery(sql).addScalar("classes",
					Hibernate.STRING);// 设置返回值类型，不然会报错
			List<String> result = query.list();
			if (result.isEmpty()) {
			} else {
				for(String item: result){
					String hql = "from " + Faults.class.getName()
							+ " a where 1=1 and a.origin = '" + origin + "' and a.classes = '" + item + "'";
					Collection<Faults> details = this.query(hql);
					Item pro = g2_connection.createItem(Symbol.intern("PRO"));
					pro.transferTo(ws, 0, 0);
					pro.makePermanent();
					Item repair = g2_connection.createItem(Symbol.intern("REPAIR"));
					repair.transferTo(ws, 0, 0);
					repair.makePermanent();
					Sequence s = new Sequence();
					for(Faults detail: details){
						Structure a = new Structure();
						a.addAttribute(Symbol.intern("EVENT-NAME"), detail.getEvent());
						a.addAttribute(Symbol.intern("TARGET-CLASS"), Symbol.intern(item));
						s.add(a);
					}
					String procedure = "";
					if (StringUtils.isNotEmpty(feature)){
						procedure = item + "procedure(target: class " + item + " , specificaction: class cdg-specific-action, triggeringevent: item-or-value , associatedevents : sequence ,timestamp : integer , win : class object) \n begin \n call setcolor(the symbol " + item + "_" + system + "_" + feature + " ) \n end";
					}else{
						procedure = item + "procedure(target: class " + item + " , specificaction: class cdg-specific-action, triggeringevent: item-or-value , associatedevents : sequence ,timestamp : integer , win : class object) \n begin \n call setcolor(the symbol " + item + "_" + system + " ) \n end";
					}
					g2_connection.callRPC(Symbol.intern("SETPRONAME"), new Object[]{
						pro,procedure
					});
					g2_connection.callRPC(Symbol.intern("CHANGEG2COLOR"), new Object[]{
						repair, item+"TRUE", Symbol.intern(item+"PROCEDURE"), Symbol.intern(item), s
					});
				}
			}
		} finally {
			session.flush();
			session.close();
		}
	}
	public Map<String, Object> querySysAndFeaByoid(String oid){
		Map<String, Object> args = new HashMap<String, Object>();
		String hql = "from "+  Systems.class.getName() + " a where a.oid = '" + oid + "'";
		Collection<Systems> details = this.query(hql);
		String system = "";
		String feature = "";
		if(details != null && details.size() > 0){
			for(Systems detail: details){
				system = detail.getSystem(); 
			}
			args.put("system", system);
			args.put("feature", feature);
		}else{
			String sql = "from "+  Feature.class.getName() + " a where a.oid = '" + oid + "'";
			Collection<Feature> items = this.query(sql);
			if(items != null && items.size() > 0){
				for(Feature item: items){
					system = item.getSystem().getSystem();
					feature = item.getFeature();
				}
				args.put("system", system);
				args.put("feature", feature);
			}
		}
		return args;
	}
	public void startDiagnose() throws Exception {
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		G2Window gw = (G2Window) g2_connection
				.getItemWithUUID("2e4c1776423811d49aea00a0249c25a1");
		g2_connection.callRPC(Symbol.intern("STARTDIAGNOSE"), new Object[]{
			gw
		});
	}
	public void endDiagnose(Map<String, Object> parameter) throws Exception {
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		g2_connection.callRPC(Symbol.intern("STOPPLAY"), new Object[]{});	
		//String name = (String)parameter.get("name");
		//g2_connection.callRPC(Symbol.intern("REDIAGNOSECOLOR"), new Object[]{
		//	Symbol.intern(name)
		//});	
		G2Window gw = (G2Window) g2_connection
				.getItemWithUUID("2e4c1776423811d49aea00a0249c25a1");
		KbWorkspace eventws = (KbWorkspace) g2_connection.getItemWithUUID("33802e400b5411e69e94e005c57bb1d3");
		KbWorkspace repairws = (KbWorkspace) g2_connection.getItemWithUUID("6be9b7db129111e68853e005c57bb1d3");
		g2_connection.callRPC(Symbol.intern("ENDDIAGNOSE"), new Object[]{
			eventws, repairws, gw
		});
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void builtRepair(
			Collection<Faults> fault) throws Exception {
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		this.setup(g2_connection);
		//String origin = (String)parameter.get("origin");
		//String hql = "from " + Faults.class.getName()
		//		+ " a where 1=1 and a.origin = '" + origin + "' and a.parentnode = null";
		Collection<Faults> details = fault;//this.query(hql);
		KbWorkspace ws = (KbWorkspace) g2_connection.getItemWithUUID("6be9b7db129111e68853e005c57bb1d3");
		if(details != null && details.size() > 0){
			for(Faults detail: details){
				Sequence s = new Sequence();
				Structure a = new Structure();
				a.addAttribute(Symbol.intern("EVENT-NAME"), detail.getEvent());
				a.addAttribute(Symbol.intern("TARGET-CLASS"), Symbol.intern(detail.getClasses()));
				s.add(a);
				Item pro = g2_connection.createItem(Symbol.intern("PRO"));
				pro.transferTo(ws, 0, 0);
				pro.makePermanent();
				Item repair = g2_connection.createItem(Symbol.intern("REPAIR"));
				repair.transferTo(ws, 0, 0);
				repair.makePermanent();
				String procedure = detail.getEvent() + "(target: class " + detail.getClasses() + " , specificaction: class cdg-specific-action, triggeringevent: item-or-value , associatedevents : sequence ,timestamp : integer , win : class object) \n begin \n call set-gensym-repair (no-name, \""+detail.getEvent()+"\") \n end";		
				g2_connection.callRPC(Symbol.intern("SETPRONAME"), new Object[]{
					pro,procedure
				});
				g2_connection.callRPC(Symbol.intern("CHANGEG2COLOR"), new Object[]{
					repair, detail.getEvent()+"("+detail.getClasses()+")", Symbol.intern(detail.getEvent()), Symbol.intern(detail.getClasses()), s
				});
			}
		}	
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void changeG2Color(
			Collection<Faults> fault) throws Exception {
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		//this.setup(g2_connection);
		//String origin = (String)parameter.get("origin");
		//String hql = "from " + Faults.class.getName()
		//		+ " a where 1=1 and a.origin = '" + origin + "' and a.parentnode = null";
		Collection<Faults> details = this.distinct(fault);
		//this.query(hql);
		KbWorkspace ws = (KbWorkspace) g2_connection.getItemWithUUID("6be9b7db129111e68853e005c57bb1d3");
		if(details != null && details.size() > 0){
			for(Faults detail: details){
				Sequence s = new Sequence();
				Structure a = new Structure();
				a.addAttribute(Symbol.intern("EVENT-NAME"), detail.getEvent());
				a.addAttribute(Symbol.intern("TARGET-CLASS"), Symbol.intern(detail.getClasses()));
				s.add(a);
				Item pro = g2_connection.createItem(Symbol.intern("PRO"));
				pro.transferTo(ws, 0, 0);
				pro.makePermanent();
				Item repair = g2_connection.createItem(Symbol.intern("REPAIR"));
				repair.transferTo(ws, 0, 0);
				repair.makePermanent();
				String procedure = detail.getEvent() + "C"+ detail.getClasses()+"(target: class " + detail.getClasses() + " , specificaction: class cdg-specific-action, triggeringevent: item-or-value , associatedevents : sequence ,timestamp : integer , win : class object) \n begin \n call set-gensym-fault (no-name, \""+detail.getEvent()+"\" , \""+detail.getClasses()+"\") \n end";		
				g2_connection.callRPC(Symbol.intern("SETPRONAME"), new Object[]{
					pro,procedure
				});
				g2_connection.callRPC(Symbol.intern("CHANGEG2COLOR"), new Object[]{
					repair, detail.getEvent()+"C("+detail.getClasses()+")", Symbol.intern(detail.getEvent() + "C" + detail.getClasses()), Symbol.intern(detail.getClasses()), s
				});
			}
		}	
	}
	public Collection<Faults> distinct(Collection<Faults> faults){
		Collection<Faults> details = new ArrayList<Faults>();
		if(faults != null && faults.size() > 0){
			for(Faults fault: faults){
				int i = 0;
				if(details != null && details.size() > 0){
					for(Faults detail: details){
						if(detail.getClasses()==fault.getClasses()&&detail.getEvent()==fault.getEvent()){
							i=1;
						}
					}
				}
				if(i==0){
					details.add(fault);
				}
			}
		}
		return details;
	}
	/**
	 * G2调用程序
	 * 
	 * @author snow_fly
	 * @param classes
	 * @throws Exception
	 */
	public String setrepair(String event) throws Exception {
		System.out.println(event);
		String hql = "from " + Repair.class.getName() + " a where 1=1 and a.event ='" + event +"'";
		Collection<Repair> details = this.query(hql);
		if(details != null && details.size() > 0){
			for(Repair item: details){
				Repairresult result = new Repairresult();
				result.setEvent(item.getEvent());
				result.setLocation(item.getLocation());
				result.setTool(item.getTool());
				result.setMethod(item.getMethod());
				repairresultDao.saveData(result);
			}
		}
		return event;
	}
	public void setfaults(String event,String classes) throws Exception {
		Tfaults tfaults = new Tfaults();
		tfaults.setEvent(event);
		tfaults.setClasses(classes);
		tfaultsDao.saveData(tfaults);
	}
	/**
	 * G2调用程序注册到桥中
	 * 
	 * @author snow_fly
	 * @param g2_connection
	 * @throws Exception
	 */
	public void setup(G2Connection g2_connection) {
		Symbol g2RPDName = Symbol.intern("SETREPAIR");
		Symbol g2RPDName1 = Symbol.intern("SETFAULTS");
		g2_connection.registerJavaMethod(this, "setrepair", g2RPDName);
		g2_connection.registerJavaMethod(this, "setfaults", g2RPDName1);

	}
	public void deleteResult() throws Exception{
		String hql = "from " + Repairresult.class.getName() + " a where 1=1";
		Collection<Repairresult> details = this.query(hql);
		if(details != null && details.size() > 0 ){
			for(Repairresult item: details){
				repairresultDao.deleteData(item);
			}
		}
	}
	public void deleteTfaults() throws Exception{
		String hql = "from " + Tfaults.class.getName() + " a where 1=1";
		Collection<Tfaults> details = this.query(hql);
		if(details != null && details.size() > 0 ){
			for(Tfaults item: details){
				tfaultsDao.deleteData(item);
			}
		}
	}
	public void deleteroad(String roadid) throws Exception{
		String hql = "from " + Faults.class.getName() + " a where 1=1 and a.roadid = '" + roadid +"'";
		Collection<Faults> details = this.query(hql);
		if(details != null && details.size() > 0 ){
			for(Faults item: details){
				this.deleteData(item);
			}
		}
	}
	
	public void sendEvent(Map<String, Object> parameter) throws Exception {
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		String event = (String)parameter.get("event");
		String key = (String)parameter.get("key");
		g2_connection.callRPC(Symbol.intern("SENDEVENT"), new Object[]{
			Symbol.intern(key+"LRU"),event
		});	
	}
	
	public void result() throws Exception {
	}
}