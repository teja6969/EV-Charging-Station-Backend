package com.charge.ev.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.charge.ev.entries.VendorDetails;

@Repository
public interface VendorDao extends CrudRepository<VendorDetails, Long> {
	
	List<VendorDetails> findByVendorid(String vendorid);
	
	
	@Query(value="select * from vd where vd.city like %:city% or vd.pincode= :pincode",nativeQuery = true)
	List<VendorDetails> getbycityorpincode(String city,int pincode);
	
}
