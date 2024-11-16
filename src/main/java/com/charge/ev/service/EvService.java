package com.charge.ev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.charge.ev.dao.EvDao;
import com.charge.ev.entries.Entries;

@Component
public class EvService {
@Autowired
 EvDao ed;
	public Entries loginService(String username,String password) {
		return ed.findByUsernameAndPassword(username,password);
		
	}

}
