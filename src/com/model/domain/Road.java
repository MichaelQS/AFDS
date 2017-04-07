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

import java.util.Date;

@Entity
@Table(name="fault_road")
public class  Road implements Serializable {
	private static final long serialVersionUID = 1L;
    public Road(){}


	private Integer frequency ;
	private Date time ;
	private String number ;
	private String address ;
	private String fuser ;
	private String feature ;
	private String system ;
	private String roadid ;
	private String id ;
	private Collection<Faults> fault;




	@Column(name="frequency")
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency=frequency;
	}
	@Column(name="time")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time=time;
	}
	@Column(name="number")
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number=number;
	}
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	@Column(name="fuser")
	public String getFuser() {
		return fuser;
	}
	public void setFuser(String fuser) {
		this.fuser=fuser;
	}
	@Id
	@Column(name = "roadid", unique = true, nullable = false)
	public String getRoadid() {
		return roadid;
	}
	public void setRoadid(String roadid) {
		this.roadid=roadid;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "roadid")
	public Collection<Faults> getFault() {
		return fault;
	}
	public void setFault(Collection<Faults> fault) {
		this.fault = fault;
	}
	@Column(name="system")
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system=system;
	}
	@Column(name="feature")
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature=feature;
	}
	@Column(name="id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}