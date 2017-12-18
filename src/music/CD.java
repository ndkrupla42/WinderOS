package music;

import java.awt.Graphics;

public class CD
{
	//pick one of these items to be the sort "key"
   private String title;
   private String artist;
   private int year;
   private int count;
   private int rating;
   private Song[] songs;
   
   private int x_loc, y_loc;
   private int image = 0;
   
   private java.awt.Image img0;
   private java.awt.Image img1;

   private final gui.ImageLoader il = gui.ImageLoader.getImageLoader();
   private static final int x_off = 100;  //the offset will change during a rotation
   private static final int y_off = 100;
   private static final java.awt.Font verdana_12 = new java.awt.Font("Verdana", java.awt.Font.BOLD, 14);

   public CD (String title, String artist, int year, int rating, int tracks)
   {
	  this.title = title;
	  this.artist = artist;
	  
      this.year = year;
	  String img_file = "resources/art/" + artist + " - " + title + ".jpg";
     try
     {
         img0 = il.getImage(img_file);
     }
     catch(Exception e){}

      try
     {
         img1 = il.getHighLightImage(il.getImage(img_file));
     }
     catch(Exception e){}

      count = 0;
      songs = new Song[tracks];

      if (rating > 0 && rating <= 10)
      {
         this.rating = rating;
      }
      else
      {
         rating = 5;
      }
   }
   
   public int getNumTracks()
   {
	   return songs.length;
   }

   public java.awt.Image getImage()
   {
      return img0;
   }

   public java.awt.Image getHighLightImage()
   {
      return img1;
   }
   
   public String getArtist()
   {
      return artist;
   }
   
   public String getTitle()
   {
      return title;
   }

   public int getYear()
   {
      return year;
   }

   public int getRating()
   {
      return rating;
   }

   public Song getSong(int index)
   {
      if (index >= 0 && index < songs.length)
      {
         return songs[index];
      }
      else
      {
         return null;
      }
   }

   public void addSong(Song song)
   {
      if (song != null && count < songs.length)
      {
         songs[count] = song;
         count++;
      }
   }

   public String toString()
   {
      return title + "  " + year + "  " + rating + "  " + songs.length;
   }

}
