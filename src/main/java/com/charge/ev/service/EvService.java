package com.charge.ev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charge.ev.dao.EvDao;
import com.charge.ev.dao.VendorDao;
import com.charge.ev.entries.Entries;
import com.charge.ev.entries.VendorDetails;

@Service
public class EvService {
    @Autowired
    EvDao evDao;

    @Autowired
    VendorDao vdao;
    
    public Entries loginService(String username, String password) {
        return evDao.findByUsernameAndPassword(username, password);
    }

	public Entries registerService(Entries register) {
		// TODO Auto-generated method stub
		System.out.println("register");
		return evDao.save(register);
	}
	public boolean registercheck(String username) {
		return evDao.existsById(username);
	}

	public VendorDetails registerVendor(VendorDetails vd) {
		long id=vdao.findmax();
		System.out.println("max id"+id);
		vd.setVendorid("EV00"+id);
		return vdao.save(vd);
		
	}
}
