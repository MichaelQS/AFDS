package com.model.systems.pr;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.gensym.classes.ImageDefinition;
import com.gensym.classes.Item;
import com.gensym.classes.KbWorkspace;
import com.gensym.classes.SystemItem;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Symbol;

import com.model.domain.Systems;
import com.model.systems.manager.SystemsManager;

@Component("systemsPR")
public class SystemsPR{

    @Resource
	private SystemsManager systemsManager;

     
   /**                  
	* 系统查询方法
	* 
	* @author snow_fly
	* @param page    
	* @param map
	* @throws Exception
	*/
	@DataProvider
	public void querySystems(Page<Systems> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    systemsManager.querySystems(page,parameter,criteria);
	}
	
	
	/**
	 * 系统保存方法
	 * 
	 * @author snow_fly
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings("rawtypes")
	 @DataResolver
	 public void saveSystems(Map<String, Collection> dataItems) throws Exception {
	    systemsManager.saveSystems(dataItems);
	 }
	 /**
		 * 系统在G2界面展示方法
		 * 
		 * @author snow_fly
		 * @param wspace
		 * @throws Exception
		 */
		@Expose
		public void showSystem(String wspace)
				throws ClassNotFoundException, G2AccessException {
			// TODO Auto-generated method stub
			G2Connection g2_connection = null;
			try {
				g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
			} catch (G2AccessInitiationException e) {
				System.out.println("Problem connection to G2 exception was:"
						+ e.toString());
			}
			Item ws = g2_connection
					.getItemWithUUID(wspace);
			KbWorkspace subws = (KbWorkspace) g2_connection.callRPC(Symbol.intern("GETS"),
					new Object[] { ws });
			g2_connection.callRPC(Symbol.intern("SHOWWORKSPACE"), new Object[]{subws});
		}
}
