package model;

import java.io.Serializable;
import java.util.ArrayList;

public class JukeboxAccountCollection implements Serializable {
	private static JukeboxAccountCollection self;
	private ArrayList<JukeboxAccount> collection = new ArrayList<JukeboxAccount>();
	public static final String FILE_NAME = "accounts1.ser";

	/*
	 * Purpose: the constructor of JukeboxAccountCollection that takes no
	 * parameter.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public JukeboxAccountCollection() {
		addTheAccount();
	}

	/*
	 * Purpose: maintain the singleton design pattern of the
	 * JukeboxAccountCollection.
	 * 
	 * Parameter: none
	 * 
	 * Return type: JukeboxAccountCollection
	 */
	public static synchronized JukeboxAccountCollection getInstance() {
		if (self == null) {
			self = new JukeboxAccountCollection();
		}
		return self;
	}

	/*
	 * Purpose: add several accounts in JukeboxAccountCollection to start with
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public void addTheAccount() {
		JukeboxAccount chris = new JukeboxAccount();
		chris.setAccountName("Chris");
		chris.setpassword("1");

		JukeboxAccount devon = new JukeboxAccount();
		devon.setAccountName("Devon");
		devon.setpassword("22");

		JukeboxAccount river = new JukeboxAccount();
		river.setAccountName("River");
		river.setpassword("333");

		JukeboxAccount ryan = new JukeboxAccount();
		ryan.setAccountName("Ryan");
		ryan.setpassword("4444");

		this.addAccount(chris);
		this.addAccount(devon);
		this.addAccount(river);
		this.addAccount(ryan);
	}

	/*
	 * Purpose: add a new account in JukeboxAccountCollection.
	 * 
	 * Parameter:JukeboxAccount account
	 * 
	 * Return type: none
	 */
	public void addAccount(JukeboxAccount account) {
		collection.add(account);
	}

	/*
	 * Purpose: get the ith account in JukeboxAccountCollection.
	 * 
	 * Parameter:int i
	 * 
	 * Return type: JukeboxAccount
	 */
	public JukeboxAccount getAccount(int i) {
		return collection.get(i);
	}

	/*
	 * Purpose: remove the ith account in JukeboxAccountCollection.
	 * 
	 * Parameter:int i
	 * 
	 * Return type: JukeboxAccount
	 */
	public void removeAccount(JukeboxAccount account) {
		if (!collection.remove(account)) {
			System.out.println("Warning this account doesn't exist");
		}
	}

	/*
	 * Purpose: check if the account passed in with name is in
	 * JukeboxAccountCollection.
	 * 
	 * Parameter: String name
	 * 
	 * Return type: boolean
	 */
	public boolean checkAccountExist(String name) {
		for (int i = 0; i < size(); i++) {
			if (getAccount(i).getAccount().compareTo(name) == 0) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Purpose: get the size of JukeboxAccountCollection.
	 * 
	 * Parameter: none
	 * 
	 * Return type: int
	 */
	public int size() {
		return collection.size();
	}

	/*
	 * Purpose: find the account in JukeboxAccountCollection by checking name.
	 * 
	 * Parameter: String name
	 * 
	 * Return type: int (-1 if didn't find a match)
	 */
	public int findAccount(String name) {
		for (int i = 0; i < size(); i++) {
			if (getAccount(i).getAccount().compareTo(name) == 0) {
				return i;
			}
		}
		return -1;
	}

	/*
	 * Purpose:check if a password matches the account name.
	 * 
	 * Parameter: char[] password, String name
	 * 
	 * Return type: boolean
	 */
	public boolean checkPassword(char[] password, String name) {
		int index = this.findAccount(name);
		String correctPassword = getAccount(index).getPassword();
		if (correctPassword.length() != password.length) {
			return false;
		}
		for (int i = 0; i < correctPassword.length(); i++) {
			if (correctPassword.charAt(i) != password[i]) {
				return false;
			}
		}
		return true;
	}

}