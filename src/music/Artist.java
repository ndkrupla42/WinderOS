package music;

import java.util.Iterator;
import table.TableInterface;
import table.TableFactory;

public class Artist
{
   private TableInterface<CD, String> CDTable;
   private String artist;
   private int numCDs;

   public String toString()
   {
      return this.artist;
   }
   public Artist(String name)
   {
		CDTable = TableFactory.createTable(new CompareCDTitles(true));
		artist = name;
   }

   public CD retrieveCD(String title)
   {
      return CDTable.tableRetrieve(title);
   }

   public int numCDs()
   {
      return CDTable.tableSize();
   }

   public void insertCD(CD album)
   {
   		try
   		{
   			CDTable.tableInsert(album);
   		}
   		catch (Exception e)
   		{
   		}
   }

   public String getArtist()
   {
      return artist;
   }

   public Iterator<CD> cdIterator()
   {
   	return CDTable.iterator();
   }

}
