package com.charge.ev.entries;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "vd")
public class VendorDetails {

	 	@Id
	    @Column
	 	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id_gen")
	 	@SequenceGenerator(name = "id_gen",initialValue = 1,allocationSize = 1,sequenceName = "idgen")
	 	private long vendori;
	 	

		//@Id
	 	@Column
	 	private String vendorid;
	 	
	    @Column(nullable = false)
	    private String vendorName;

	    @Column(nullable = false)
	    private String stationName;

	    @Column(nullable = false)
	    private int pincode;
	    
	    @Column(nullable = false)
	    private String city;
	    
	    @Column(nullable = false)
	    private String landmark;
	    
	    @Column(nullable = false)
	    private String state;
	    
	    @Column(nullable = false)
	    private int slot;
	    
	    @Column(nullable = false)
	    private String capacity;
	    
	    @Column(nullable = false)
	    private int vType;
	    
	    @Column(updatable = false)
	    private LocalDateTime createdAt = LocalDateTime.now();
	    
	    @Column(unique = true, nullable = false)
	    private long phone; 
	    
	    @Column(unique = true, nullable = false)
	    private String email;

	    public long getVendori() {
			return vendori;
		}

		public void setVendori(long vendori) {
			this.vendori = vendori;
		}
		public String getVendorid() {
			return vendorid;
		}

		public void setVendorid(String vendorid) {
			this.vendorid = vendorid;
		}

		public String getVendorName() {
			return vendorName;
		}

		public void setVendorName(String vendorName) {
			this.vendorName = vendorName;
		}

		public String getStationName() {
			return stationName;
		}

		public void setStationName(String stationName) {
			this.stationName = stationName;
		}

		public int getPincode() {
			return pincode;
		}

		public void setPincode(int pincode) {
			this.pincode = pincode;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getLandmark() {
			return landmark;
		}

		public void setLandmark(String landmark) {
			this.landmark = landmark;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public int getSlot() {
			return slot;
		}

		public void setSlot(int slot) {
			this.slot = slot;
		}

		public String getCapacity() {
			return capacity;
		}

		public void setCapacity(String capacity) {
			this.capacity = capacity;
		}

		public int getvType() {
			return vType;
		}

		public void setvType(int vType) {
			this.vType = vType;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public long getPhone() {
			return phone;
		}

		public void setPhone(long phone) {
			this.phone = phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public String toString() {
			return "VendorDetails [vendorid=" + vendorid + ", vendorName=" + vendorName + ", stationName=" + stationName
					+ ", pincode=" + pincode + ", city=" + city + ", landmark=" + landmark + ", state=" + state
					+ ", slot=" + slot + ", capacity=" + capacity + ", vType=" + vType + ", createdAt=" + createdAt
					+ ", phone=" + phone + ", email=" + email + "]";
		}
	    
}
