package tests;

/**
* This class is the Junit test class that test all models.
* It maintains tests for all the methods in models.
*
* @author Yiling Ding and Mingjun Zhou
*/

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import model.JukeboxAccount;
import model.JukeboxAccountCollection;
import model.Song;
import model.SongLibrary;

public class JukeboxTest {

	/*
	 * Test for the AcccountCollection
	 */
	@Test
	public void testAcccountCollection() {
		JukeboxAccountCollection collection = new JukeboxAccountCollection();
		JukeboxAccount account = new JukeboxAccount();
		account.setAccountName("Mercer");
		account.setpassword("123");
		collection.addAccount(account);

		assertTrue(collection.checkAccountExist("Mercer"));
		char[] passwd = { '1', '2', '3' };
		assertTrue(collection.checkPassword(passwd, "Mercer"));
		assertFalse(collection.checkPassword(passwd, "Chris"));
		account.decreaseCredit(1000);
		assertEquals(89000, account.getCreditLeft());
		collection.removeAccount(account);
	}

	/*
	 * Test for the SongLibrary
	 */
	@Test
	public void testSongLibrary() {
		SongLibrary songLibrary = new SongLibrary();
		Song song = new Song("unknownArtist", "unknownTitle", "file.mp3", 3);
		songLibrary.add(song);
		assertEquals(10, songLibrary.getSize());
		assertEquals("unknownArtist", song.getArtist());
		assertEquals("file.mp3", song.getfileName());
		assertEquals(3, song.getLength());
		assertEquals("unknownTitle", song.getTitle());
		assertEquals(song, songLibrary.getSong(9));
		song.increaseTimePlayed();
		assertEquals(1, song.getTimePlayed());
		songLibrary.delete(song);
	}

	/*
	 * Test for the TimePlayed
	 */
	@Test
	public void testSongCheckTimePlayed() {
		LocalDate today = LocalDate.now();
		SongLibrary songLibrary = new SongLibrary();
		Song song = new Song("unknownArtist", "unknownTitle", "file.mp3", 3);
		songLibrary.add(song);

		song.increaseTimePlayed();
		assertTrue(song.checkTimePlayed(today));

		song.increaseTimePlayed();
		assertTrue(song.checkTimePlayed(today));

		song.increaseTimePlayed();
		assertTrue(song.checkTimePlayed(today));

		song.increaseTimePlayed();
		assertFalse(song.checkTimePlayed(today));
		LocalDate tmr = LocalDate.now().plusDays(1);
		assertTrue(song.checkTimePlayed(tmr));
		assertEquals(0, song.getTimePlayed());
	}

	/*
	 * Test for the AcccountTimePlayed
	 */
	@Test
	public void testAccountCheckTimePlayed() {
		LocalDate today = LocalDate.now();
		JukeboxAccountCollection collection = new JukeboxAccountCollection();
		JukeboxAccount account = new JukeboxAccount();
		account.setAccountName("Mercer");
		account.setpassword("123");
		collection.addAccount(account);

		account.increaseTimePlayed();
		assertTrue(account.checkTimePlayed(today));

		account.increaseTimePlayed();
		assertTrue(account.checkTimePlayed(today));

		account.increaseTimePlayed();
		assertTrue(account.checkTimePlayed(today));

		account.increaseTimePlayed();
		assertFalse(account.checkTimePlayed(today));
		LocalDate tmr = LocalDate.now().plusDays(1);
		assertTrue(account.checkTimePlayed(tmr));
		assertEquals(0, account.getTimePlayed());
	}
}
