package com.charge.ev.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.charge.ev.entries.VendorDetails;

@Repository
public interface VendorDao extends CrudRepository<VendorDetails, String> {
	
	@Query(value = "select max(vendori) from vd", name = "maz",nativeQuery=true)
	public long findmax();
}
