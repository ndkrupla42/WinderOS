package music;

import table.Comparator;
import table.CompareIntegers;

public class CompareCDRatings implements Comparator<CD, Integer>
{
	private java.util.Comparator<Integer> compare_ratings;
	
	public CompareCDRatings(boolean asc)
	{
		compare_ratings = new CompareIntegers(asc);
	}
	
	public int compareKeyItem(Integer rating, CD cd)
	{
		return compare_ratings.compare(rating, cd.getRating());
	}
	
	public int compareItemItem(CD cd_1, CD cd_2)
	{
		return compareKeyItem(cd_1.getRating(), cd_2);
	}
}
