package controller;

import java.awt.BorderLayout;

/**
 * This class is the GUI part of JukeBox. This is the 1st iteration
 * and we can choose two songs from 4 valid accounts. Each account can play
 * three songs in one day, and each song can be played at most three times per day.
 *
 * @author Yiling Ding and Mingjun Zhou
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.JukeboxAccount;
import model.JukeboxAccountCollection;
import model.PlayList;
import model.Song;
import model.SongLibrary;
import songplayer.EndOfSongEvent;
import songplayer.EndOfSongListener;
import songplayer.SongPlayer;

public class JukeboxGUI extends JFrame {

	/*
	 * Purpose: the main class of JukeboxGUI.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public static void main(String[] args) {
		JukeboxGUI g = new JukeboxGUI();
		g.setVisible(true);
	}

	private JButton signout;
	private JButton login;
	private int loginStatus;
	private WaitingForSongToEnd waiter;
	private String name;
	public String baseDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "songfiles"
			+ System.getProperty("file.separator");
	private JTextArea status;
	private JukeboxAccountCollection collection;
	private JSplitPane splitPane;
	private JTextField account = new JTextField(15);
	private JPasswordField password = new JPasswordField(15);
	private TableModel model;
	private ListModel model2;
	private JPanel song = new JPanel();
	private JList songPlayList;
	private JButton addSong = new JButton(new ImageIcon("images/play.png"));
	private JTable table;

	/*
	 * Purpose: the constructor of JukeboxGUI that set up basic panel.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	public JukeboxGUI() {
		waiter = new WaitingForSongToEnd();
		loadDataWithOptionPane();
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		buildTheGUI();
	}

	/*
	 * Purpose: load the saved data.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	private void loadDataWithOptionPane() {
		int choice = JOptionPane.showConfirmDialog(null,
				"Start with previous saved state? \n No means all new objects.");
		if (choice == 0) {
			startWithPersistentVersion();
		} else if (choice == 1) {
			model2 = new PlayList();
			new SongLibrary();
			model = new SongLibrary();
			collection = new JukeboxAccountCollection();
		} else {
			JOptionPane.showMessageDialog(null,
					"You choose the cancel option, which means: \n I can do nothing for you.");
			System.exit(0);
		}
		songPlayList = new JList(model2);
		if (((PlayList) model2).size() != 0) {
			playSong();
		}
	}

	/*
	 * Purpose: quit the program and give information
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	private void quitPanel() {
		int choice = JOptionPane.showConfirmDialog(null, "Do you want to save the data.");
		if (choice == 0) {
			saveData();// save(yes) is 0
			System.exit(0);
		} else if (choice == 1) {
			System.exit(0);
		} else {
			JOptionPane.showMessageDialog(null, "You choose cancel, save or not what do you want?\n So please choose again.");
		}
	}

	/*
	 * Purpose: build the frame and panel in the GUI
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	private void buildTheGUI() {
		//set window size and location.
		this.setSize(800, 800);
		this.setLocation(100, 40);
		
		//manage log in and signout
		loginStatus = 0;
		login = new JButton("  Log in  ");
		signout = new JButton("  Sign out  ");
		ButtonLogInListener logInListener = new ButtonLogInListener();
		login.addActionListener(logInListener);
		ButtonSignoutListener signOutListener = new ButtonSignoutListener();
		signout.addActionListener(signOutListener);
		
		//build up panel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout());
		leftPanel.setBackground(Color.PINK);
		addSong.setSize(200, 80);
		table = new JTable(model);
		JScrollPane scrool = new JScrollPane(table);
		JScrollPane scrool1 = new JScrollPane(songPlayList);
		table.setFont(new Font("Courier", Font.PLAIN, 14));
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		
		//build the user login panel
		JPanel information = new JPanel();
		information.setPreferredSize(new Dimension(330, 250));
		information.setLayout(new FlowLayout());
		JLabel accountName = new JLabel("  Account Name");
		JLabel passwordLabel = new JLabel("      Password");
		information.add(accountName);
		information.add(account);
		information.add(passwordLabel);
		information.add(password);
		information.setSize(300, 300);
		information.add(login);
		information.add(signout);
		information.setBackground(new Color(230, 230, 250));
		Font myFont = new Font("Courier", Font.PLAIN, 18);
		status = new JTextArea("      status: Login first  ");
		status.setFont(new Font("Courier", Font.PLAIN, 18));
		status.setBackground(new Color(230, 230, 250));
		accountName.setFont(myFont);
		passwordLabel.setFont(myFont);
		signout.setFont(myFont);
		login.setFont(myFont);
		information.add(status);
		
		//split panel
		splitPane = new JSplitPane();
		splitPane.setSize(350, 200);
		splitPane.setDividerSize(0);
		splitPane.setDividerLocation(350);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		
		//build songplaying list as left panel that shows songs in queue
		JLabel playListInformation = new JLabel("Play List(song at top is playing)");
		leftPanel.add(playListInformation);
		leftPanel.add(scrool1);
		leftPanel.add(addSong);
		leftPanel.add(information);
		ButtonSongListener songListener = new ButtonSongListener();
		addSong.addActionListener(songListener);
		splitPane.setLeftComponent(leftPanel);
		
		//build songLibrary shown in the table format in right panel
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.PINK);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		JLabel select = new JLabel("Select song from this JukeBox");
		select.setFont(myFont);
		select.setForeground(Color.RED);
		rightPanel.add(select);
		rightPanel.add(scrool);
		scrool.setPreferredSize(new Dimension(350, 700));
		scrool1.setPreferredSize(new Dimension(330, 320));
		splitPane.setRightComponent(rightPanel);
		this.add(splitPane);
		this.addWindowListener(new CloseWindow());
	}

	/*
	 * Purpose: save the current data.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	private void saveData() {
		FileOutputStream bytesToDisk = null;
		try {
			bytesToDisk = new FileOutputStream(PlayList.FILE_NAME);
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			outFile.writeObject(model2);
			outFile.writeObject(collection);
			outFile.writeObject(model);
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Purpose: start the project with saved data.
	 * 
	 * Parameter: None
	 * 
	 * Return type: None
	 */
	private void startWithPersistentVersion() {
		try {
			ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("accounts.ser"));
			model2 = (PlayList) inFile.readObject();
			collection = (JukeboxAccountCollection) inFile.readObject();
			model = (SongLibrary) inFile.readObject();
			inFile.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "As for now, you don't have any saved file.\n So please choose 'NO' to have a new object.");
			System.exit(0);
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "As for now, you don't have any saved file1.");
			e.printStackTrace();
		}
	}

	/*
	 * Purpose: convert the credit remain to format hh:mm:ss.
	 * 
	 * Parameter: int time
	 * 
	 * Return type: String
	 */
	public String convertTime(int time) {
		int hour = time / 3600;
		time = time % 3600;
		int minute = time / 60;
		time = time % 60;
		String hourString = "00";
		String minuteString = "00";
		String secondString = "00";
		if (hour != 0) {
			hourString = String.valueOf(hour);
		}
		if (minute != 0) {
			minuteString = String.valueOf(minute);
		}

		if (time != 0) {
			secondString = String.valueOf(time);
		}

		return hourString + ":" + minuteString + ":" + secondString;
	}

	/*
	 * Purpose: this is the ButtonListener class that controls the GUI when
	 * login button clicked.
	 * 
	 */
	private class ButtonLogInListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (loginStatus == 1) {
				JOptionPane.showMessageDialog(null, "You haven't signed out the current account yet.");
				return;
			}

			name = account.getText();
			char[] passwd = password.getPassword();

			if (account.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Enter name first");
				return;
			} else if (passwd.length == 0) {
				JOptionPane.showMessageDialog(null, "Enter password first");
				return;
			}

			if (!collection.checkAccountExist(name)) {
				JOptionPane.showMessageDialog(null, "Please enter a valid account");
				return;
			}

			if (!collection.checkPassword(passwd, name)) {
				JOptionPane.showMessageDialog(null, "Please enter the correct password");
				return;
			}
			account.setText("");
			password.setText("");
			JukeboxAccount account = collection.getAccount(collection.findAccount(name));
			account.checkTimePlayed(LocalDate.now());
			int songPlayed = account.getTimePlayed();
			int creditRemain = collection.getAccount(collection.findAccount(name)).getCreditLeft();
			status.setText(account.getAccount() + " logged in," + songPlayed + " played, \n         "
					+ convertTime(creditRemain));
			loginStatus = 1;

		}
	}

	/*
	 * Purpose: this is the ButtonListener class that controls the GUI when
	 * signout button clicked.
	 * 
	 */
	private class ButtonSignoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (loginStatus != 1) {
				JOptionPane.showMessageDialog(null, "You haven't logged in yet.");
				return;
			}
			name = "";
			account.setText("");
			password.setText("");
			JOptionPane.showMessageDialog(null, "You have successfully signed out");
			status.setText("Status: login first.");
			loginStatus = 0;

		}
	}

	/*
	 * Purpose: this is the ButtonListener class that controls the GUI when song
	 * buttons clicked.
	 * 
	 */
	private class ButtonSongListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (loginStatus != 1) {
				JOptionPane.showMessageDialog(null, "User must log in to select song.");
				return;
			}
			String accountName = name;
			JukeboxAccount account = collection.getAccount(collection.findAccount(accountName));// playSong();
			int timeLeft = account.getCreditLeft();
			int viewRowIndex = table.getSelectedRow();
			int modelIndex = table.convertRowIndexToModel(viewRowIndex);
			Song song = ((SongLibrary) model).getSong(modelIndex);
			int songLength = song.getLength();
			if (timeLeft < songLength) {
				JOptionPane.showMessageDialog(null, "You don't have enough credit left, please add money.");
				return;
			}
			if (decide(account, song)) {
				((PlayList) model2).enqueue(song);
				songPlayList.setModel(model2);
				songPlayList.updateUI();
				if (((PlayList) model2).size() == 1) {
					playSong();
				}
				account.decreaseCredit(song.getLength());
				account.increaseTimePlayed();
				song.increaseTimePlayed();
				int creditRemain;
				int songPlayed;
				creditRemain = account.getCreditLeft();
				songPlayed = account.getTimePlayed();
				status.setText(account.getAccount() + " logged in," + songPlayed + " played, \n         "
						+ convertTime(creditRemain));
			}
		}

	}

	/*
	 * Purpose: the method that plays the songs.
	 * 
	 * Parameter: none
	 * 
	 * Return type: none
	 */
	private void playSong() {
		if (((PlayList) model2).size() >= 1) {
			Song song = ((PlayList) model2).peek();
			SongPlayer.playFile(waiter, baseDir + song.getfileName());
		}
	}

	/*
	 * Purpose: the method that decide if the song can be played.
	 * 
	 * Parameter: JukeboxAccount account, Song song
	 * 
	 * Return type: boolean (true if can play, false otherwise)
	 */
	public boolean decide(JukeboxAccount account, Song song) {
		LocalDate today = LocalDate.now();
		if (account.checkTimePlayed(today)) {
			if (song.checkTimePlayed(today)) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "This song has been played 3 times in a day.");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "You have played 3 songs today.");
			return false;
		}
	}

	/*
	 * Purpose: this is the WaitingForSongToEnd class that perform actions when
	 * one song stopped playing.
	 * 
	 */
	class WaitingForSongToEnd implements EndOfSongListener {
		public void songFinishedPlaying(EndOfSongEvent eosEvent) {
			System.out.println("\nFinished " + eosEvent.fileName() + ", " + eosEvent.finishedDate() + ", "
					+ eosEvent.finishedTime());
			if (((PlayList) model2).size() > 1) {
				((PlayList) model2).dequeue();
				songPlayList.setModel(model2);
				songPlayList.updateUI();
				Song song = ((PlayList) model2).peek();
				SongPlayer.playFile(waiter, baseDir + song.getfileName());
			} else if (((PlayList) model2).size() == 1) {
				((PlayList) model2).dequeue();
				songPlayList.setModel(model2);
				songPlayList.updateUI();
			}
		}

	}

	/*
	 * Purpose: The window that appears when quitting the project.
	 * 
	 * Parameter: None
	 * 
	 * Return type: None
	 */
	private class CloseWindow implements WindowListener {
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			quitPanel();
		}

		@Override
		public void windowClosed(WindowEvent e) {

		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub

		}

	}
}