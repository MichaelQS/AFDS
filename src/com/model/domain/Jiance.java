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
@Table(name="moxing_jiance")
public class  Jiance implements Serializable {
	private static final long serialVersionUID = 1L;
    public Jiance(){}
   public Jiance(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String event ;
	private String classes ;



	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="event")
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event=event;
	}
	@Column(name="classes")
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes=classes;
	}


}