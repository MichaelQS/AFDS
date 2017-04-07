package com.model.tfaults.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

import com.model.domain.Tfaults;
import com.model.tfaults.dao.TfaultsDao;

@Component("tfaultsManager")
public class TfaultsManager {

	@Resource
	private TfaultsDao tfaultsDao;

	/**
	 * 数据查询
	 * 
	 * @author snow_fly
	 * @param page
	 * @param map
	 * @throws Exception
	 */
	public void queryTfaults(Page<Tfaults> page, Map<String, Object> parameter,
			Criteria criteria) throws Exception {
		tfaultsDao.queryTfaults(page, parameter, criteria);
	}

	/**
	 * 数据储存
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveTfaults(Map<String, Collection> dataItems) throws Exception {
		Collection<Tfaults> details = (Collection<Tfaults>) dataItems
				.get("dsTfaults");
		this.saveTfaults(details);
	}

	/**
	 * 单条数据保存
	 * 
	 * @author snow_fly
	 * @param details
	 * @throws Exception
	 */
	public void saveTfaults(Collection<Tfaults> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Tfaults item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					tfaultsDao.saveData(item);
				} else if (state.equals(EntityState.MODIFIED)) {
					tfaultsDao.updateData(item);
				} else if (state.equals(EntityState.DELETED)) {
					tfaultsDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
				}
			}
		}
	}

	/**
	 * 删除临时故障路径
	 * 
	 * @author snow_fly
	 * @throws Exception
	 */
	@Expose
	public void deleteTfaults() throws Exception {
		tfaultsDao.deleteTfaults();
	}
	/**
	 * 保存故障路径
	 * 
	 * @author snow_fly
	 * @throws Exception
	 */
	@Expose
	public void saveFaults() throws Exception {
		tfaultsDao.saveFaults();
	}
	public Collection<Tfaults> queryTf() throws Exception{
		
		return tfaultsDao.queryTf();
	}
	public String saveTf(Collection<Tfaults> details) throws Exception {
		String result = "";
		if (null != details && details.size() > 0) {
			for (Tfaults item : details) {
				if (this.check(item)==0) {
					tfaultsDao.save(item);
					result =  item.getEvent();
				}
			}
		}
		return result;
	}
	public void updataTf(Collection<Tfaults> details) throws Exception {
		if (null != details && details.size() > 0) {
			for (Tfaults item : details) {
				if (this.check1(item)==0) {
					tfaultsDao.save(item);
				}
			}
		}
	}
	public int check(Tfaults item){
		int i = tfaultsDao.check(item);
		return i;
	}
	public int check1(Tfaults item){
		int i = tfaultsDao.check1(item);
		return i;
	}
	
	public String updataNo(String result, int i) throws Exception{
		Collection<Tfaults> details = this.queryTf();
		if (null != details && details.size() > 0) {
			for (Tfaults item : details) {
				if (this.check1(item)==0) {
					tfaultsDao.updata(item,i+2);
					result =  item.getEvent();
				}
			}
		}
		return result;
	}
	@Expose
	public void setNo() throws Exception {
		//String result = this.saveTf(this.queryTf());
		this.updataTf(this.queryTf());
	}

}
