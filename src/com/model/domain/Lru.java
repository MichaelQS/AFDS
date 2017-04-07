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
@Table(name="physics_lru")
public class  Lru implements Serializable {
	private static final long serialVersionUID = 1L;
    public Lru(){}
   public Lru(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String loc ;
	private String source ;
	private String text ;
	private String system ;
	private String feature ;
	private String ke ;
	private String input ;
	private String output ;
	private String id ;



	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="loc")
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc=loc;
	}
	@Column(name="source")
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source=source;
	}
	@Column(name="text")
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text=text;
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
	@Column(name="ke")
	public String getKe() {
		return ke;
	}
	public void setKe(String ke) {
		this.ke=ke;
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
	@Column(name="id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


}