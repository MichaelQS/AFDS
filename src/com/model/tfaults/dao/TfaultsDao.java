package com.model.tfaults.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;

import com.model.domain.Faults;
import com.model.domain.Tfaults;
import com.model.faults.dao.FaultsDao;

@Repository("tfaultsDao")
public class TfaultsDao extends HibernateBaseDao {

	@Resource
	private FaultsDao faultsDao;

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryTfaults(Page<Tfaults> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		Map<String, Object> args = new HashMap<String, Object>();
		StringBuffer coreHql = new StringBuffer("from "
				+ Tfaults.class.getName() + " a where 1=1 ");

		if (null != parameter && !parameter.isEmpty()) {
			String no = (String) parameter.get("no");
			if (StringUtils.isNotEmpty(no)){
				coreHql.append(" and a.no= :no ");
				args.put("no", no);
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
		hql += " order by a.no";
		this.pagingQuery(page, hql, countHql, args);
	}

	/**
	 * 数据保存
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Tfaults detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setRoadid(UUID.randomUUID().toString());
			session.save(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	public void save(Tfaults detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setNo("a");
			session.update(detail);
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
	public void updateData(Tfaults detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.update(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	public void updata(Tfaults detail,int i) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setNo("a");
			session.update(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 数据删除
	 * 
	 * @author snow_fly
	 * @param detail
	 * @throws Exception
	 */
	public void deleteData(Tfaults detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}

	/**
	 * 删除临时故障路径
	 * 
	 * @author snow_fly
	 * @throws Exception
	 */
	public void deleteTfaults() throws Exception {
		String hql = "from " + Tfaults.class.getName() + " a where 1=1 ";
		Collection<Tfaults> details = this.query(hql);
		if (null != details && details.size() > 0)
			for (Tfaults item : details) {
				this.deleteData(item);
			}
	}
	public Collection<Tfaults> queryTf() throws Exception {
		String hql = "from " + Tfaults.class.getName() + " a where 1=1 ";
		Collection<Tfaults> details = this.query(hql);
		return details;
	}
	/**
	 * 保存故障路径
	 * 
	 * @author snow_fly
	 * @throws Exception
	 */
	public void saveFaults() throws Exception {
		String hql = "from " + Tfaults.class.getName()
				+ " a where 1=1 order by a.no";
		Collection<Tfaults> details = this.query(hql);
		if (null != details && details.size() > 0) {
			String oid = null;
			for (Tfaults item : details) {
				Faults faults = new Faults();
				if (oid != null) {
					faults.setParentnode(oid);
				}
				faults.setOid(UUID.randomUUID().toString());
				faults.setClasses(item.getClasses());
				faults.setEvent(item.getEvent());
				//faults.setRoadid(item.getRoadid());
				faults.setOrigin(item.getOrigin());
				faultsDao.saveData(faults);
				oid = faults.getOid();
			}
		}
	}
	public int check(Tfaults item){
		String hql = "from " + Faults.class.getName()
				+ " a where 1=1";
		Collection<Faults> details = this.query(hql);
		String result ="aaa";
		if (null != details && details.size() > 0) {
			for (Faults fitem : details) {
				if(fitem.getEvent() == item.getEvent()){
					result = fitem.getOid();
				}
			}
		}
		int i = 1;
		if (null != details && details.size() > 0) {
			for (Faults fitem : details) {
				if(fitem.getParentnode().equals(result) ){
					i = 0;
				}
			}
		}
		return i;
	}
	public int check1(Tfaults item){
		String hql = "from " + Faults.class.getName()
				+ " a where 1=1";
		Collection<Faults> details = this.query(hql);
		int i = 1;
		if (null != details && details.size() > 0) {
			for (Faults fitem : details) {
				if(fitem.getEvent().equals(item.getEvent())&&fitem.getClasses().equals(item.getClasses())){
					if(fitem.getParentnode()==null){
						i=0;
					}
				}
			}
		}
		return i;
	}
}