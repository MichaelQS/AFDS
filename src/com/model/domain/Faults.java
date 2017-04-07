package com.model.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="moxing_faults")
public class  Faults implements Serializable {
	private static final long serialVersionUID = 1L;
    public Faults(){}
   public Faults(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String classes ;
	private String feature ;
	private String parentnode ;
	private String event ;
	private String system ;
	private String road ;
	private String origin ;
	private Road roadid;


	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="classes")
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes=classes;
	}
	@Column(name="feature")
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature=feature;
	}
	@Column(name="parentnode")
	public String getParentnode() {
		return parentnode;
	}
	public void setParentnode(String parentnode) {
		this.parentnode=parentnode;
	}
	@Column(name="event")
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event=event;
	}
	@Column(name="system")
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system=system;
	}
	@Column(name="road")
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road=road;
	}
	@Column(name="origin")
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "roadid")
	public Road getRoadid() {
		return roadid;
	}
	public void setRoadid(Road roadid) {
		this.roadid = roadid;
	}


}