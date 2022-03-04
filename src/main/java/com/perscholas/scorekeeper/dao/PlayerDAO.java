package com.perscholas.scorekeeper.dao;

import com.perscholas.scorekeeper.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerDAO extends JpaRepository<Player, Long> {
	List<Player> findAll();
	Player findById(long id);
	Player findByUsername(String username);
	Player findByEmail(String email);
}
