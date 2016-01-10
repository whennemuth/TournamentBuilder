package com.warren.tournament.api;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.warren.tournament.entity.Side;
import com.warren.tournament.service.populators.SideComparator;

/**
 * Set the sortOrder field value of Side instances so that when they are encountered in the 
 * compare method of a comparator a collection of Side instances sort as expected.
 *  
 * @author Warren
 *
 */
public abstract class SideSorter {

	private Comparator<Side> sideComparator;
	
	public abstract Set<Side> setSortOrderValues(Set<Side> unassignedSides);
	
	public void setComparator(Comparator<Side> sideComparator) {
		this.sideComparator = sideComparator;
	}
	
	/**
	 * Factory method to return an instance of this class that operates using the supplied
	 * comparator only for sorting and ignores the setSortOrderValues method.
	 * 
	 * @param sideComparator
	 * @return
	 */
	public static SideSorter getComparatorOnlyInstance(Comparator<Side> sideComparator) {
		SideSorter sideSorter = new SideSorter() {
			@Override public Set<Side> setSortOrderValues(Set<Side> unassignedSides) {
				return unassignedSides; // Don't actually apply any sort values.
			}};
		sideSorter.setComparator(sideComparator);
		return sideSorter;
	}
	
	public Comparator<Side> getComparator() {
		if(sideComparator == null)
			return SideComparator.getDefaultInstance();
		else
			return sideComparator;
	}
	
	public TreeSet<Side> getSortedSides(Set<Side> unsortedSides) {
		unsortedSides = setSortOrderValues(unsortedSides);
		TreeSet<Side> sortedSides = new TreeSet<Side>(getComparator());
		sortedSides.addAll(unsortedSides);
		return sortedSides;
	}
}