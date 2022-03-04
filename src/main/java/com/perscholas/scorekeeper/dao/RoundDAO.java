package com.perscholas.scorekeeper.dao;

import com.perscholas.scorekeeper.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundDAO extends JpaRepository<Round, Long> {
	Round findById(long id);
}
