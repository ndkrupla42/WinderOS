package music;

import java.awt.*;
import javax.swing.*;
import java.awt.dnd.*;
import java.awt.event.*;
import gui.CenterFrame;
import gui.Drawable;
import gui.DrawPanel;
import gui.EasyGridBag;
import java.util.Iterator;

public class MusicGUI extends CenterFrame implements Drawable, ActionListener
{
	private JComboBox<Artist> artistCombo;
	private JComboBox<CD> cdCombo;
	private ManySongs manySongs;
	private SingleSong song;
	private Music MC;

	public MusicGUI(int width, int height, Music mc)
	{
		
		super (width, height, "Badass Metal CDs");
		MC = mc;
		setLayout(new BorderLayout());
		JPanel center = new JPanel();
		add(center, BorderLayout.CENTER);
		Dimension dim = new Dimension(10,17);
		DrawPanel draw = new DrawPanel();
		draw.setDrawable(this);

		cdCombo = new JComboBox<CD>();		
		artistCombo = new JComboBox<Artist>();
		manySongs = new ManySongs();
		manySongs.setFont(new Font("Verdana",Font.PLAIN, 12));
		manySongs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		song = new SingleSong();
		song.setSize(dim);

		JPanel northPanel = new JPanel();
		GridLayout northLayout = new GridLayout(1,2);
		northPanel.setLayout(northLayout);
		northPanel.add(artistCombo);
		northPanel.add(cdCombo);
		add(northPanel, BorderLayout.NORTH);

		JScrollPane songScroll = new JScrollPane();
		songScroll.setPreferredSize(dim);
		songScroll.getViewport().add(manySongs);

		JPanel songPanel = new JPanel();
		songPanel.setLayout(new BorderLayout());
		songPanel.add(songScroll, BorderLayout.CENTER);
		songPanel.add(song, BorderLayout.SOUTH);

		EasyGridBag easy = new EasyGridBag(1,4,center);
		center.setLayout(easy);

		easy.fillCellWithRowColSpan(1,2,1,4,GridBagConstraints.BOTH,draw);
		easy.fillCellWithRowColSpan(1,1,1,1,GridBagConstraints.BOTH,songPanel);

		setArtistList(MC);

		artistCombo.setActionCommand("artistCombo");
		artistCombo.addActionListener(this);
		artistCombo.setSelectedIndex(0);

		cdCombo.setActionCommand("cdCombo");
		cdCombo.setSelectedIndex(0);

		DragSource dragSource = DragSource.getDefaultDragSource();
		DragGestureRecognizer dragger = dragSource.createDefaultDragGestureRecognizer(manySongs, DnDConstants.ACTION_COPY, manySongs);
		DropTarget dropTarget = new DropTarget(song, song);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("artistCombo"))
		{
			Artist temp = (Artist)artistCombo.getSelectedItem();
			setCDList(temp);
			cdCombo.setSelectedIndex(0);
			manySongs.setCD((CD)cdCombo.getSelectedItem());
		}
		if(ae.getActionCommand().equals("cdCombo"))
		{
			manySongs.setCD((CD)cdCombo.getSelectedItem());
		}
	}

	private void setArtistList(Music MC)
	{
		Iterator<Artist> it = MC.Iterator();

		while(it.hasNext())
		{
			artistCombo.addItem(it.next());
		}
	}

	private void setCDList(Artist artist)
	{
		Iterator<CD> iter = artist.cdIterator();
		cdCombo.removeActionListener(this);
		cdCombo.removeAllItems();

		while (iter.hasNext())
		{			
			CD tempcd = iter.next();
			cdCombo.addItem(tempcd);
			System.out.println(tempcd.toString());
		}

		System.out.println("finished\n\n");
		cdCombo.addActionListener(this);
	}

    public void draw(Graphics g, int width, int height){}
    public void mouseClicked(int x, int y){}
    public void keyPressed(char key){}
}