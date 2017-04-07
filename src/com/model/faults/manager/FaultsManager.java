package com.model.faults.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Symbol;

import com.model.domain.Faults;
import com.model.faults.dao.FaultsDao;

@Component("faultsManager")
public class FaultsManager {

	@Resource
	private FaultsDao faultsDao;

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryFaults(Page<Faults> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		faultsDao.queryFaults(page, parameter, criteria);
	}
	
	public Collection<Faults> queryFaults(Map<String, Object> parameter) throws Exception {
		return faultsDao.queryFaults(parameter);
	}

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param parameter
	 * @throws Exception
	 */
	public Collection<Map<String, Object>> queryResults(
			Map<String, Object> parameter) throws Exception {
		Collection<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		    return list;
	}

	/**
	 * 数据储存
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveFaults(Map<String, Collection> dataItems) throws Exception {
		Collection<Faults> details = (Collection<Faults>) dataItems
				.get("dsFaults");
		this.saveFaults(details);
	}

	/**
	 * 单条数据操作
	 * 
	 * @author snow_fly
	 * @param details
	 * @throws Exception
	 */
	public void saveFaults(Collection<Faults> details) throws Exception {
		if (null != details && details.size() > 0) {
			String oid = null;
			for (Faults item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					if (oid != null) {
						item.setParentnode(oid);
					}
					item.setOid(UUID.randomUUID().toString());
					faultsDao.saveData(item);
					oid = item.getOid();
				} else if (state.equals(EntityState.MODIFIED)) {
					faultsDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					faultsDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
				}
			}
		}
	}
	public void deleteFaults(String roadid) throws Exception {
		Collection<Faults> details = faultsDao.queryFaults(roadid);
		if (null != details && details.size() > 0) {
			for (Faults item : details) {
				faultsDao.deleteData(item);	
			}
		}
	}

	/**
	 * G2调用程序
	 * 
	 * @author snow_fly
	 * @param classes
	 * @throws Exception
	 */
	public String getresult(String classes) throws Exception {
		System.out.println(classes);
		faultsDao.paramChange(classes);
		return classes;
	}
	/**
	 * G2调用程序注册到桥中
	 * 
	 * @author snow_fly
	 * @param g2_connection
	 * @throws Exception
	 */
	public void setup(G2Connection g2_connection) {
		Symbol g2RPDName = Symbol.intern("GET-RESULT");
		g2_connection.registerJavaMethod(this, "getresult", g2RPDName);

	}
	/**
	 * G2连接程序
	 * 
	 * @author snow_fly
	 * @throws G2AccessException
	 */
	@Expose
	public void connect() throws G2AccessException {
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		this.setup(g2_connection);
	}
	/**
	 * 生成故障模型
	 * 
	 * @author snow_fly
	 * @throws Exception 
	 */
	@Expose
	public void builtFaults(Map<String, Object> parameter) throws Exception {
		Collection<Faults> faults = faultsDao.queryFaults1(parameter);
		Collection<Map<String, Object>> list = faultsDao.queryResults(faults);
		faultsDao.builtFaults(list);
		//faultsDao.changeG2Color(parameter);
		
		faultsDao.builtRepair(faultsDao.queryFaults1(parameter));
		faultsDao.changeG2Color(faultsDao.queryFaults2(parameter));
	}
	@Expose
	public void changeColor(Map<String, Object> parameter) throws Exception {
		faultsDao.changeColor(parameter);
	}
	@Expose
	public void reColor(Map<String, Object> parameter) throws Exception {
		faultsDao.reColor(parameter);
	}
	@Expose
	public void startDiagnose() throws Exception {
		faultsDao.startDiagnose();
	}
	@Expose
	public void endDiagnose(Map<String, Object> parameter) throws Exception{
		faultsDao.endDiagnose(parameter);
	}
	@Expose
	public void deleteResult() throws Exception{
		faultsDao.deleteResult();
	}
	@Expose
	public void deleteTfaults() throws Exception{
		faultsDao.deleteTfaults();
	}
	@Expose
	public void deleteroad(String roadid) throws Exception{
		faultsDao.deleteroad(roadid);
	}
	@Expose
	public void sendEvent(Map<String, Object> parameter) throws Exception{
		faultsDao.sendEvent(parameter);
	}
	@Expose
	public void result() throws Exception {
		faultsDao.result();
	}
}
