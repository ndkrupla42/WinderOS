package music;

import javax.swing.JList;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureEvent;
import java. awt.dnd.DragSource;

public class ManySongs extends JList<Song> implements DragGestureListener
{
	private static String Empty = new String(" ");

	public ManySongs()
	{
		super();
	}

	public void setCD(CD cd)
	{
		int numSongs = cd.getNumTracks();
		Song[] songs = new Song[numSongs];

		for(int i = 0; i < numSongs; i++)
		{
			songs[i] = cd.getSong(i);
		}

		setListData(songs);
	}


	public void dragGestureRecognized(DragGestureEvent dge)
	{
		SongTransferable transferable = new SongTransferable((Song) getSelectedValue());
		DragSource dragSource = dge.getDragSource();
		dragSource.startDrag(dge, DragSource.DefaultCopyDrop, transferable, null);
	}
}

	