package music;

public class ClearSingleSong implements Runnable
{
	private SingleSong single_song;
	
	public ClearSingleSong(SingleSong sing_song)
	{
		single_song = sing_song;
	}

	public void run()
	{
		single_song.clear();
	}

}