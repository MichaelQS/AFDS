package com.model.detect.dao;

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
import com.gensym.classes.Item;
import com.gensym.classes.KbWorkspace;
import com.gensym.jgi.G2AccessException;
import com.gensym.jgi.G2AccessInitiationException;
import com.gensym.jgi.G2Connection;
import com.gensym.jgi.G2Gateway;
import com.gensym.util.Symbol;
import com.gensym.classes.Entity;

import com.model.domain.Attr;
import com.model.domain.Detect;
import com.model.domain.Detectline;
import com.model.domain.Detectlru;
import com.model.domain.Lru;
import com.model.domain.Tfaults;
import com.model.faults.dao.FaultsDao;
import com.model.jiance.dao.JianceDao;

@Repository("detectDao")
public class DetectDao extends HibernateBaseDao {
	
	@Resource
	private JianceDao jianceDao;

	/**
	 * 鍚屾椂涔熸敮鎸佹櫘閫氱被鍨嬫煡璇紝鍦ㄦ暟鎹被鍨嬪拰鏃ユ湡绫诲瀷鏀寔鍖洪棿鏌ヨ
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryDetect(Page<Detect> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Detect.class.getName()+" a where 1=1 ");
        
        if(null != parameter && !parameter.isEmpty()){
			String id = (String) parameter.get("id");
			if (StringUtils.isNotEmpty(id)) {
				coreHql.append(" and a.id = :id ");
				args.put("id", id);
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
	
	/**
	 * 鏁版嵁娣诲姞
	 * @param detail
	 * @throws Exception
	 */
	public void saveData(Detect detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setOid(UUID.randomUUID().toString());
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
	public void updateData(Detect detail) throws Exception {
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
	public void deleteData(Detect detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	
	public void builtDetect(Map<String, Object> parameter) throws ClassNotFoundException, G2AccessException{
		String hql = "from " + Detect.class.getName() + " a where 1=1 ";
        if(null != parameter && !parameter.isEmpty()){
			String id = (String) parameter.get("id");
			hql += "and a.id = '" + id + "'";
        }
        Collection<Detect> details = this.query(hql);
        for(Detect item : details){
        	String id = jianceDao.createJiance();
        	this.builtDatapoint(item.getDatapoint());
        	this.builtLrupoint(item.getId(),item.getType(),item.getDatapoint());
        	this.builtLRU(item, id);
        	this.builtLine(item, id);
        }
	}
	public void builtLrupoint(String id, String type, String datapoint) throws G2AccessException {
		String hql = "from " + Lru.class.getName()+" a where 1=1 and a.id = '" + id + "' and a.text = '" + type + "'";
		Collection<Lru> details = this.query(hql);
		for(Lru item : details){
			this.builtLrupoint(item,datapoint);
		}
	}
	public void builtLrupoint(Lru item, String datapoint) throws G2AccessException {
		// TODO Auto-generated method stub
			G2Connection g2_connection = null;
			try {
				g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
			} catch (G2AccessInitiationException e) {
				System.out.println("Problem connection to G2 exception was:"
						+ e.toString());
			}
			g2_connection.callRPC(
					Symbol.intern("SETPARAM"),
					new Object[] {Symbol.intern(item.getKe()+"LRU"),datapoint});
			
	}
	public void builtDatapoint(String Datapoint) throws G2AccessException {
		String hql = "from " + Attr.class.getName()+" a where 1=1 and a.a = '" + Datapoint + "'";
		Collection<Attr> details = this.query(hql);
		for(Attr item : details){
			this.builtDatapoint(item);
		}
	}
	public void builtDatapoint(Attr item) throws G2AccessException {
		// TODO Auto-generated method stub
			G2Connection g2_connection = null;
			try {
				g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
			} catch (G2AccessInitiationException e) {
				System.out.println("Problem connection to G2 exception was:"
						+ e.toString());
			}
			g2_connection.callRPC(
					Symbol.intern("BUILTDATAPOINT"),
					new Object[] {Symbol.intern(item.getA()),item.getZ(),item.getAa(),item.getE(),item.getC(),Float.parseFloat((item.getB().split(" "))[0]),item.getF(),Float.parseFloat(item.getAb()),Float.parseFloat(item.getAc())});
			
	}
	public void builtLRU(Detect detect, String id) throws G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		Item workspace = g2_connection.getItemWithUUID(id);
		KbWorkspace subws = (KbWorkspace) g2_connection.callRPC(
				Symbol.intern("GETSUBWS"), new Object[] { workspace });
		g2_connection.callRPC(
				Symbol.intern("SETJIANCE"),
				new Object[] { detect.getName(),
						Symbol.intern(detect.getType()), workspace });
		String hql = "from " + Detectlru.class.getName()
				+ " a where 1=1 and a.diagram = '" + detect.getDiagram() + "'";
		Collection<Detectlru> details = this.query(hql);
		for (Detectlru item : details) {
			if (item.getText().equals("数据输入")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item pointenter = g2_connection.createItem(Symbol
						.intern("GEDP-IO-PROPERTY-ENTRY-POINT-BLOCK"));
				((Entity) pointenter).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				pointenter.transferTo(subws, x, y);
				pointenter.makePermanent();
				g2_connection.callRPC(Symbol.intern("SETPOINT"),
						new Object[] { pointenter });
			}
			if (item.getText().equals("事件输出")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item eventpost = g2_connection.createItem(Symbol
						.intern("GEDP-IO-POST-CDG-EVENT-BLOCK"));
				((Entity) eventpost).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				eventpost.transferTo(subws, x, y);
				eventpost.makePermanent();
				g2_connection.callRPC(Symbol.intern("SETPOINTENTER"),
						new Object[] { eventpost, detect.getName() });
			}			
			if (item.getText().equals(">")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item greater = g2_connection.createItem(Symbol
						.intern("GEDP-GREATER-THAN-BLOCK"));
				((Entity) greater).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				greater.transferTo(subws, x, y);
				greater.makePermanent();
				g2_connection.callRPC(Symbol.intern("SETGREATER"),
						new Object[] { greater, Integer.parseInt(item.getPara()) });
			}
			if (item.getText().equals("<")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item less = g2_connection.createItem(Symbol
						.intern("GEDP-LESS-THAN-BLOCK"));
				((Entity) less).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				less.transferTo(subws, x, y);
				less.makePermanent();
				g2_connection.callRPC(Symbol.intern("SETLESS"),
						new Object[] { less, Integer.parseInt(item.getPara()) });
			}
			if (item.getText().equals("≤")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item lessorequal = g2_connection.createItem(Symbol
						.intern("GEDP-LESS-THAN-OR-EQUAL-TO-BLOCK"));
				((Entity) lessorequal).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				lessorequal.transferTo(subws, x, y);
				lessorequal.makePermanent();
				g2_connection.callRPC(Symbol.intern("SETLESSOREQUAL"),
						new Object[] { lessorequal, Integer.parseInt(item.getPara()) });
			}
			if (item.getText().equals("≥")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item greaterorequal = g2_connection.createItem(Symbol
						.intern("GEDP-GREATER-THAN-OR-EQUAL-TO-BLOCK"));
				((Entity) greaterorequal).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				greaterorequal.transferTo(subws, x, y);
				greaterorequal.makePermanent();
				g2_connection.callRPC(Symbol.intern("SETGREATEROREQUAL"),
						new Object[] { greaterorequal, Integer.parseInt(item.getPara()) });
			}
			if (item.getText().equals("+")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item add = g2_connection.createItem(Symbol
						.intern("GEDP-BIAS-BLOCK"));
				((Entity) add).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				add.transferTo(subws, x, y);
				add.makePermanent();
				g2_connection.callRPC(Symbol.intern("SETADD"),
						new Object[] { add, Float.parseFloat(item.getPara()) });
				
			}
			if (item.getText().equals("-")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item add = g2_connection.createItem(Symbol
						.intern("GEDP-BIAS-BLOCK"));
				((Entity) add).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				add.transferTo(subws, x, y);
				add.makePermanent();
				g2_connection.callRPC(Symbol.intern("SETADD"),
						new Object[] { add, -(Float.parseFloat(item.getPara())) });
				
			}
			if (item.getText().equals("绝对值")) {
				String[] arr = item.getLoc().split(" ");
 				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item absolute = g2_connection.createItem(Symbol
						.intern("GEDP-ABSOLUTE-VALUE-BLOCK"));
				((Entity) absolute).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				absolute.transferTo(subws, x, y);
				absolute.makePermanent();
			}
			if (item.getText().equals("倒数")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item inverse = g2_connection.createItem(Symbol
						.intern("GEDP-INVERSE-BLOCK"));
				((Entity) inverse).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				inverse.transferTo(subws, x, y);
				inverse.makePermanent();
			}
			if (item.getText().equals("相反数")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item changesign = g2_connection.createItem(Symbol
						.intern("GEDP-CHANGE-SIGN-BLOCK"));
				((Entity) changesign).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				changesign.transferTo(subws, x, y);
				changesign.makePermanent();
			}
			if (item.getText().equals("相加")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item summation = g2_connection.createItem(Symbol
						.intern("GEDP-SUMMATION-BLOCK"));
				((Entity) summation).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				summation.transferTo(subws, x, y);
				summation.makePermanent();
			}
			if (item.getText().equals("相乘")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item multiplitation = g2_connection.createItem(Symbol
						.intern("GEDP-MULTIPLICATION-BLOCK"));
				((Entity) multiplitation).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				multiplitation.transferTo(subws, x, y);
				multiplitation.makePermanent();
			}
			if (item.getText().equals("乘以")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item multiplitate = g2_connection.createItem(Symbol
						.intern("GEDP-GAIN-BLOCK"));
				((Entity) multiplitate).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				multiplitate.transferTo(subws, x, y);
				multiplitate.makePermanent();
				g2_connection.callRPC(Symbol.intern("SETMULTIPLICATE"),
						new Object[] { multiplitate, Float.parseFloat(item.getPara()) });
			}
			if (item.getText().equals("延迟")) {
				String[] arr = item.getLoc().split(" ");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				Item delay = g2_connection.createItem(Symbol
						.intern("GEDP-DATA-DELAY-BLOCK"));
				((Entity) delay).setNames(Symbol.intern(item.getOther()+detect.getDiagram()));
				delay.transferTo(subws, x, y);
				delay.makePermanent();
				g2_connection.callRPC(Symbol.intern("SETDELAY"),
						new Object[] { delay, Float.parseFloat(item.getPara()) });
			}
			
			
			
			
			
			
			
		}
	}
	public void builtLine(Detect detect, String id) throws G2AccessException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		String hql = "from " + Detectline.class.getName()
				+ " a where 1=1 and a.diagram = '" + detect.getDiagram() + "'";
		Collection<Detectline> details = this.query(hql);
		for(Detectline item : details){
			g2_connection.callRPC(Symbol.intern("CONNECTDLINE"),
					new Object[] {Symbol.intern(item.getFromid() + detect.getDiagram()),Symbol.intern(item.getToid()+detect.getDiagram())});
		}
		
	}
	public Collection<Detect> queryDetectOnTfaults(Collection<Tfaults> details) throws Exception {
		
	   return null;
	}
        
}