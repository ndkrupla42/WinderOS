package table;
import java.util.Comparator;

public class CompareStrings implements Comparator<String>
{
	private boolean asc;
	public CompareStrings(boolean asc)
	{
		this.asc = asc;
	}
	
	public int compare(String str_1, String str_2)
	{
		int result = str_1.compareTo(str_2);
		if (asc)
		{
			return result;
		}
		else
		{
			return -1*result;
		}
	}
}
