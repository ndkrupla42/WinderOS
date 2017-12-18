package table;

/**
 * This class creates and returns an implementation of TableInterface (BST).
 * This is an example of the Factory design pattern.
 */
public class TableFactory<E, S>
{
   /**
    * Call this method to obtain a TableInterface implementation.
    */
   public static <E,S> TableInterface<E, S> createTable(Comparator<E, S> comp_keys)
   {
      return new TableSTBased<E, S>(comp_keys);
   }
} 