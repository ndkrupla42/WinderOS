package table;

abstract class BinaryTreeBasis<E>
{
   private TreeNode<E> root;

   public BinaryTreeBasis() 
   {
      root = null;
   }  

   public boolean isEmpty() 
   {
      return (root == null);
   }  

   public E getRootItem()
   {
      if (root == null) 
      {
         return null;
      }
      else 
      {
         return root.getItem();
      } 
   }  

   protected TreeNode<E> getRootNode()
   {
      return root;
   }

   protected void setRootNode(TreeNode<E> tNode)
   {
      root = tNode;
   }
   
   public TreeIterator<E> iterator()
   {
      return new BinaryTreeIterator<E>(root);
   }

}  
