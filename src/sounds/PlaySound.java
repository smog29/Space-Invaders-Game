package sounds;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlaySound {

	
	public static void play(String location) {
		
		try {
			URL soundLocation = PlaySound.class.getResource(location);
			
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundLocation);
				
			Clip clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.start();
				
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
