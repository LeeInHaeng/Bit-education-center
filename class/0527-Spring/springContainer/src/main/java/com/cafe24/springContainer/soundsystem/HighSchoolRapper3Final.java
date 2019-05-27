package com.cafe24.springContainer.soundsystem;

import javax.inject.Named;

import org.springframework.stereotype.Component;

@Component("highSchoolRapper3Final")
//@Named("highSchoolRapper3Final")
public class HighSchoolRapper3Final implements CompactDisc {

	private String title = "지구멸망";
	private String artist = "양승호";
			
	@Override
	public void play() {
		// TODO Auto-generated method stub
		System.out.println("Playing " + title + " by " + artist);
	}

}
