package com.model.road.dao;

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

import com.model.domain.Faults;
import com.model.domain.Road;
import com.model.domain.Tfaults;

@Repository("roadDao")
public class RoadDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryRoad(Page<Road> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Road.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
        	String id = (String) parameter.get("id");
			if (StringUtils.isNotEmpty(id)) {
				coreHql.append(" and a.id = :id ");
				args.put("id", id);
			}
			String roadid = (String) parameter.get("roadid");
			if (StringUtils.isNotEmpty(roadid)) {
				coreHql.append(" and a.roadid = :roadid ");
				args.put("roadid", roadid);
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
	public Collection<Road> queryRoad1() throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		Map<String, Object> args1 = new HashMap<String, Object>();
		String hql = "select distinct a.event from " + Tfaults.class.getName() + " a where 1=1";
		List<String> event = this.query(hql);
		String hql1 = "select distinct a.classes from " + Tfaults.class.getName() + " a where 1=1";
		List<String> classes = this.query(hql1);
		args.put("event", event);
		args.put("classes", classes);
		if (event.isEmpty()||classes.isEmpty()) {
			return null;
		} else {
			StringBuffer coreHql = new StringBuffer("select distinct a.roadid from "
				+ Faults.class.getName() + " a where 1=1 and a.event in(:event) and a.classes in(:classes)");
			List<String> roadid = this.query(coreHql.toString(), args);
			Collection<Road> details= this.query(coreHql.toString(), args);
			return details;
        }
	}
	/**
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Road detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setRoadid(UUID.randomUUID().toString());
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
	public void updateData(Road detail) throws Exception {
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
	public void deleteData(Road detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
        
}