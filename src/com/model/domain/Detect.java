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
@Table(name="moxing_detect")
public class  Detect implements Serializable {
	private static final long serialVersionUID = 1L;
    public Detect(){}
   public Detect(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String diagram ;
	private String type ;
	private String id ;
	private String name ;
	private String datapoint ;



	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="diagram")
	public String getDiagram() {
		return diagram;
	}
	public void setDiagram(String diagram) {
		this.diagram=diagram;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type=type;
	}
	@Column(name="id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	@Column(name="datapoint")
	public String getDatapoint() {
		return datapoint;
	}
	public void setDatapoint(String datapoint) {
		this.datapoint = datapoint;
	}


}