package com.perscholas.scorekeeper;

import com.perscholas.scorekeeper.dao.RoundDAO;
import com.perscholas.scorekeeper.entity.Round;
import com.perscholas.scorekeeper.entity.Round;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoundDAOTest {
	//These are invalid values in production, should avoid collisions.
	private static final int WIND = 5;
	private static final int NEW_WIND = 6;
	private static long roundId;

	@Autowired
	private RoundDAO roundDAO;

	@Test
	@Order(1)
	@Rollback(value = false)
	public void testCreate(){
		Round round = new Round();
		round.setWind(WIND);

		roundDAO.save(round);
		roundId = round.getId();
		Assertions.assertTrue(round.getId() > 0);
	}

	@Test
	@Order(2)
	@Rollback(value = false)
	public void testRead(){
		Round round = roundDAO.findById(roundId);
		System.out.println(roundId);
		Assertions.assertEquals(round.getWind(), WIND);
	}

	@Test
	@Order(3)
	@Rollback(value = false)
	public void testUpdate(){
		Round round = roundDAO.findById(roundId);
		round.setWind(NEW_WIND);
		roundDAO.save(round);
		Assertions.assertEquals(NEW_WIND, roundDAO.findById(roundId).getWind());
	}

	@Test
	@Order(4)
	@Rollback(value = false)
	public void testDelete(){
		Round round = roundDAO.findById(roundId);
		roundDAO.delete(round);
		Assertions.assertNull(roundDAO.findById(roundId));
	}
}
