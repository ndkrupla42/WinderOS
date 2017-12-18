package music;

public class MusicException extends Exception
{
   public MusicException()
   {
      this("Yo... something went bad...");
   }

   public MusicException(String s)
   {
      super(s);
   }
}