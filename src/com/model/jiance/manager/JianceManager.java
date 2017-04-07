
package com.model.jiance.manager;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.gensym.classes.G2Window;
import com.gensym.classes.Item;
import com.gensym.classes.KbWorkspace;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Symbol;

import com.model.domain.Jiance;
import com.model.jiance.dao.JianceDao;

@Component("jianceManager")
public class JianceManager {
	
	@Resource
	private JianceDao jianceDao;
		
	/**                  
	* 鍒嗛〉鏌ヨ淇℃伅锛屽甫鏈塩riteria
	* 灏哻riteria杞崲涓轰竴涓狹ap
	* @param page    
	* @param map
	* @throws Exception
	*/
	public void queryJiance(Page<Jiance> page,Map<String, Object> parameter,Criteria criteria) throws Exception {
	    jianceDao.queryJiance(page,parameter,criteria);
	}
	
	
	/**
	 * 鏁版嵁淇濆瓨锛屽澶氫釜鏁版嵁闆嗙殑鎿嶄綔锛屽寘鎷鍒犳敼
	 * @param dataItems
	 * @throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public void saveJiance(Map<String, Collection> dataItems) throws Exception {
	    Collection<Jiance> details =(Collection<Jiance>) dataItems.get("dsJiance");
		this.saveJiance(details);
	 }
	 
	 
	 /**
	 * 閽堝鍗曚釜鏁版嵁闆嗘搷浣�鍖呮嫭澧炲垹鏀�
	 * 
	 * @param details
	 * @throws Exception
	 */
	 public void saveJiance(Collection<Jiance> details) throws Exception {
		if (null != details && details.size() > 0) {
	    	for(Jiance item : details) {
				EntityState state = EntityUtils.getState(item);
				if (state.equals(EntityState.NEW)) {
					jianceDao.saveData(item);
									} else if (state.equals(EntityState.MODIFIED)) {
					jianceDao.updateData(item);
									} else if (state.equals(EntityState.DELETED)) {
										jianceDao.deleteData(item);
				} else if (state.equals(EntityState.NONE)) {
														}
			}
		}
	 }
	 
	@Expose
	public void showJiance(String oid) throws ClassNotFoundException,
			G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		Item ws = g2_connection
				.getItemWithUUID(oid);
		g2_connection.callRPC(Symbol.intern("SHOWJIANCE"),
				new Object[] { ws });
	}
	@Expose
	public void sentG2datapoint() throws ClassNotFoundException,
			G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		g2_connection.callRPC(Symbol.intern("GETTHISWINDOW"),
				new Object[] {  });
	}
	 
	 
	
}
