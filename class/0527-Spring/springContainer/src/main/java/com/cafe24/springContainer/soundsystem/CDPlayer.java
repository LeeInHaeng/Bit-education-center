package com.cafe24.springContainer.soundsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CDPlayer {

	// 와이어링01
	//@Autowired
	private CompactDisc cd;
	
	public CDPlayer() {
		
	}
	
	// 와이어링02
	//@Autowired
	public CDPlayer(CompactDisc cd) {
		this.cd = cd;
	}
	
	public void play() {
		cd.play();
	}

	public CompactDisc getCd() {
		return cd;
	}

	// 와이어링03
	//@Autowired
	public void setCd(CompactDisc cd) {
		this.cd = cd;
	}
	
	// 와이어링04
	@Autowired
	public void insertCompactDisc(CompactDisc cd) {
		this.cd = cd;
		
	}
}
