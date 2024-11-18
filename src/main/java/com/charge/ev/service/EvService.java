package com.charge.ev.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charge.ev.dao.EvDao;
import com.charge.ev.entries.Entries;

@Service
public class EvService {
    @Autowired
    EvDao evDao;

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
}
