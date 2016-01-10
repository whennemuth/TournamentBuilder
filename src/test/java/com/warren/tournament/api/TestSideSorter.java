package com.warren.tournament.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.warren.tournament.entity.Match;
import com.warren.tournament.entity.Player;
import com.warren.tournament.entity.Side;
import com.warren.tournament.service.populators.SideComparator;
import com.warren.tournament.util.PlayerMocks;
import com.warren.tournament.util.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class TestSideSorter {
	
	private TreeSet<Player> playerMocks;
	private Set<Side> unsortedSides;
	private SideSorter ss;

	@Before
	public void setUp() throws Exception {
		playerMocks = PlayerMocks.getPlayerMocks(10);		
		unsortedSides = new HashSet<Side>();
		for(Player player : playerMocks) {
			Match dummyMatch = mock(Match.class);
			Side side = mock(Side.class);
			when(side.getMatch()).thenReturn(dummyMatch);
			when(dummyMatch.getSides()).thenReturn(Arrays.asList(new Side[]{side}));
			when(side.getPlayers()).thenReturn(new HashSet<Player>(Arrays.asList(new Player[]{player})));
			Float rank = new Float(player.getRank());
			when(side.getRank()).thenReturn(rank);
			unsortedSides.add(side);
		}
	}

	/**
	 * Test that a Set of sides can be ordered based on the natural ordering of the rank field values.
	 * The SideSorter will apply the ordering to the set using a comparator provided to it.
	 */
	@Test
	public void testSetSortOrderByRank() {		
		unsortedSides = TestUtils.shuffle(unsortedSides);
		Comparator<Side> comparator = SideComparator.getInstance("rank", Float.class);
		ss = SideSorter.getComparatorOnlyInstance(comparator);
		TreeSet<Side> sortedSides = ss.getSortedSides(unsortedSides);
		assertEquals(10, sortedSides.size());
		int iFirst = 1;
		int iLast = 10;
		while(!sortedSides.isEmpty()) {
			Side first = sortedSides.pollFirst();
			Side last = sortedSides.pollLast();
			assertEquals(new Float(iFirst), first.getRank());
			assertEquals(new Float(iLast), last.getRank());
			iFirst++;
			iLast--;
		}
	}

	/**
	 * Test that a Set of sides can be ordered based on their sortOrder field values.
	 * An implementation of SideSorter will apply the sortOrder field values.
	 */
	@Test
	public void testSetSortOrderCustom() {
		final Map<Float, Integer> customOrder = new HashMap<Float, Integer>();
		customOrder.put(1f, 3);
		customOrder.put(2f, 9);
		customOrder.put(3f, 4);
		customOrder.put(4f, 8);
		customOrder.put(5f, 1);
		customOrder.put(6f, 2);
		customOrder.put(7f, 10);
		customOrder.put(8f, 5);
		customOrder.put(9f, 6);
		customOrder.put(10f, 7);
		
		ss = new SideSorter(){
			@Override public Set<Side> setSortOrderValues(Set<Side> unassignedSides) {
				for(Side side : unassignedSides) {
					Integer sortOrderVal = customOrder.get(side.getRank());
					when(side.getSortOrder()).thenReturn(sortOrderVal);	
				}
				return new HashSet<Side>(unassignedSides);
			}};
		TreeSet<Side> sortedSides = ss.getSortedSides(unsortedSides);
		assertEquals(10, sortedSides.size());
		assertEquals(new Float(5), sortedSides.pollFirst().getRank());
		assertEquals(new Float(6), sortedSides.pollFirst().getRank());
		assertEquals(new Float(1), sortedSides.pollFirst().getRank());
		assertEquals(new Float(3), sortedSides.pollFirst().getRank());
		assertEquals(new Float(8), sortedSides.pollFirst().getRank());
		assertEquals(new Float(9), sortedSides.pollFirst().getRank());
		assertEquals(new Float(10), sortedSides.pollFirst().getRank());
		assertEquals(new Float(4), sortedSides.pollFirst().getRank());
		assertEquals(new Float(2), sortedSides.pollFirst().getRank());
		assertEquals(new Float(7), sortedSides.pollFirst().getRank());
	}

}
