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
@Table(name="moxing_repairresult")
public class  Repairresult implements Serializable {
	private static final long serialVersionUID = 1L;
    public Repairresult(){}
   public Repairresult(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String method ;
	private String tool ;
	private String event ;
	private String location ;
	private String id ;



	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="method")
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method=method;
	}
	@Column(name="tool")
	public String getTool() {
		return tool;
	}
	public void setTool(String tool) {
		this.tool=tool;
	}
	@Column(name="event")
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event=event;
	}
	@Column(name="location")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location=location;
	}
	@Column(name="id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


}