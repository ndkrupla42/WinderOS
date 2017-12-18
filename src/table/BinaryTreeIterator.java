package table;

import util.QueueLinked;

class BinaryTreeIterator<E> implements TreeIterator<E>
{
   private TreeNode<E> root;  
   private QueueLinked<E> items;       

   public BinaryTreeIterator(TreeNode<E> root) 
   {
      this.root = root;
      items = new QueueLinked<E>();
   } 

   public boolean hasNext() 
   {
      return !items.isEmpty();
   }  

   public E next() throws TreeException
   {
	   if (hasNext())
	   {
		   return items.dequeue();
	   }
	   
	   throw new TreeException("End of tree traversal.");
   }  

   public void remove() throws UnsupportedOperationException 
   {
      throw new UnsupportedOperationException();
   }  

   public void setPreorder() 
   {
      items.dequeueAll();
      preorder(root);
   } 

   public void setInorder() 
   {
      items.dequeueAll();
      inorder(root);
   }  

   public void setPostorder() 
   {
      items.dequeueAll();
      postorder(root);
   }   

   private void preorder(TreeNode<E> tNode) 
   {
      if (tNode != null) 
      {  
         items.enqueue(tNode.getItem());
         preorder(tNode.getLeft());
         preorder(tNode.getRight());
      } 
   } 

   private void inorder(TreeNode<E> tNode) 
   {
      if (tNode != null) 
      {  
         inorder(tNode.getLeft());
         items.enqueue(tNode.getItem());
         inorder(tNode.getRight());
      }
   }  

   private void postorder(TreeNode<E> tNode) 
   {
      if (tNode != null) 
      {  
         postorder(tNode.getLeft());
         postorder(tNode.getRight());
         items.enqueue(tNode.getItem());
      } 
   } 

   public void setLevelorder()
   {
      if (root != null)
      {
         QueueLinked<TreeNode<E>> q = new QueueLinked<TreeNode<E>>();
         q.enqueue(root);

         while (!q.isEmpty())
         {
            TreeNode<E> tNode = q.dequeue();
            items.enqueue(tNode.getItem());

            TreeNode<E> left = tNode.getLeft();
            TreeNode<E> right = tNode.getRight();

            if (left != null)
            {
               q.enqueue(left);
            }

            if (right != null)
            {
               q.enqueue(right);
            }
         }
      }
   }
}  