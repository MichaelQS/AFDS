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
@Table(name="moxing_detectline")
public class  Detectline implements Serializable {
	private static final long serialVersionUID = 1L;
    public Detectline(){}
   public Detectline(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String toid ;
	private String diagram ;
	private String fromid ;



	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="toid")
	public String getToid() {
		return toid;
	}
	public void setToid(String toid) {
		this.toid=toid;
	}
	@Column(name="diagram")
	public String getDiagram() {
		return diagram;
	}
	public void setDiagram(String diagram) {
		this.diagram=diagram;
	}
	@Column(name="fromid")
	public String getFromid() {
		return fromid;
	}
	public void setFromid(String fromid) {
		this.fromid=fromid;
	}


}