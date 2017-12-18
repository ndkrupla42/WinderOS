package music;

import table.Comparator;
import table.CompareStrings;

public class CompareArtists implements Comparator<Artist, String>
{
	private java.util.Comparator<String> compare_artists;
	
	public CompareArtists(boolean asc)
	{
		compare_artists = new CompareStrings(asc);
	}
	
	public int compareKeyItem(String artist_str, Artist artist)
	{
		return compare_artists.compare(artist_str, artist.getArtist());
	}
	
	public int compareItemItem(Artist art_1, Artist art_2)
	{
		return compareKeyItem(art_1.getArtist(), art_2);
	}
}
