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
@Table(name="moxing_jixing")
public class  Jixing implements Serializable {
	private static final long serialVersionUID = 1L;
    public Jixing(){}
   public Jixing(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String other ;
	private String jixing ;

	private Collection<Systems> sy ;

	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}
	@Column(name="other")
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	@Column(name="jixing")
	public String getJixing() {
		return jixing;
	}
	public void setJixing(String jixing) {
		this.jixing=jixing;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "jixing")
	public Collection<Systems> getSy() {
		return sy;
	}
	public void setSy(Collection<Systems> sy) {
		this.sy = sy;
	}


}