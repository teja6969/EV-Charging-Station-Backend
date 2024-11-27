package com.charge.ev.dao;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.charge.ev.entries.VendorDetails;

@Repository
public interface VendorDao extends CrudRepository<VendorDetails, Long> {
	
	Optional<VendorDetails> findByVendorid(String vendorid);
	
}
