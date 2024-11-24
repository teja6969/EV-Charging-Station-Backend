package com.charge.ev.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charge.ev.entries.SlotType;

public interface SlotDao extends JpaRepository<SlotType, Integer>{
	
}
