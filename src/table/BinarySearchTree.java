package table;

import table.Comparator;

public class BinarySearchTree<E, S> extends BinaryTreeBasis<E> implements SearchTreeInterface<E, S>
{ 
   protected int size;
   protected Comparator<E, S> compare_keys;
   
   public int size()
   {
      return size;
   }
   
   public BinarySearchTree(Comparator<E, S> comp_keys)
   {
      super();
	  size = 0;
	  compare_keys = comp_keys;
   }

   public E retrieve(S search_key) 
   {
      return retrieveItem(getRootNode(), search_key);
   }  

   public void insert(E item)
   {
      setRootNode(insertItem(getRootNode(), item));
	  size++;
	  assert validateSize() : "Size incorrect for insertion of item " + item.toString() + ".";
	  assert validateBST() : "BST property invalid for insertion of item " + item.toString() + ".";
   }  

   public void remove(S search_key)
   {
      setRootNode(removeItem(getRootNode(), search_key));
	  size--;
	  assert validateSize() : "Size incorrect for removal of key " + search_key + ".";
	  assert validateBST() : "BST property invalid for removal of key " + search_key + ".";
   }  

   protected E retrieveItem(TreeNode<E> tNode, S search_key)
   {
      //disallow duplicates so that you always know which object to retrieve (or delete)
      //you could return a list with all duplicate search keys (but delete is still a problem)
      E treeItem;

      if (tNode == null) 
      {
         treeItem = null;
      }
      else 
      {
         E nodeItem = tNode.getItem();
         int comparison = compare_keys.compareKeyItem(search_key, nodeItem);

         if (comparison == 0) 
         {
            // item is in the root of some subtree
            treeItem = nodeItem;
         }
         else if (comparison < 0) 
         {
            // search the left subtree
            treeItem = retrieveItem(tNode.getLeft(), search_key);
         }
         else  
         { 
            // search the right subtree
            treeItem = retrieveItem(tNode.getRight(), search_key);
         }  
      }  
      return treeItem;
   }  

   //after a node is inserted, check the BST property
   protected TreeNode<E> insertItem(TreeNode<E> tNode, E item)
   {

      if (tNode == null) 
      {
         // position of insertion found; insert after leaf
         // create a new node
         tNode = new TreeNode<E>(item);
         return tNode;
      }  

      TreeNode<E> subtree;
      E nodeItem = tNode.getItem();
	  int comparison = compare_keys.compareItemItem(item, nodeItem);

      if (comparison < 0) 
      {
         // search the left subtree
         subtree = insertItem(tNode.getLeft(), item);
         tNode.setLeft(subtree);
      }
      else 
      { 
         // search the right subtree
         subtree = insertItem(tNode.getRight(), item);
         tNode.setRight(subtree);
      }  

      return tNode;
   }  

   protected TreeNode<E> removeItem(TreeNode<E> tNode, S search_key)
   {

      if (tNode == null) 
      {
         return null;  //is this right??
		 //throw new TreeException("Item not found");
      }

      TreeNode<E> subtree;
      E nodeItem = tNode.getItem();
      int comparison = compare_keys.compareKeyItem(search_key, nodeItem);

      if (comparison == 0) 
      {
         // item is in the root of some subtree
         tNode = removeNode(tNode);  // delete the item
      }
      // else search for the item
      else if (comparison < 0) 
      {
         // search the left subtree
         subtree = removeItem(tNode.getLeft(), search_key);
         tNode.setLeft(subtree);
      }
      else 
      { 
         // search the right subtree
         subtree = removeItem(tNode.getRight(), search_key);
         tNode.setRight(subtree);
      } 

      return tNode;
   }  

   protected TreeNode<E> removeNode(TreeNode<E> tNode) 
   {
      // Algorithm note: There are four cases to consider:
      //   1. The tNode is a leaf.
      //   2. The tNode has no left child.
      //   3. The tNode has no right child.
      //   4. The tNode has two children.
      // Calls: findLeftmost and deleteLeftmost

      // test for a leaf --  this test is taken care of by the next two
      if ((tNode.getLeft() == null) && (tNode.getRight() == null)) 
      {
         return null;
      }  

      // test for no left child
      else if (tNode.getLeft() == null) 
      {
         return tNode.getRight();
      } 

      // test for no right child
      else if (tNode.getRight() == null) 
      {
         return tNode.getLeft();
      } 

      // there are two children:
      // retrieve and delete the inorder successor
      else 
      {
         E replacementItem = findLeftmost(tNode.getRight());
         tNode.setItem(replacementItem);
         TreeNode<E> subtree = removeLeftmost(tNode.getRight());
         tNode.setRight(subtree);
         return tNode;
      } 
   }  

   protected E findLeftmost(TreeNode<E> tNode)  
   {
      if (tNode.getLeft() == null) 
      {
         return tNode.getItem();
      }
      else 
      {
         return findLeftmost(tNode.getLeft());
      }  
   } 

   protected TreeNode<E> removeLeftmost(TreeNode<E> tNode) 
   {
      if (tNode.getLeft() == null) 
      {
         return tNode.getRight();
      }
      else 
      {
         TreeNode<E> subtree = removeLeftmost(tNode.getLeft());
         tNode.setLeft(subtree);
         return tNode;
      }  
   } 

   //would child classes (AVLTree) have issues if the
   //implementation of this method changed
   //but the method signature remained the same
   protected TreeNode<E> rotateLeft(TreeNode<E> tNode)
   {
      TreeNode<E> right = tNode.getRight();
      TreeNode<E> rightleft = right.getLeft();

      tNode.setRight(rightleft);
      right.setLeft(tNode);

      return right;
   }

   protected TreeNode<E> rotateRight(TreeNode<E> tNode)
   {
      TreeNode<E> left = tNode.getLeft();
      TreeNode<E> leftright = left.getRight();

      tNode.setLeft(leftright);
      left.setRight(tNode);

      return left;
   }
   
   public boolean isBalanced()
   {
      return isBalanced(getRootNode());
   }

   private boolean isBalanced(TreeNode<E> tNode)
   {
      if (tNode == null)
      {
         return true;
      }

      boolean balanced = isBalanced(tNode.getLeft());
      if (!balanced) return balanced;
      balanced = isBalanced(tNode.getRight());
      if (!balanced) return balanced;

      int leftHeight = getHeight(tNode.getLeft());
      int rightHeight = getHeight(tNode.getRight());
      if (Math.abs(leftHeight - rightHeight) > 1)
      {
         balanced = false;
      }

      return balanced;
   }

   public int height()
   {
      return getHeight(getRootNode());
   }

   private int getHeight(TreeNode<E> tNode)
   {
      if (tNode == null)
      {
         return 0;
      }

      int height = 0;
      int leftHeight = getHeight(tNode.getLeft());
      int rightHeight = getHeight(tNode.getRight());
      if (leftHeight >= rightHeight)
      {
         height = leftHeight + 1;
      }
      else
      {
         height = rightHeight + 1;
      }

      return height;
   }

   protected boolean validateBST()
   {
      TreeIterator<E> iter = iterator();
      iter.setInorder();
      boolean valid = true;

      //an empty tree satisfies the BST property by definition
      if (!iter.hasNext()) return true;

      E item1 = iter.next();
      //S key1 = item1.getKey();
      while (iter.hasNext())
      {
         E item2 = iter.next();
         //S key2 = item2.getKey();

         //key1 must be <= key2 for BST property
         if (compare_keys.compareItemItem(item1, item2) > 0)
         {
            valid = false;
            break;
         }

         item1 = item2;
         //key1 = key2;
      }

      return valid;
   }
   
   private int computeSize()
   {
      int num = 0;
      TreeIterator<E> iter = iterator();
      iter.setInorder();

      while (iter.hasNext())
      {
         E item = iter.next();
         num++;
      }

      return num;
   }

   protected boolean validateSize()
   {
      return size() == computeSize();
   }
   
} 
