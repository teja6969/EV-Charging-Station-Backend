package com.charge.ev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charge.ev.dao.EvDao;
import com.charge.ev.dao.ReservationDao;
import com.charge.ev.dao.SlotDao;
import com.charge.ev.dao.VendorDao;
import com.charge.ev.entries.Entries;
import com.charge.ev.entries.Reservation;
import com.charge.ev.entries.SlotType;
import com.charge.ev.entries.VendorDetails;

@Service
public class EvService {
    @Autowired
    EvDao evDao;

    @Autowired
    VendorDao vdao;
    
    @Autowired
    SlotDao sl;
    
    @Autowired
    ReservationDao rd;
    
    public Entries loginService(String email, String password) {
        return evDao.findByEmailAndPassword(email, password);
    }

	public Entries registerService(Entries register) {
		// TODO Auto-generated method stub
		System.out.println("register");
		return evDao.save(register);
	}
	public String registercheck(String email) {
		return evDao.existsByEmail(email);
	}

	public VendorDetails registerVendor(VendorDetails vd) {
			
		List<SlotType> slotTypes = vd.getSl();
		if (slotTypes != null) {
            for (SlotType slotType : slotTypes) {
                slotType.setstationID(vd);
            }
        }
		
		System.out.println(slotTypes);
		return vdao.save(vd);
		
	}



	public List<VendorDetails> vdupdateretrieve(String a) {
		// TODO Auto-generated method stub
		return vdao.findByVendorid(a);
		
	}
	
	@Transactional
	public Optional<VendorDetails> vupdate(long id) {
		return vdao.findById(id);
	}
	
	public VendorDetails vupdatedeatils(VendorDetails vd) {
		return vdao.save(vd);
	}
	
	public int deleteSlottype(VendorDetails v) {
		return sl.deleteByStationID(v);
	}

	public List<VendorDetails> uservendorretrieve(String city, int pincode) {
		// TODO Auto-generated method stub
		return vdao.getbycityorpincode (city, pincode);
	}

	public List<Reservation> viewSlotAvailability(String vendorid, String availabledate) {
		// TODO Auto-generated method stub
		return rd.getByVendorIdandDate(vendorid, availabledate);
	}
	
	public String getCapacityByVendorId(String vendorid,long stationid) {
		return vdao.getCapacityByVendorId(vendorid,stationid);
	}

	public void slotbooking(Reservation rev) {
		// TODO Auto-generated method stub
		rd.save(rev);
	}
	
}
