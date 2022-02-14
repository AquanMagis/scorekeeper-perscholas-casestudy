package com.perscholas.scorekeeper.dao;

import com.perscholas.scorekeeper.entity.Player;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerDAO {
	List<Player> findAll();
	Player findById(int id);
	Player findByUsername(String username);
}
