package com.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="physics_line")
public class  Line implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String  oid ;
	private String  line ;
	private String  tol ;
	private String  froml ;
	private String  toport ;
	private String  fromport ;
	private String  system ;
	private String  feature;
	private String  points ;
	private String  icon ;
	private String  id ;
	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}
	@Column(name = "line")
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line=line;
	}
	@Column(name = "tol")
	public String getTol() {
		return tol;
	}
	public void setTol(String tol) {
		this.tol=tol;
	}
	@Column(name = "froml")
	public String getFroml() {
		return froml;
	}
	public void setFroml(String froml) {
		this.froml=froml;
	}
	@Column(name = "toport")
	public String getToport() {
		return toport;
	}
	public void setToport(String toport) {
		this.toport=toport;
	}
	@Column(name = "fromport")
	public String getFromport() {
		return fromport;
	}
	public void setFromport(String fromport) {
		this.fromport=fromport;
	}
	@Column(name = "system")
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	@Column(name = "feature")
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	@Column(name = "points")
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	@Column(name = "icon")
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(name = "id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
