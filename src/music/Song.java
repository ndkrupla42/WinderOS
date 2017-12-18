package music;

public class Song 
{
   private String title;
   private int min;
   private int sec;
   private String mp3_str;

   public Song(String title, String length, String artist, String cd, int track)
   {
      this.title = title;

      String[] pieces = length.split(":");
      String minutes = pieces[0];
      String seconds = pieces[1];
      min = Integer.parseInt(minutes);
      sec = Integer.parseInt(seconds);
	  
	   mp3_str = "resources/mp3/";
      mp3_str += artist + "/";
      mp3_str += cd + "/";

      if (track < 10)
      {
         mp3_str += "0" + track +"-";
      }
      else
      {  
         mp3_str += "" + track + "-";
      }
      mp3_str += title + ".mp3";
   }

   public String getTitle()
   {
      return title;
   }

   public String getMP3()
   {
      return mp3_str;
   }

   public String toString()
   {
      String second = "";
      if (sec < 10)
      {
         second = "0" + sec;
      }
      else
      {
         second = sec + "";
      }

      return title + " " + min + ":" + second;
   }
}
