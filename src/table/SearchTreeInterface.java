package table;

public interface SearchTreeInterface<E, S>
{
  // Tree operations:
  // Precondition for all operations: 
  // No two items of the tree have the same search key.
  // The tree's items are sorted by search key.

  public E getRootItem() throws TreeException;
  //public boolean isBalanced();
  //public int getHeight();

 /**
  * Determines whether a tree is empty.
  * Postcondition: Returns true if the tree is 
  * empty; otherwise returns false.
  */
  public boolean isEmpty();

 /**
  * Inserts an item into a tree in its proper sorted
  * order according to the item's search key.
  * Precondition: The item to be inserted into the 
  * tree has a search key that differs from 
  * all search keys presently in the table.
  * Postcondition: If the insertion was successful, 
  * item is in its proper order in the tree.
  * Otherwise, the tree is unchanged.
  * Throws: TreeException if the search key is already present
  */
  public void insert(E item) throws TreeException;

 /**
  * Deletes an item with a given search key from the 
  * tree.
  * Precondition: searchKey is the search key of the 
  * item to be deleted.
  * Postcondition: If the item whose search key equals
  * searchKey existed in the tree, the item was 
  * deleted. Otherwise, the tree is unchanged.
  * Throws: TreeException if the search key is not in the tree.
  */
  public void remove(S searchKey) throws TreeException;

 /**
  * Retrieves an item with a given search key from the
  * tree.
  * Precondition: searchKey is the search key of the 
  * item to be retrieved.
  * Postcondition: If the retrieval was successful,
  * the tree item with the matching search key is
  * returned. If no such item exists, the method
  * returns a null reference.
  */
  public E retrieve(S searchKey);

 /**
  * Returns the TreeIterator for the active tree
  * Preconditions: None
  * Postconditions: The TreeIterator is returned.
  */
  public TreeIterator<E> iterator();

} 