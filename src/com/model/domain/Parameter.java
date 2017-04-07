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
@Table(name="moxing_parameter")
public class  Parameter implements Serializable {
	private static final long serialVersionUID = 1L;
    public Parameter(){}


	private Integer no ;
	private String roadid ;
	private String classes ;
	private String origin ;




	@Column(name="no")
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no=no;
	}
	@Id
	@Column(name = "roadid", unique = true, nullable = false)
	public String getRoadid() {
		return roadid;
	}
	public void setRoadid(String roadid) {
		this.roadid=roadid;
	}
	@Column(name="origin")
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	@Column(name="classes")
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	
	


}