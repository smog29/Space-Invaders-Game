package sounds;

public class SoundAssets {

	
	public static String[] invaderSounds = new String[4];
	public static String playerShoot;
	
	public static String enemyShot;

	public static String playerHit;

	public static String ufo;
	
	public static void setSoundAssets() {
		invaderSounds[0] = "/music/fastinvader1.wav";
		invaderSounds[1] = "/music/fastinvader2.wav";
		invaderSounds[2] = "/music/fastinvader3.wav";
		invaderSounds[3] = "/music/fastinvader4.wav";
	
		playerShoot = "/music/shoot.wav";
		
		enemyShot = "/music/invaderkilled.wav";

		playerHit = "/music/playerhit.wav";

		ufo = "/music/ufo.wav";
	}
}
