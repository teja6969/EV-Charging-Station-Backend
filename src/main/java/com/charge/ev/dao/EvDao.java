package com.charge.ev.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.charge.ev.entries.Entries;

@Repository
public interface EvDao extends CrudRepository<Entries, String> {
    Entries findByUsernameAndPassword(String username, String password);
}
