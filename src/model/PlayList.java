package model;


/**
 * This class is the PlayList class that stores the songs selected to play.
 * It maintains a list of all playable songs.
 *
 * @author Yiling Ding and Mingjun Zhou
 */

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class PlayList implements ListModel, Serializable {
	private Queue<Song> list;
	private static PlayList self;
	public static final String FILE_NAME = "accounts.ser";
	private int size;

	/*
	 * Purpose: the constructor of the PlayList.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public PlayList() {
		list = new LinkedList<Song>();
		size = 0;

	}

	/*
	 * Purpose: maintain the singleton design pattern of the PlayList.
	 * 
	 * Parameter: none
	 * 
	 * Return type: PlayList
	 */
	public static synchronized PlayList getInstance() {
		if (self == null) {
			self = new PlayList();
		}
		return self;
	}

	/*
	 * Purpose: peek the head element of the PlayList.
	 * 
	 * Parameter: none
	 * 
	 * Return type: Song
	 */
	public Song peek() {
		return list.peek();
	}

	/*
	 * Purpose: add an element at the end of the queue.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public void enqueue(Song song) {
		list.add(song);
		size++;
	}

	/*
	 * Purpose: remove the head element in the queue.
	 * 
	 * Parameter: none
	 * 
	 * Return type: Song
	 */
	public Song dequeue() {
		Song song = list.peek();
		list.remove(list.peek());
		size--;
		return song;
	}

	/*
	 * Purpose: return the size of the PlayList.
	 * 
	 * Parameter: none
	 * 
	 * Return type: int
	 */
	public int size() {
		return size;
	}

	/*
	 * Purpose: return the size of the queue.
	 * 
	 * Parameter: none
	 * 
	 * Return type: int
	 */
	@Override
	public int getSize() {
		return list.size();
	}

	/*
	 * Purpose: return the element in PlayList in index.
	 * 
	 * Parameter: int index
	 * 
	 * Return type: toString() version of the element
	 */
	@Override
	public Object getElementAt(int index) {
		Song song = ((LinkedList<Song>) list).get(index);
		String result = (convertTime(song.getLength()) + " " + song.getTitle() + " by " + song.getArtist());
		return result;
	}

	/*
	 * Purpose: convert the time passed in in certain format.
	 * 
	 * Parameter: int time
	 * 
	 * Return type: String.
	 */
	public String convertTime(int time) {
		time = time % 3600;
		int minute = time / 60;
		time = time % 60;
		String minuteString = "00";
		String secondString = "00";

		if (minute != 0) {
			minuteString = String.valueOf(minute);
		}

		if (time != 0) {
			secondString = String.valueOf(time);
		}

		return minuteString + ":" + secondString;
	}

	@Override
	public void addListDataListener(ListDataListener l) {

	}

	@Override
	public void removeListDataListener(ListDataListener l) {

	}
}