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
@Table(name="physics_lei")
public class  Lei implements Serializable {
	private static final long serialVersionUID = 1L;
    public Lei(){}
   public Lei(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private Systems  system ;
	private String lei ;
	private String other ;
	private String icon ;
	private String input ;
	private String output ;
	private Collection<Attr> attr;



	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="lei")
	public String getLei() {
		return lei;
	}
	public void setLei(String lei) {
		this.lei=lei;
	}
	@Column(name="other")
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other=other;
	}
	@Column(name="icon")
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon=icon;
	}
	@Column(name="input")
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	@Column(name="output")
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "lei")
	public Collection<Attr> getAttr() {
		return attr;
	}
	public void setAttr(Collection<Attr> attr) {
		this.attr = attr;
	}
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "system")
	public Systems getSystem() {
		return system;
	}
	public void setSystem(Systems system) {
		this.system = system;
	}
	

}