package com.charge.ev.entries;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "slot_booking")
public class Reservation {
	@Id
    @Column
 	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "Rid_gen")
 	@SequenceGenerator(name = "Rid_gen",initialValue = 1,allocationSize = 1,sequenceName = "Ridgen")
 	private long rid;
	
	@Column(nullable = false)
    private String cbrand;
	
	@Column(nullable = false)
    private String vendorid;
	
	@Column(nullable = false)
    private String slotType;
    
	@Column(nullable = false)
    private String cmodel;
	
	@Column(nullable = false)
    private double bstatus;
	
	@Column(nullable = false)
    private LocalDateTime sdatet;
	
	@Column(nullable = false)
    private LocalDateTime edatet;
	
	@Column(nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private String status;

	public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}

	public String getCbrand() {
		return cbrand;
	}

	public void setCbrand(String cbrand) {
		this.cbrand = cbrand;
	}

	public String getCmodel() {
		return cmodel;
	}

	public void setCmodel(String cmodel) {
		this.cmodel = cmodel;
	}

	public double getBstatus() {
		return bstatus;
	}

	public void setBstatus(double bstatus) {
		this.bstatus = bstatus;
	}

	public LocalDateTime getSdatet() {
		return sdatet;
	}

	public void setSdatet(LocalDateTime sdatet) {
		this.sdatet = sdatet;
	}

	public LocalDateTime getEdatet() {
		return edatet;
	}

	public void setEdatet(LocalDateTime edatet) {
		this.edatet = edatet;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Reservation [rid=" + rid + ", cbrand=" + cbrand + ", cmodel=" + cmodel + ", bstatus=" + bstatus
				+ ", sdatet=" + sdatet + ", edatet=" + edatet + ", paymentType=" + paymentType + ", status=" + status
				+ "]";
	}
	
}
