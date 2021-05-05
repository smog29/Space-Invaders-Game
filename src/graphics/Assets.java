package graphics;

import java.awt.image.BufferedImage;

//klasa statyczna przechowuje zaladowane assety do gry
public class Assets {

	public static BufferedImage background;

	public static BufferedImage[] monster = new BufferedImage[2];
	public static BufferedImage[] octopus = new BufferedImage[2];
	public static BufferedImage[] headcrab = new BufferedImage[2];
	
	public static BufferedImage player_green, player_red, player_pink;
	
	public static BufferedImage explosion;

	public static BufferedImage ufo;
	
	public static void setAssets() {

		//ustawianie tla
		background = ImageLoader.getImage("/textures/background.png");
		
		//ustawianie assetow potwora
		monster[0] = ImageLoader.getImage("/textures/monster0.png");
		monster[1] = ImageLoader.getImage("/textures/monster1.png");
		
		//ustawianie assetow osmiornicy
		octopus[0] = ImageLoader.getImage("/textures/octopus0.png");
		octopus[1] = ImageLoader.getImage("/textures/octopus1.png");

		headcrab[0] = ImageLoader.getImage("/textures/headcrab0.png");
		headcrab[1] = ImageLoader.getImage("/textures/headcrab1.png");
		
		//ustawianie assetow gracza
		player_green = ImageLoader.getImage("/textures/player_green.png");
		player_red = ImageLoader.getImage("/textures/player_red.png");
		player_pink = ImageLoader.getImage("/textures/player_pink.png");
		
		//wyglad eksplozji - kiedy potwor jest postrzelony
		explosion = ImageLoader.getImage("/textures/explosion.png");

		//wyglad ufa
		ufo = ImageLoader.getImage("/textures/ufo.png");
	}
}
