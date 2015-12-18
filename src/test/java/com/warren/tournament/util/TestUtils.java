package com.warren.tournament.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestUtils {


	@Test 
	public void testClosestLog2() {
		assertEquals(0, Utils.closestLog2(0));
		assertEquals(0, Utils.closestLog2(1));
		assertEquals(1, Utils.closestLog2(2));
		assertEquals(1, Utils.closestLog2(3));
		assertEquals(2, Utils.closestLog2(4));
		assertEquals(2, Utils.closestLog2(5));
		assertEquals(2, Utils.closestLog2(6));
		assertEquals(2, Utils.closestLog2(7));
		assertEquals(3, Utils.closestLog2(8));
		assertEquals(3, Utils.closestLog2(9));
	}

}
