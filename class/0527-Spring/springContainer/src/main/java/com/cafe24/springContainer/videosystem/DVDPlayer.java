package com.cafe24.springContainer.videosystem;

public class DVDPlayer {

	private DigitalVideoDisc dvd;
	
	public DVDPlayer() {
		
	}
	
	public DVDPlayer(DigitalVideoDisc dvd) {
		this.dvd = dvd;
	}
	
	public void play() {
		dvd.play();
	}

	public void setDvd(DigitalVideoDisc dvd) {
		this.dvd = dvd;
	}
}
