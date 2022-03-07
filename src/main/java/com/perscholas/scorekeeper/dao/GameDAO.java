package com.perscholas.scorekeeper.dao;

import com.perscholas.scorekeeper.entity.Game;
import com.perscholas.scorekeeper.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameDAO extends JpaRepository<Game, Long> {
	List<Game> findAll();
	Game findById(long id);
	List<Game> findByPlayers(Player player);
	@Query(nativeQuery = true, value = "SELECT DISTINCT game_id FROM game_players WHERE player_id = :playerId")
	List<Long> findIdByPlayerId(Long playerId);
}
