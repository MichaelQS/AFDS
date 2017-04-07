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
@Table(name="moxing_tfaults")
public class  Tfaults implements Serializable {
	private static final long serialVersionUID = 1L;
    public Tfaults(){}


	
    private String origin ;
	private String no ;
	private String classes ;
	private String event ;
	private String roadid ;




	@Column(name="origin")
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	@Column(name = "no")
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no=no;
	}
	@Column(name="classes")
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes=classes;
	}
	@Column(name="event")
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event=event;
	}
	@Id
	@Column(name = "roadid", unique = true, nullable = false)
	public String getRoadid() {
		return roadid;
	}
	public void setRoadid(String roadid) {
		this.roadid=roadid;
	}


}