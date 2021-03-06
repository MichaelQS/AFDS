package com.model.classes.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.kabeja.dxf.DXFArc;
import org.kabeja.dxf.DXFCircle;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFEllipse;
import org.kabeja.dxf.DXFHatch;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLine;
import org.kabeja.dxf.DXFPolyline;
import org.kabeja.dxf.helpers.HatchBoundaryLoop;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;

import com.bstek.bdf2.core.orm.ParseResult;
import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;
import com.common.HibernateBaseDao;
import com.gensym.classes.ClassDefinition;
import com.gensym.classes.Entity;
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

import com.model.domain.Classes;

@Repository("classesDao")
public class ClassesDao extends HibernateBaseDao {

	/**
	 * 同时也支持普通类型查询，在数据类型和日期类型支持区间查询
	 * 
	 * @param page
	 * @param parameter
	 * @param criteria
	 * @throws Exception
	 */
	public void queryClasses(Page<Classes> page, Map<String, Object> parameter,Criteria criteria) throws Exception {
        Map<String, Object> args = new HashMap<String,Object>();
        StringBuffer coreHql = new StringBuffer("from " + Classes.class.getName()+" a where 1=1 ");
        
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
	 * 数据添加
	 * @param detail
	 * @throws Exception
	 */
	@Expose
	public void saveData(Classes detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			detail.setOid(this.createClasses(detail.getClasses(), detail.getIcon()));
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
	@Expose
	public void updateData(Classes detail) throws Exception {
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
	@Expose
	public void deleteData(Classes detail) throws Exception {
		Session session = this.getSessionFactory().openSession();
		try {
			session.delete(detail);
		} finally {
			session.flush();
			session.close();
		}
	}
	/**
	 * G2��������ɷ���
	 * 
	 * @author snow_fly
	 * @param name
	 * @param icon
	 */
	@Expose
	public static String createClasses(String lei, String icon) throws ClassNotFoundException,
			G2AccessException, ParseException {
		// TODO Auto-generated method stub
		G2Connection g2_connection = null;
		try {
			g2_connection = G2Gateway.getOrMakeConnection("localhost", "1111");
		} catch (G2AccessInitiationException e) {
			System.out.println("Problem connection to G2 exception was:"
					+ e.toString());
		}
		KbWorkspace ws = (KbWorkspace) g2_connection
				.getItemWithUUID("86fbe857c3da11e4bb5e0021ccc83f7d");
		ClassDefinition classes = (ClassDefinition) g2_connection
				.createItem(Symbol.intern("CLASS-DEFINITION"));
		classes.setClassName(Symbol.intern(lei));
		Sequence s = new Sequence();
		s.add(Symbol.intern("ɲ��ϵͳ"));
		classes.setDirectSuperiorClasses(s);
		classes.transferTo(ws, 0, 0);
		classes.makePermanent();
		Object text = g2_connection.callRPC(Symbol.intern("GETUUID"),
				new Object[] { classes });
		String uuid = text.toString();
		String id = uuid.substring(uuid.indexOf("\"") + 1, uuid.length() - 1);
//		Structure icondescription = this.builtIcon(icon);
//		classes.setIconDescription(icondescription);
		return id;
	}
	
	public Structure builtIcon(String icon) throws ParseException{
		Structure s = new Structure();
		s.addAttribute(Symbol.intern("WIDTH"), 50);
		s.addAttribute(Symbol.intern("HEIGHT"), 50);
		Sequence regions = new Sequence();
		Structure region = new Structure();
		region.addAttribute(Symbol.intern("REGION-NAME"), Symbol.intern("R-SHACHE"));
		region.addAttribute(Symbol.intern("COLOR-NAME"), Symbol.intern("YELLOW"));
		regions.add(region);
		s.addAttribute(Symbol.intern("REGIONS"), regions);
		Sequence layers = new Sequence();
		Structure layerregionorcolor = new Structure();
		layerregionorcolor.addAttribute(Symbol.intern("REGION-OR-COLOR"), Symbol.intern("R-SHACHE"));
		Sequence elements = new Sequence();
		Parser parser = ParserBuilder.createDefaultParser();
		try{
			File f = new File("F://workplace//AFDS//web//dxf//" + icon);
			parser.parse(new FileInputStream(f), DXFParser.DEFAULT_ENCODING);
			DXFDocument doc = parser.getDocument();
			DXFLayer layer = doc.getDXFLayer("LEGENDE");
			List hatchs = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_HATCH);
			if(hatchs !=null){
			for(int i = 0; i < hatchs.size(); i++){
				DXFHatch hatch = (DXFHatch)hatchs.get(i);
				int color = hatch.getColor();
				String colour = this.change(color);
				Structure layercolor = new Structure();
				Sequence elements0 = new Sequence();
				layercolor.addAttribute(Symbol.intern("REGION-OR-COLOR"), Symbol.intern(colour));
				Iterator iterator = hatch.getBoundaryLoops();
				while(iterator.hasNext()){
					HatchBoundaryLoop loops = (HatchBoundaryLoop) iterator.next();
					Iterator iter = loops.getBoundaryEdgesIterator();
					while(iter.hasNext()){
						Object b =  iter.next();
						if(b.getClass() == DXFEllipse.class){
							DXFEllipse c = (DXFEllipse)b;
							int x = -Integer.parseInt(new java.text.DecimalFormat("0").format(c.getCenterPoint().getX()));
							int y = Integer.parseInt(new java.text.DecimalFormat("0").format(c.getCenterPoint().getY()));
							int r = Integer.parseInt(new java.text.DecimalFormat("0").format(c.getMajorAxisDirection().getY()));
							Structure layercircle = new Structure();
							Sequence points = new Sequence();
							Structure point1 = new Structure();
							point1.addAttribute(Symbol.intern("X"), x+r);
							point1.addAttribute(Symbol.intern("Y"), -y);
							Structure point2 = new Structure();
							point2.addAttribute(Symbol.intern("X"), x-r);
							point2.addAttribute(Symbol.intern("Y"), -y);
							Structure point3 = new Structure();
							point3.addAttribute(Symbol.intern("X"), x);
							point3.addAttribute(Symbol.intern("Y"), -(y+r));
							points.add(point1);
							points.add(point2);
							points.add(point3);
							layercircle.addAttribute(Symbol.intern("TYPE"), Symbol.intern("FILLED-CIRCLE"));
							layercircle.addAttribute(Symbol.intern("POINTS"), points);
							elements0.add(layercircle);
							System.out.println(colour + ": filled circle"+ "("+ (x+r) + "," + -y + ")" + "("+ (x-r) + "," + -y + ")" + "("+ x + "," + -(y+r) + ");");
						}
						if(b.getClass() == DXFArc.class){
							DXFArc c = (DXFArc)b;
							int x = -Integer.parseInt(new java.text.DecimalFormat("0").format(c.getCenterPoint().getX()));
							int y = Integer.parseInt(new java.text.DecimalFormat("0").format(c.getCenterPoint().getY()));
							int r = Integer.parseInt(new java.text.DecimalFormat("0").format(c.getRadius()));
							Structure layercircle = new Structure();
							Sequence points = new Sequence();
							Structure point1 = new Structure();
							point1.addAttribute(Symbol.intern("X"), x+r);
							point1.addAttribute(Symbol.intern("Y"), -y);
							Structure point2 = new Structure();
							point2.addAttribute(Symbol.intern("X"), x-r);
							point2.addAttribute(Symbol.intern("Y"), -y);
							Structure point3 = new Structure();
							point3.addAttribute(Symbol.intern("X"), x);
							point3.addAttribute(Symbol.intern("Y"), -(y+r));
							points.add(point1);
							points.add(point2);
							points.add(point3);
							layercircle.addAttribute(Symbol.intern("TYPE"), Symbol.intern("FILLED-CIRCLE"));
							layercircle.addAttribute(Symbol.intern("POINTS"), points);
							elements0.add(layercircle);
							System.out.println(colour + ": filled circle"+ "("+ (x+r) + "," + -y + ")" + "("+ (x-r) + "," + -y + ")" + "("+ x + "," + -(y+r) + ");");
						}
						if(b.getClass() == DXFPolyline.class){
							DXFPolyline p = (DXFPolyline)b;
							Structure layerline = new Structure();
							Sequence points = new Sequence();
							String gql = colour + ": filled polygon";
							for(int j = 0; j < p.getVertexCount(); j++){
								int x = Integer.parseInt(new java.text.DecimalFormat("0").format(p.getVertex(j).getX()));
								int y = Integer.parseInt(new java.text.DecimalFormat("0").format(p.getVertex(j).getY()));
								Structure point = new Structure();		
								point.addAttribute(Symbol.intern("X"), -x);
								point.addAttribute(Symbol.intern("Y"), -y);
								points.add(point);
								gql += "(" + -x + "," + -y + ")";
							}
							layerline.addAttribute(Symbol.intern("TYPE"), Symbol.intern("FILLED-POLYGON"));
							layerline.addAttribute(Symbol.intern("POINTS"), points);
							elements0.add(layerline);
							System.out.println(gql+";");
						}
					}
					}
			layercolor.addAttribute(Symbol.intern("LINE-DRAWING-ELEMENTS"), elements0);
			layers.add(layercolor);
			}}
			List circles = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_CIRCLE);
			if(circles !=null){
			for(int i = 0; i < circles.size(); i++){
				DXFCircle circle = (DXFCircle)circles.get(i);
				int x = Integer.parseInt(new java.text.DecimalFormat("0").format(circle.getCenterPoint().getX()));
				int y = Integer.parseInt(new java.text.DecimalFormat("0").format(circle.getCenterPoint().getY()));
				int r = Integer.parseInt(new java.text.DecimalFormat("0").format(circle.getRadius()));
				Structure layercircle = new Structure();
				Sequence points = new Sequence();
				Structure point1 = new Structure();
				point1.addAttribute(Symbol.intern("X"), -(x+r));
				point1.addAttribute(Symbol.intern("Y"), -y);
				Structure point2 = new Structure();
				point2.addAttribute(Symbol.intern("X"), -(x-r));
				point2.addAttribute(Symbol.intern("Y"), -y);
				Structure point3 = new Structure();
				point3.addAttribute(Symbol.intern("X"), -x);
				point3.addAttribute(Symbol.intern("Y"), -(y+r));
				points.add(point1);
				points.add(point2);
				points.add(point3);
				layercircle.addAttribute(Symbol.intern("TYPE"), Symbol.intern("CIRCLE"));
				layercircle.addAttribute(Symbol.intern("POINTS"), points);
				elements.add(layercircle);
				System.out.println("circle"+ "("+ (x+r) + "," + -y + ")" + "("+ (x-r) + "," + -y + ")" + "("+ x + "," + -(y+r) + ");");
			}}
			List lines = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_LINE);
			if(lines !=null){
			for(int i = 0; i < lines.size(); i++){
				DXFLine line = (DXFLine)lines.get(i);
				String gql = "lines";
				int sx = Integer.parseInt(new java.text.DecimalFormat("0").format(line.getStartPoint().getX()));
				int sy = Integer.parseInt(new java.text.DecimalFormat("0").format(line.getStartPoint().getY()));
				int ex = Integer.parseInt(new java.text.DecimalFormat("0").format(line.getEndPoint().getX()));
				int ey = Integer.parseInt(new java.text.DecimalFormat("0").format(line.getEndPoint().getY()));
				gql += "(" + sx + "," + -sy +")" + "(" + ex + "," + -ey + ")";
				Structure layerline = new Structure();
				Sequence points = new Sequence();
				Structure point1 = new Structure();
				point1.addAttribute(Symbol.intern("X"), sx);
				point1.addAttribute(Symbol.intern("Y"), -sy);
				Structure point2 = new Structure();
				point2.addAttribute(Symbol.intern("X"), ex);
				point2.addAttribute(Symbol.intern("Y"), -ey);
				points.add(point1);
				points.add(point2);
				layerline.addAttribute(Symbol.intern("TYPE"), Symbol.intern("LINES"));
				layerline.addAttribute(Symbol.intern("POINTS"), points);
				elements.add(layerline);
				System.out.println(gql+";");
			}}
			List arcs = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_ARC);
			if(arcs !=null){
			for(int i = 0; i < arcs.size(); i++){
				DXFArc arc = (DXFArc)arcs.get(i);
				String gql = "lines";
				int sx = Integer.parseInt(new java.text.DecimalFormat("0").format(arc.getStartPoint().getX()));
				int sy = Integer.parseInt(new java.text.DecimalFormat("0").format(arc.getStartPoint().getY()));
				int ex = Integer.parseInt(new java.text.DecimalFormat("0").format(arc.getEndPoint().getX()));
				int ey = Integer.parseInt(new java.text.DecimalFormat("0").format(arc.getEndPoint().getY()));
				double angle;
				if(arc.getStartAngle()>arc.getEndAngle()){
					if(arc.isCounterClockwise()){
						angle = (arc.getStartAngle()+arc.getEndAngle())/2;
					}else{
						angle = (arc.getStartAngle()+arc.getEndAngle())/2+180;
					}
				}else{
					if(arc.isCounterClockwise()){
						angle = (arc.getStartAngle()+arc.getEndAngle())/2+180;
					}else{
						angle = (arc.getStartAngle()+arc.getEndAngle())/2;
					}
				}
				int x = Integer.parseInt(new java.text.DecimalFormat("0").format(arc.getPointAt(angle).getX()));
				int y = Integer.parseInt(new java.text.DecimalFormat("0").format(arc.getPointAt(angle).getY()));
				gql += "(" + sx + "," + -sy + ")" + "arc(" + x + "," + -y +")" + "(" + ex + "," + -ey + ")";
				Structure layerline = new Structure();
				Sequence points = new Sequence();
				Structure point1 = new Structure();
				point1.addAttribute(Symbol.intern("X"), sx);
				point1.addAttribute(Symbol.intern("Y"), -sy);
				Structure point2 = new Structure();		
				point2.addAttribute(Symbol.intern("X"), ex);
				point2.addAttribute(Symbol.intern("Y"), -ey);
				Structure point3 = new Structure();
				point3.addAttribute(Symbol.intern("X"), ex);
				point3.addAttribute(Symbol.intern("Y"), -ey);
				points.add(point1);
				points.add(point2);
				points.add(point3);
				layerline.addAttribute(Symbol.intern("TYPE"), Symbol.intern("LINES"));
				layerline.addAttribute(Symbol.intern("POINTS"), points);
				elements.add(layerline);
				System.out.println(gql+";");
			}}
			layerregionorcolor.addAttribute(Symbol.intern("LINE-DRAWING-ELEMENTS"), elements);
			layers.add(layerregionorcolor);

		}catch(IOException e){
			e.printStackTrace();
		}
		s.addAttribute(Symbol.intern("LAYERS"), layers);
		return s;
	}

	private String change(int color) {
		// TODO Auto-generated method stub
		switch (color) {
        case 1:
            return "RED";
        case 2:
        	return "YELLOW";
        case 3:
        	return "GREEN";
        case 4:
        	return "CYAN";
        case 5:
            return "BLUE";
        case 6:
        	return "MAGENTA";
        case 7:
        	return "BLACK";
        case 8:
        	return "GRAY";
        case 9:
        	return "SMOKE";	
        default:
        	return "WHITE";
        }
		
	}
        
}