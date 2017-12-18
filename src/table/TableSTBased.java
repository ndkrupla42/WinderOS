package table;

import java.util.Iterator;

public class TableSTBased<E, S> implements TableInterface<E, S>
{
   private SearchTreeInterface<E, S> st;  // binary search tree that contains the table’s items
   private int size;                // number of items in the table

   public TableSTBased(Comparator<E, S> comp_keys) 
   {  
      st = new BinarySearchTree<E, S>(comp_keys);
      size = 0;
   } 
  
   public boolean tableIsEmpty() 
   {
      return size == 0;
   }  
   
   public int tableSize() 
   {
      return size;
   }  

   public E tableRetrieve(S searchKey) 
   {
      return st.retrieve(searchKey);
   } 
  
   public void tableInsert(E item) throws TableException 
   { 
      try
      {
         st.insert(item);
         size++;
      }
      catch(TreeException e)
      {
         throw new TableException("Insertion failed, duplicate key item");
      }
   } 

   public boolean tableRemove(S searchKey) 
   {
      try 
      {
         st.remove(searchKey);
         size--;
         return true;
      }  
      catch (TreeException e) 
      {
         return false;
      }  
   } 

   public Iterator<E> iterator()
   {
      TreeIterator<E> iter = st.iterator();
      iter.setInorder();
      return iter;
   }
} 