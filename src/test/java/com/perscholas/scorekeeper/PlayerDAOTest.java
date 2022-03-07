package com.perscholas.scorekeeper;

import com.perscholas.scorekeeper.dao.GameDAO;
import com.perscholas.scorekeeper.dao.HandDAO;
import com.perscholas.scorekeeper.dao.PlayerDAO;
import com.perscholas.scorekeeper.dao.RoundDAO;
import com.perscholas.scorekeeper.entity.Player;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerDAOTest {
	private static final String USERNAME = "tester";
	private static final String PASSWORD = "password";
	private static final String NEW_PASSWORD = "newpassword";

	@Autowired
	private PlayerDAO playerDAO;
	@Autowired
	private GameDAO gameDAO;
	@Autowired
	private RoundDAO roundDAO;
	@Autowired
	private HandDAO handDAO;

	@Test
	@Order(1)
	@Rollback(value = false)
	public void testCreate(){
		Player player = new Player();
		player.setUsername(USERNAME);
		player.setPassword(PASSWORD);

		playerDAO.save(player);
		Assertions.assertTrue(player.getId() > 0);
	}

	@Test
	@Order(2)
	@Rollback(value = false)
	public void testRead(){
		Player player = playerDAO.findByUsername(USERNAME);
		Assertions.assertEquals(player.getPassword(), PASSWORD);
	}

	@Test
	@Order(3)
	@Rollback(value = false)
	public void testUpdate(){
		Player player = playerDAO.findByUsername(USERNAME);
		player.setPassword(NEW_PASSWORD);
		Assertions.assertEquals(NEW_PASSWORD, playerDAO.findByUsername(USERNAME).getPassword());
	}

	@Test
	@Order(4)
	@Rollback(value = false)
	public void testDelete(){
		Player player = playerDAO.findByUsername(USERNAME);
		playerDAO.delete(player);
		Assertions.assertNull(playerDAO.findByUsername(USERNAME));
	}
}
