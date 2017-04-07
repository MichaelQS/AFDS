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
@Table(name="moxing_attr")
public class  Attr implements Serializable {
	private static final long serialVersionUID = 1L;
    public Attr(){}
   public Attr(Integer oid) {
      this.oid=oid;
 	}	

	private Integer oid ;

	private String c ;
	private String t ;
	private String b ;
	private String z ;
	private String o ;
	private String m ;
	private String a ;
	private String f ;
	private String s ;
	private String h ;
	private String j ;
	private String u ;
	private String d ;
	private String ac ;
	private String g ;
	private String p ;
	private String q ;
	private String e ;
	private String x ;
	private String r ;
	private String l ;
	private String y ;
	private String w ;
	private String aa ;
	private String ab ;
	private String i ;
	private String k ;
	private String v ;
	private String n ;
	private Lei lei;


	@Id
	@Column(name = "oid", unique = true, nullable = false)
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid=oid;
	}

	@Column(name="C")
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c=c;
	}
	@Column(name="T")
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t=t;
	}
	@Column(name="B")
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b=b;
	}
	@Column(name="Z")
	public String getZ() {
		return z;
	}
	public void setZ(String z) {
		this.z=z;
	}
	@Column(name="O")
	public String getO() {
		return o;
	}
	public void setO(String o) {
		this.o=o;
	}
	@Column(name="M")
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m=m;
	}
	@Column(name="A")
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a=a;
	}
	@Column(name="F")
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f=f;
	}
	@Column(name="S")
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s=s;
	}
	@Column(name="H")
	public String getH() {
		return h;
	}
	public void setH(String h) {
		this.h=h;
	}
	@Column(name="J")
	public String getJ() {
		return j;
	}
	public void setJ(String j) {
		this.j=j;
	}
	@Column(name="U")
	public String getU() {
		return u;
	}
	public void setU(String u) {
		this.u=u;
	}
	@Column(name="D")
	public String getD() {
		return d;
	}
	public void setD(String d) {
		this.d=d;
	}
	@Column(name="AC")
	public String getAc() {
		return ac;
	}
	public void setAc(String ac) {
		this.ac=ac;
	}
	@Column(name="G")
	public String getG() {
		return g;
	}
	public void setG(String g) {
		this.g=g;
	}
	@Column(name="P")
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p=p;
	}
	@Column(name="Q")
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q=q;
	}
	@Column(name="E")
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e=e;
	}
	@Column(name="X")
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x=x;
	}
	@Column(name="R")
	public String getR() {
		return r;
	}
	public void setR(String r) {
		this.r=r;
	}
	@Column(name="L")
	public String getL() {
		return l;
	}
	public void setL(String l) {
		this.l=l;
	}
	@Column(name="Y")
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y=y;
	}
	@Column(name="W")
	public String getW() {
		return w;
	}
	public void setW(String w) {
		this.w=w;
	}
	@Column(name="AA")
	public String getAa() {
		return aa;
	}
	public void setAa(String aa) {
		this.aa=aa;
	}
	@Column(name="AB")
	public String getAb() {
		return ab;
	}
	public void setAb(String ab) {
		this.ab=ab;
	}
	@Column(name="I")
	public String getI() {
		return i;
	}
	public void setI(String i) {
		this.i=i;
	}
	@Column(name="K")
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k=k;
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
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "lei")
	public Lei getLei() {
		return lei;
	}
	public void setLei(Lei lei) {
		this.lei = lei;
	}
}