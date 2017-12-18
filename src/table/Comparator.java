package table;

public interface Comparator<T, S>
{
	public int compareItemItem(T item1, T item2);
	public int compareKeyItem(S key, T item);
}
