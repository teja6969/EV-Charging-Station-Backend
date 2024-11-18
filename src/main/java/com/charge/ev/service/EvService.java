package com.charge.ev.service;

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
}
