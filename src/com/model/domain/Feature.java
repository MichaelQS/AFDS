package com.model.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;




@Entity
@Table(name="moxing_feature")
public class  Feature implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String  oid ;
	private String  feature ;
	private Systems  system ;
	private String  other ;
	private String  background ;

	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "system")
	public Systems getSystem() {
		return system;
	}
	public void setSystem(Systems system) {
		this.system = system;
	}
	@Column(name = "feature")
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	@Column(name = "other")
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	@Column(name = "background")
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	
	
		
	}
