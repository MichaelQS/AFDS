package com.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="moxing_physics")
public class  Physics implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String  oid ;
	private String  localsituation ;
	private String  appearance ;
	private Double  x ;
	private String  feature ;
	private String  output ;
	private Double  y ;
	private String  system ;
	private String  input ;
	private Double  z ;
	private String  lru ;
	private String  origin ;

	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}
	@Column(name = "localsituation")
	public String getLocalsituation() {
		return localsituation;
	}
	public void setLocalsituation(String localsituation) {
		this.localsituation=localsituation;
	}
	@Column(name = "appearance")
	public String getAppearance() {
		return appearance;
	}
	public void setAppearance(String appearance) {
		this.appearance=appearance;
	}
	@Column(name = "x")
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x=x;
	}
	@Column(name = "feature")
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature=feature;
	}
	@Column(name = "output")
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output=output;
	}
	@Column(name = "y")
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y=y;
	}
	@Column(name = "system")
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system=system;
	}
	@Column(name = "input")
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input=input;
	}
	@Column(name = "z")
	public Double getZ() {
		return z;
	}
	public void setZ(Double z) {
		this.z=z;
	}
	@Column(name = "lru")
	public String getLru() {
		return lru;
	}
	public void setLru(String lru) {
		this.lru=lru;
	}
	@Column(name = "origin")
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	
	}
