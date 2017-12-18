package table;
import java.util.Comparator;

public class CompareIntegers implements Comparator<Integer>
{
	private boolean asc;
	public CompareIntegers(boolean asc)
	{
		this.asc = asc;
	}
	
	public int compare(Integer int_1, Integer int_2)
	{
		if (asc)
		{
			return int_1 - int_2;
		}
		else
		{
			return int_2 - int_1;
		}
	}
}
