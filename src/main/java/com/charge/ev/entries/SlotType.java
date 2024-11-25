package com.charge.ev.entries;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="slot_type")
public class SlotType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private int id;
	
	@ManyToOne
	@JoinColumn(name="vendori")
	private VendorDetails vendori;
	
	@Column
	private String slotType;
	
	public VendorDetails getVendori() {
		return vendori;
	}
	public void setVendori(VendorDetails vendori) {
		this.vendori = vendori;
	}
	public String getSlotType() {
		return slotType;
	}
	public void setSlotType(String slotType) {
		this.slotType = slotType;
	}
	@Override
	public String toString() {
		return "SlotType [vendori=" + vendori + ", slotType="+slotType+"]";
	}
}
