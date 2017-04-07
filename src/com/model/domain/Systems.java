package com.model.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;






@Entity
@Table(name="moxing_systems")
public class  Systems implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String  oid ;
	private String  system ;
	private String  other ;
	private String  background ;
	private Jixing jixing;
	private Collection<Feature> ft;
	private Collection<Lei> lei;
	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}
	@Column(name = "system")
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "system")
	public Collection<Feature> getFt() {
		return ft;
	}

	public void setFt(Collection<Feature> ft) {
		this.ft = ft;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "system")
	public Collection<Lei> getLei() {
		return lei;
	}
	public void setLei(Collection<Lei> lei) {
		this.lei = lei;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "jixing")
	public Jixing getJixing() {
		return jixing;
	}
	public void setJixing(Jixing jixing) {
		this.jixing = jixing;
	}	
	}
