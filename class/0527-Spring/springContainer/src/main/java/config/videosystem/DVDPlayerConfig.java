package config.videosystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cafe24.springContainer.videosystem.Avengers;
import com.cafe24.springContainer.videosystem.DVDPlayer;
import com.cafe24.springContainer.videosystem.DigitalVideoDisc;

@Configuration
public class DVDPlayerConfig {

	@Bean
	public Avengers avengers() {
		return new Avengers();
	}

	// 주입하기1
	// @Bean
	public DVDPlayer dvdPlayer1() {
		return new DVDPlayer(avengers());
	}

	// 주입하기2
	//@Bean
	public DVDPlayer dvdPlayer2(DigitalVideoDisc dvd) {
		return new DVDPlayer(dvd);
	}

	// 주입하기3
	//@Bean(name="dvdPlayer3")
	public DVDPlayer dvdPlayer3(DigitalVideoDisc dvd) {
		DVDPlayer player = new DVDPlayer(dvd);
		player.setDvd(dvd);
		return player;
	}
	
	// 주입하기4
	@Bean(name="dvdPlayer4")
	public DVDPlayer dvdPlayer4() {
		return new DVDPlayer(avengers());
	}
}
