package com.perscholas.scorekeeper;

import com.perscholas.scorekeeper.dao.GameDAO;
import com.perscholas.scorekeeper.dao.HandDAO;
import com.perscholas.scorekeeper.dao.PlayerDAO;
import com.perscholas.scorekeeper.dao.RoundDAO;
import com.perscholas.scorekeeper.entity.Hand;
import com.perscholas.scorekeeper.entity.Player;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HandDAOTest {
	//This is an invalid value in production, should avoid collisions.
	private static final int FU = 53;
	private static final int HAN = 3;
	private static final int NEW_HAN = 4;
	private static long handId;

	@Autowired
	private HandDAO handDAO;

	@Test
	@Order(1)
	@Rollback(value = false)
	public void testCreate(){
		Hand hand = new Hand();
		hand.setFu(FU);
		hand.setHan(HAN);

		handDAO.save(hand);
		handId = hand.getId();
		Assertions.assertTrue(hand.getId() > 0);
	}

	@Test
	@Order(2)
	@Rollback(value = false)
	public void testRead(){
		Hand hand = handDAO.findById(handId);
		Assertions.assertEquals(hand.getFu(), FU);
		Assertions.assertEquals(hand.getHan(), HAN);
	}

	@Test
	@Order(3)
	@Rollback(value = false)
	public void testUpdate(){
		Hand hand = handDAO.findById(handId);
		hand.setHan(NEW_HAN);
		handDAO.save(hand);
		Assertions.assertEquals(NEW_HAN, handDAO.findById(handId).getHan());
	}

	@Test
	@Order(4)
	@Rollback(value = false)
	public void testDelete(){
		Hand hand = handDAO.findById(handId);
		handDAO.delete(hand);
		Assertions.assertNull(handDAO.findById(handId));
	}
}
