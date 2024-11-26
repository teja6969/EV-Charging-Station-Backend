package com.charge.ev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charge.ev.dao.EvDao;
import com.charge.ev.dao.VendorDao;
import com.charge.ev.entries.Entries;
import com.charge.ev.entries.SlotType;
import com.charge.ev.entries.VendorDetails;

@Service
public class EvService {
    @Autowired
    EvDao evDao;

    @Autowired
    VendorDao vdao;
    
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
		//long id=vdao.findmax();
		
		//vd.setVendorid("EV00"+vd.getVendori());
		
		List<SlotType> slotTypes = vd.getSl();
		if (slotTypes != null) {
            for (SlotType slotType : slotTypes) {
                slotType.setstationID(vd);
            }
        }
		
		System.out.println(slotTypes);
		return vdao.save(vd);
		
	}



	public Optional<VendorDetails> vdupdateretrieve(String a) {
		// TODO Auto-generated method stub
		return vdao.findByVendorid(a);
		
	}
}
