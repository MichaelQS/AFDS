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
@Table(name="moxing_param")
public class  Param implements Serializable {
	private static final long serialVersionUID = 1L;
    public Param(){}
   public Param(Integer oid) {
      this.oid=oid;
 	}	

	private Integer oid ;

	private String e ;
	private String p ;
	private String i ;
	private String l ;
	private String u ;
	private String v ;
	private String n ;
	private String g ;
	private String h ;
	private String y ;
	private String t ;
	private String r ;
	private String j ;
	private String s ;
	private String w ;
	private String z ;
	private String c ;
	private String o ;
	private String x ;
	private String f ;
	private String a ;
	private String d ;
	private String m ;
	private String b ;
	private String k ;
	private String q ;



	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid=oid;
	}

	@Column(name="E")
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e=e;
	}
	@Column(name="P")
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p=p;
	}
	@Column(name="I")
	public String getI() {
		return i;
	}
	public void setI(String i) {
		this.i=i;
	}
	@Column(name="L")
	public String getL() {
		return l;
	}
	public void setL(String l) {
		this.l=l;
	}
	@Column(name="U")
	public String getU() {
		return u;
	}
	public void setU(String u) {
		this.u=u;
	}
	@Column(name="V")
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v=v;
	}
	@Column(name="N")
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n=n;
	}
	@Column(name="G")
	public String getG() {
		return g;
	}
	public void setG(String g) {
		this.g=g;
	}
	@Column(name="H")
	public String getH() {
		return h;
	}
	public void setH(String h) {
		this.h=h;
	}
	@Column(name="Y")
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y=y;
	}
	@Column(name="T")
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t=t;
	}
	@Column(name="R")
	public String getR() {
		return r;
	}
	public void setR(String r) {
		this.r=r;
	}
	@Column(name="J")
	public String getJ() {
		return j;
	}
	public void setJ(String j) {
		this.j=j;
	}
	@Column(name="S")
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s=s;
	}
	@Column(name="W")
	public String getW() {
		return w;
	}
	public void setW(String w) {
		this.w=w;
	}
	@Column(name="Z")
	public String getZ() {
		return z;
	}
	public void setZ(String z) {
		this.z=z;
	}
	@Column(name="C")
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c=c;
	}
	@Column(name="O")
	public String getO() {
		return o;
	}
	public void setO(String o) {
		this.o=o;
	}
	@Column(name="X")
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x=x;
	}
	@Column(name="F")
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f=f;
	}
	@Column(name="A")
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a=a;
	}
	@Column(name="D")
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d=d;
	}
	@Column(name="M")
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m=m;
	}
	@Column(name="B")
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b=b;
	}
	@Column(name="K")
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k=k;
	}
	@Column(name="Q")
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q=q;
	}


}