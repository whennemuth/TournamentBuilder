package com.warren.tournament.service.populators;

import java.util.Comparator;

import com.warren.tournament.entity.Side;
import com.warren.tournament.util.Utils;

/**
 * This comparator uses reflection so that fields of Side instances can be identified at runtime 
 * and their output values to serve as the basis for comparison in sorting.
 * 
 * @author Warren
 *
 * @param <T>
 */
public class SideComparator <T extends Comparable<T>> implements Comparator<Side> {

	private String fieldName;
	private Class<T> clazz;
	
	public static final String DEFAULT_SORT_FIELD = "sortOrder";
	
	private SideComparator() { /* Restrict private constructor */ }
	
	public static SideComparator<?> getDefaultInstance() {
		return getInstance(DEFAULT_SORT_FIELD, Integer.class);
	}
	
	/**
	 * This factory method takes parameters necessary in getting values for comparison via reflection.
	 * For example if Sides are to be sorted by rank, "rank" and Float.class would be the parameters. 
	 * 
	 * @param fieldName An accessor field of the Side class whose return type must implement Comparable
	 * @param clazz The type of the fieldName value. This type must be for a class that implements Comparable.
	 */	
	public static <T extends Comparable<T>> SideComparator<T> getInstance(String fieldName, Class<T> clazz) {
		SideComparator<T> comparator = new SideComparator<T>();
		comparator.fieldName = fieldName;
		comparator.clazz = clazz;
		return comparator;
	}

	/**
	 * If compared field values are equal, then comparing the two sides would return zero.
	 * This is to be avoided because the TreeSet would consider the two sides equal and where the comparison is 
	 * triggered through the add method the set would not allow such a side to be added because this would 
	 * violate the uniqueness of the set. Therefore 1 is returned in case of zero. 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compare(Side side1, Side side2) {
		try {
			int retval = 0;
			Object obj1 = Utils.getAccessorValue(side1, fieldName);
			Object obj2 = Utils.getAccessorValue(side2, fieldName);
			
			if(obj1 == null) {
				if(obj2 != null)
					retval = 1;
			}
			else if(obj2 == null) {
				retval = -1;
			}
			else {
				Comparable<T> val1 = clazz.cast(obj1);
				Comparable<T> val2 = clazz.cast(obj2);
				retval = val1.compareTo((T) val2);
			}
			// Have exhausted all basis for comparison, so arbitrarily assign a non-zero value
			return retval == 0 ? 1 : retval;
				
		} 
		catch (Exception e) {
			return 0;
		}
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Class<T> getClazz() {
		return clazz;
	}
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
}
