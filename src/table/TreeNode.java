package table;

class TreeNode<T>
{
   private T item;
   private TreeNode<T> leftChild;
   private TreeNode<T> rightChild;

   public TreeNode(T item) 
   {
      this.item = item;
      leftChild  = null;
      rightChild = null;
   } 

   public T getItem() 
   {
      return item;
   }  

   public void setItem(T item) 
   {
      this.item  = item;
   }  

   public TreeNode<T> getLeft() 
   {
      return leftChild;
   } 

   public void setLeft(TreeNode<T> left) 
   {
      leftChild  = left;
   }  

   public TreeNode<T> getRight() 
   {
      return rightChild;
   }  

   public void setRight(TreeNode<T> right) 
   {
      rightChild  = right;
   }  
} 
