package com.perscholas.scorekeeper.dao;

import com.perscholas.scorekeeper.entity.Game;
import com.perscholas.scorekeeper.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameDAO extends JpaRepository<Game, Long> {
	List<Game> findAll();
	Game findById(long id);
	Game findByPlayers(Player player);
}
