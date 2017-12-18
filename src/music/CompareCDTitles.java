package music;

import table.Comparator;
import table.CompareStrings;

public class CompareCDTitles implements Comparator<CD, String>
{
	private java.util.Comparator<String> compare_titles;
	
	public CompareCDTitles(boolean asc)
	{
		compare_titles = new CompareStrings(asc);
	}
	
	public int compareKeyItem(String title, CD cd)
	{
		return compare_titles.compare(title, cd.getTitle());
	}
	
	public int compareItemItem(CD cd_1, CD cd_2)
	{
		return compareKeyItem(cd_1.getTitle(), cd_2);
	}
}
