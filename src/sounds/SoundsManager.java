package sounds;

import java.net.URL;

import states.GameState;

//klasa odtwarza kilka dzwiekow co okreslony czas
public class SoundsManager {

	private final String[] sounds;

	//indeks dzwieku
	private int index;
		
	//co ile ma zmieniac sie dzwiek
	private int speed;
		
	long timer, beforeTime;

	public SoundsManager(String[] sounds, int speed) {
		this.sounds = sounds;
		this.speed = speed;
		
		beforeTime = System.currentTimeMillis(); 
		
		timer = 0;
		index = 0;
	}
	
	public void update() {
		
		timer += System.currentTimeMillis() - beforeTime;
		beforeTime = System.currentTimeMillis();
		
		if(timer >= speed) {
			playSound();
			index++;
			
			timer = 0;
		}
		
		if(index >= sounds.length) {
			index = 0;
		}
	}
	
	private void playSound() {
		PlaySound.play(sounds[index]);
	}
	
	//setter
	public void changeSpeed(int x) {
		speed = x;
	}
	
	//getter
	public int getSpeed() {
		return speed;
	}
}
