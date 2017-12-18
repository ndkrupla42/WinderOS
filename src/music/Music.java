package music;

import java.util.Iterator;
import table.TableInterface;
import table.TableFactory;
import table.TableException;

public class Music
{
   private TableInterface<Artist, String> artistTable;

   public Music(String file_name)
   {
      artistTable = TableFactory.createTable(new CompareArtists(true));

      readMusic(file_name);
   }

   public Artist retrieveArtist(String artist)
   {
      return artistTable.tableRetrieve(artist);
   }

   public CD retrieveCD(String artist, String cd)
   {
      return retrieveArtist(artist).retrieveCD(cd);
   }

   private void readMusic(String file_name)
   {
      util.ReadTextFile rf = new util.ReadTextFile(file_name);
      String art = rf.readLine();
      System.out.println(art);

      while (!rf.EOF())
      {
         String title = rf.readLine();
         System.out.println(title);
         int year = Integer.parseInt(rf.readLine());
         int rating = Integer.parseInt(rf.readLine());
         int numTracks = Integer.parseInt(rf.readLine());
         CD cd = new CD(title, art, year, rating, numTracks);

         int tracks = 1;

         while (tracks <= numTracks)
         {
            String temp = rf.readLine();
            String[] line = temp.split(",");
            String len = line[0];
            String song_title = line[1];
			   Song song = new Song(song_title, len, art, cd.getTitle(), tracks);
            cd.addSong(song);
            tracks++;
         }
		 
		 //DO THIS
         //if the artist isn't already present in the table, create a new artist and insert it

         Artist artist = artistTable.tableRetrieve(art);

         if(artist==null)
         {
            artist = new Artist(art);
            artistTable.tableInsert(artist);            
         }

         artist.insertCD(cd);            

         art = rf.readLine();
      }
	  
	  rf.close();
   }

   public int numArtists()
   {
      return artistTable.tableSize();
   }

   public Iterator<Artist> Iterator()
   {
      return artistTable.iterator();
   }

   public static void main(String[] args)
   {
      Music mc = new Music("resources/cds.txt");
      //instantiate your GUI here
      MusicGUI gui = new MusicGUI(640,480,mc);


   }
}