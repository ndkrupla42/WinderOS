package music;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

public class SongTransferable implements Transferable
{
	static DataFlavor song_flavor = null;

	static
	{
		try
		{
			song_flavor = new DataFlavor(Class.forName("music.Song"), "Song");

		}
		catch (ClassNotFoundException e){}
	}

	private Song song;

	public SongTransferable(Song song)
	{
		this.song = song;
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
	{
		if(flavor.equals(song_flavor))
		{
			return song;
		}
		else
		{
			throw new UnsupportedFlavorException(flavor);
		}
	}

	public DataFlavor[] getTransferDataFlavors()
	{
		DataFlavor[] flavors = {song_flavor};
		return flavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return flavor.equals(song_flavor);
	}
}