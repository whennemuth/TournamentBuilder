package com.warren.tournament.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
	
	/**
	 * Shuffle a set into some random order.
	 * NOTE: This won't work if the Set is a TreeSet or based on SortedSet.
	 * 
	 * @param set
	 */
	public static <T> LinkedHashSet<T> shuffle(Set<T> set) {
		// Validate the parameter
		if(set instanceof TreeSet || set instanceof SortedSet) {
			throw new IllegalStateException("Cannot shuffle a set that already imposes a sort order");
		}
		if(set == null) {
			return null;
		}
		
		// Create a list of indexes to diminish and a list of the Sets values to build.
		List<T> list = new ArrayList<T>(set);
		List<Integer> indexes = new ArrayList<Integer>(set.size());
		for(int i = 0; i < set.size(); i++) {
			indexes.add(i);
		}
		
		// Build the List using the Set values by referencing them in the Set through available indexes, 
		// using up those indexes through random selection as we go.
		Random r = new Random(System.currentTimeMillis());
		while(!indexes.isEmpty()) {
			int i = r.nextInt(indexes.size());
			int x = indexes.get(i);
			indexes.remove(i);
			i = r.nextInt(indexes.size());
			int y = indexes.get(i);
			indexes.remove(i);			
			Collections.swap(list, x, y);
		}
		
		// Turn the built list back into a Set
		LinkedHashSet<T> shuffledSet = new LinkedHashSet<T>();
		shuffledSet.addAll(list);
		return shuffledSet;
	}

}
