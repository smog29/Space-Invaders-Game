package game;

public class Launcher {

	public static void main(String[] args) {

		Game game = new Game("Space Invaders", 960, 900);
		
		game.start();
	} 
}
