package model;

import java.io.Serializable;

/**
 * This class is the Song class that contains song information.
 * It maintains times played, length, audio file name, artist, title.
 *
 * @author Yiling Ding and Mingjun Zhou
 */

import java.time.LocalDate;
import java.util.ArrayList;

public class Song implements Serializable {
	private int timePlayed;
	private int length;
	private String fileName;
	private String artist;
	private String title;
	private ArrayList<LocalDate> date = new ArrayList<LocalDate>();

	/*
	 * Purpose: the constructor of Song that takes four parameters.
	 * 
	 * Parameter: String artist, String title, String fileName, int length
	 * 
	 * Return type: none
	 */
	public Song(String artist, String title, String fileName, int length) {
		this.artist = artist;
		this.title = title;
		this.fileName = fileName;
		this.length = length;
	}

	/*
	 * Purpose: increase the time played of song.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public void increaseTimePlayed() {
		timePlayed++;
	}

	/*
	 * Purpose:reset the timePlayed.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public void reset() {
		timePlayed = 0;
	}

	/*
	 * Purpose: get timePlayed
	 * 
	 * Parameter: none
	 * 
	 * Return type: int
	 */
	public int getTimePlayed() {
		return timePlayed;
	}

	/*
	 * Purpose: get length of Song
	 * 
	 * Parameter: none
	 * 
	 * Return type: int
	 */
	public int getLength() {
		return length;
	}

	/*
	 * Purpose: get FileName of Song
	 * 
	 * Parameter: none
	 * 
	 * Return type: String
	 */
	public String getfileName() {
		return fileName;
	}

	/*
	 * Purpose: get artist of Song
	 * 
	 * Parameter: none
	 * 
	 * Return type: String
	 */
	public String getArtist() {
		return artist;
	}

	/*
	 * Purpose: get Title of Song
	 * 
	 * Parameter: none
	 * 
	 * Return type: String
	 */
	public String getTitle() {
		return title;
	}

	/*
	 * Purpose: check the number of time played
	 * 
	 * Parameter: LocalDate newdate
	 * 
	 * Return type: boolean
	 */
	public boolean checkTimePlayed(LocalDate newdate) {
		if (checkSameDate(newdate)) {
			if (timePlayed < 3)
				return true;
			else
				return false;
		} else {
			date.clear();
			date.add(newdate);
			reset();

			return true;
		}
	}

	/*
	 * Purpose: check the same date
	 * 
	 * Parameter: LocalDate newdate
	 * 
	 * Return type: boolean
	 */
	public boolean checkSameDate(LocalDate newdate) {
		if (date.size() == 0) {
			return false;
		} else {
			if (date.get(date.size() - 1).compareTo(newdate) == 0) {
				return true;
			} else {
				return false;
			}
		}
	}

}