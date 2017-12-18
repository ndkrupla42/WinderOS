package table;

import java.util.Iterator;

public interface TreeIterator<E> extends Iterator<E>
{
   //we can iterate in several different ways since trees are nonlinear
   public void setInorder();
   public void setPreorder();
   public void setPostorder();

   public void setLevelorder();
}