package com.perscholas.scorekeeper.dao;

import com.perscholas.scorekeeper.entity.Hand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandDAO extends JpaRepository<Hand, Long> {
	Hand findById(long id);
}
