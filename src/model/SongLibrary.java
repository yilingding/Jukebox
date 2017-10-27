package model;

import java.io.Serializable;

/**
 * This class is the SongLibrary class that stores several songs.
 * It maintains a list of all playable songs.
 *
 * @author Yiling Ding and Mingjun Zhou
 */

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class SongLibrary implements TableModel, Serializable {
	private ArrayList<Song> songCollection;
	public static final String FILE_NAME = "accounts2.ser";
	private static SongLibrary self;

	/*
	 * Purpose: the constructor of SongLibrary that takes no parameters.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public SongLibrary() {
		songCollection = new ArrayList<Song>();
		buildLibrary();
	}

	/*
	 * Purpose: maintain the singleton design pattern of the SongLibrary.
	 * 
	 * Parameter: none
	 * 
	 * Return type: SongLibrary
	 */
	public static synchronized SongLibrary getInstance() {
		if (self == null) {
			self = new SongLibrary();
		}
		return self;
	}

	/*
	 * Purpose: add several songs to start with .
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	private void buildLibrary() {
		Song song1 = new Song("KevinMacLeod", "DanseMacabre", "DanseMacabreViolinHook.mp3", 34);
		songCollection.add(song1);
		Song song2 = new Song("FreePlayMusic", "DeterminedTumbao", "DeterminedTumbao.mp3", 20);
		songCollection.add(song2);
		Song song3 = new Song("SunMicrosystems", "Flute", "flute.aif", 5);
		songCollection.add(song3);
		Song song4 = new Song("KevinMacLeod", "LopingSting", "LopingSting.mp3", 4);
		songCollection.add(song4);
		Song song5 = new Song("Unknown", "SpaceMusic", "spacemusic.au", 6);
		songCollection.add(song5);
		Song song6 = new Song("FreePlayMusic", "Swing Cheese", "SwingCheese.mp3", 15);
		songCollection.add(song6);
		Song song7 = new Song("Microsoft", "Tada", "tada.wav", 2);
		songCollection.add(song7);
		Song song8 = new Song("KevinMacLeod", "TheCurtainRises", "TheCurtainRises.mp3", 28);
		songCollection.add(song8);
		Song song9 = new Song("PierreLanger", "Untameable Fire", "UntameableFire.mp3", 282);
		songCollection.add(song9);
	}

	/*
	 * Purpose: get the size of library.
	 * 
	 * Parameter: none
	 * 
	 * Return type: int
	 */
	public int getSize() {
		return songCollection.size();
	}

	/*
	 * Purpose: add a new song in library.
	 * 
	 * Parameter: Song
	 * 
	 * Return type: none
	 */
	public void add(Song newSong) {
		songCollection.add(newSong);
	}

	/*
	 * Purpose: delete a song in library.
	 * 
	 * Parameter: Song
	 * 
	 * Return type: none
	 */
	public void delete(Song song) {
		songCollection.remove(song);
	}

	/*
	 * Purpose: get a song in library.
	 * 
	 * Parameter: int
	 * 
	 * Return type: Song
	 */
	public Song getSong(int i) {
		return songCollection.get(i);
	}

	/*
	 * Purpose: get the count of row in table.
	 * 
	 * Parameter: none
	 * 
	 * Return type: int
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return songCollection.size();
	}

	/*
	 * Purpose: get the count of column in table.
	 * 
	 * Parameter: none
	 * 
	 * Return type: int
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	/*
	 * Purpose: get the name in the columnIndex in table.
	 * 
	 * Parameter: int columnIndex
	 * 
	 * Return type: String
	 */
	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			return "Artist";
		} else if (columnIndex == 1) {
			return "Title";
		} else if (columnIndex == 2) {
			return "Seconds";
		}
		return null;

	}

	/*
	 * Purpose: get the class in the columnIndex in table.
	 * 
	 * Parameter: int columnIndex
	 * 
	 * Return type: Class<?>
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex <= 1) {
			return String.class;
		} else {
			return Integer.class;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * Purpose: get the value in certain place in table.
	 * 
	 * Parameter: int rowIndex, int columnIndex
	 * 
	 * Return type: Object
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		Song song = songCollection.get(rowIndex);
		if (columnIndex == 0) {
			return song.getArtist();
		} else if (columnIndex == 1) {
			return song.getTitle();
		} else {
			return song.getLength();
		}

	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}
}