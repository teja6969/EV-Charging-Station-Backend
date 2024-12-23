package com.charge.ev.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.charge.ev.entries.Reservation;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, Long>{

	@Query(value="select * from slot_booking where vendorid=?1 and to_char(sdatet,'YYYY-MM-DD')=?2", nativeQuery = true)
	public List<Reservation> getByVendorIdandDate(String vendorid,String availability);

}
