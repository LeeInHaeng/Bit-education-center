package com.cafe24.springContainer.videosystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:config/videosystem/DVDPlayerConfig.xml"})
public class DVDPlayerXmlConfigTest {

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
			
	@Autowired
	@Qualifier("ironMan")
	private DigitalVideoDisc dvd2;
	
	@Autowired
	@Qualifier("avengersInfinityWar")
	private DigitalVideoDisc dvd3;
	
	@Autowired
	@Qualifier("avengersEndgame")
	private DigitalVideoDisc dvd4;
	
	@Autowired
	@Qualifier("avengersAgeOfUltron")
	private DigitalVideoDisc dvd5;
	
	@Autowired
	@Qualifier("captainAmerica")
	private DigitalVideoDisc dvd6;
	
	@Autowired
	@Qualifier("avengersDirectorEdition")
	private DigitalVideoDisc dvd7;
	
	@Autowired
	private DVDPack dvdPack;
	
	@Autowired
	@Qualifier("dvdPlayer2")
	private DVDPlayer player2;
	
	@Autowired
	@Qualifier("dvdPlayer3")
	private DVDPlayer player3;
	
	@Autowired
	@Qualifier("dvdPlayer4")
	private DVDPlayer player4;
	
	@Autowired
	@Qualifier("dvdPlayer5")
	private DVDPlayer player5;
	
	@Autowired
	@Qualifier("dvdPlayer6")
	private DVDPlayer player6;
	
	@Autowired
	@Qualifier("dvdPlayer7")
	private DVDPlayer player7;
	
	@Test
	public void testDVD2Null() {
		assertNotNull(dvd2);
	}
	
	@Test
	public void testDVD3() {
		assertNotNull(dvd3);
		System.out.println(dvd3);
		assertEquals("BlankDisc [title=Avengers Infinity War, studio=MARVEL, actors=null]", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
	
	@Test
	public void testDVD4() {
		assertNotNull(dvd4);
		System.out.println(dvd4);
		assertEquals("BlankDisc [title=Avengers Endgame, studio=MARVEL, actors=null]", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
	
	@Test
	public void testDVD5() {
		assertNotNull(dvd5);
		System.out.println(dvd5);
		assertEquals("BlankDisc [title=Avengers Age Of Ultron, studio=MARVEL, actors=null]", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
	
	@Test
	public void testDVD6() {
		assertNotNull(dvd6);
		System.out.println(dvd6);
		assertEquals("BlankDisc [title=Captain America, studio=MARVEL, actors=null]", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
	
	@Test
	public void testDVD7() {
		assertNotNull(dvd7);
		System.out.println(dvd7);
		assertEquals("BlankDisc [title=Avengers Director's Edition, studio=MARVEL, actors=[Robert, Scarlett, Chris]]", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
	
	@Test
	public void testDVDPackNull() {
		assertNotNull(dvdPack);
		System.out.println(dvdPack);
	}
	
	@Test
	public void testPlayer2Null() {
		assertNotNull(player2);
	}
	
	@Test
	public void testPlayer3Null() {
		assertNotNull(player3);
	}
	
	@Test
	public void testPlayer4Null() {
		assertNotNull(player4);
	}
	
	@Test
	public void testPlayer5Null() {
		assertNotNull(player5);
	}
	
	@Test
	public void testPlay2() {
		player2.play();
		assertEquals("Playing Movie MARVEL's IronMan", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
	
	@Test
	public void testPlay3() {
		player3.play();
		assertEquals("Playing Movie MARVEL's IronMan", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
	
	@Test
	public void testPlay4() {
		player4.play();
		assertEquals("Playing Movie MARVEL's IronMan", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
	
	@Test
	public void testPlay5() {
		player5.play();
		assertEquals("Playing Movie MARVEL's IronMan", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
	
	@Test
	public void testPlay6() {
		player6.play();
		assertEquals("Playing Movie MARVEL's Avengers Infinity War", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
	
	@Test
	public void testPlay7() {
		player7.play();
		assertEquals("Playing Movie MARVEL's Avengers Infinity War", systemOutRule.getLog().replace("\r\n", "").replace("\n", ""));
	}
}
