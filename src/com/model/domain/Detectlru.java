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
@Table(name="moxing_detectlru")
public class  Detectlru implements Serializable {
	private static final long serialVersionUID = 1L;
    public Detectlru(){}
   public Detectlru(String oid) {
      this.oid=oid;
 	}	

	private String oid ;

	private String text ;
	private String para ;
	private String other ;
	private String diagram ;
	private String color ;
	private String line ;
	private String figure ;
	private String loc ;



	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid=oid;
	}

	@Column(name="text")
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text=text;
	}
	@Column(name="para")
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para=para;
	}
	@Column(name="other")
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other=other;
	}
	@Column(name="diagram")
	public String getDiagram() {
		return diagram;
	}
	public void setDiagram(String diagram) {
		this.diagram=diagram;
	}
	@Column(name="color")
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Column(name="line")
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	@Column(name="figure")
	public String getFigure() {
		return figure;
	}
	public void setFigure(String figure) {
		this.figure = figure;
	}
	@Column(name="loc")
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	


}