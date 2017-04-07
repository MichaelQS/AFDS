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
@Table(name="moxing_classes")
public class  Classes implements Serializable {
	private static final long serialVersionUID = 1L;
    public Classes(){}
   public Classes(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String system ;
	private String icon ;
	private String classes ;



	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="system")
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system=system;
	}
	@Column(name="icon")
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon=icon;
	}
	@Column(name="classes")
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes=classes;
	}


}