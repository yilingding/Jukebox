package model;

import java.io.Serializable;

/**
 * This class is the JukeboxAccount class that contains account information.
 * It maintains times played and credit remaining of accounts.
 *
 * @author Yiling Ding and Mingjun Zhou
 */

import java.time.LocalDate;
import java.util.ArrayList;

public class JukeboxAccount implements Serializable {
	private String password;
	private String accountName;
	private int timePlayed;
	private int creditRemain;
	private ArrayList<LocalDate> date = new ArrayList<LocalDate>();

	/*
	 * Purpose: the constructor of JukeboxAccount that takes no parameter.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public JukeboxAccount() {
		password = "";
		accountName = "";
		creditRemain = 90000;
		timePlayed = 0;
	}

	/*
	 * Purpose: reset time played.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public void resetTimePlayed() {
		timePlayed = 0;
	}

	public boolean checkSameDate(LocalDate today) {
		if (date.size() == 0) {
			return false;
		} else {
			if (date.get(date.size() - 1).compareTo(today) == 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	/*
	 * Purpose: set password of account.
	 * 
	 * Parameter: String
	 * 
	 * Return type: none
	 */
	public void setpassword(String password) {
		this.password = password;
	}

	/*
	 * Purpose: set name of account.
	 * 
	 * Parameter: String
	 * 
	 * Return type: none
	 */
	public void setAccountName(String name) {
		this.accountName = name;
	}

	/*
	 * Purpose: get password of account.
	 * 
	 * Parameter: String
	 * 
	 * Return type: none
	 */
	public String getPassword() {
		return password;
	}

	/*
	 * Purpose: get account name.
	 * 
	 * Parameter: none
	 * 
	 * Return type: String
	 */
	public String getAccount() {
		return accountName;
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
			resetTimePlayed();

			return true;
		}
	}

	/*
	 * Purpose: increase time played in account.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public void increaseTimePlayed() {
		timePlayed++;
	}

	/*
	 * Purpose: decrease credit in account.
	 * 
	 * Parameter: int
	 * 
	 * Return type: none
	 */
	public void decreaseCredit(int length) {
		creditRemain = creditRemain - length;
	}

	/*
	 * Purpose: get time played in account.
	 * 
	 * Parameter: String
	 * 
	 * Return type: none
	 */
	public int getTimePlayed() {
		return timePlayed;
	}

	/*
	 * Purpose: get credit left in account.
	 * 
	 * Parameter: none
	 * 
	 * Return type: int
	 */
	public int getCreditLeft() {
		return creditRemain;
	}

}