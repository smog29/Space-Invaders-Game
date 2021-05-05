package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import customization.PlayerColor;
import difficulty.DifficultyLevels;
import graphics.Assets;
import input.KeyManager;

public class StartingState extends State{

	private final int width, height;
	
	//poruszanie sie potwora na ekranie tytulowym
	private int x,y;
	private boolean xDirection;
	private boolean yDirection;
	
	//poziom trudnosci wybierany przez gracza
	private int difficulty;
	
	//sprawdza ile czasu minelo
	private int counter = 0;
	
	//number koloru wybieranego przez gracza
	private int color;
	

	
	public StartingState(int width, int height) {
		this.height = height;
		this.width = width;
		
		x = width / 2 - 50;
		y = 300;
		xDirection = true;
		yDirection = true;
		
		difficulty = 0;
		color = 2;
		
		//wylaczenie koloru, zeby dalo sie ponownie ustawic po porazce
		PlayerColor.playerColor = null;
	}
	
	
	private void showPlayerColor(Graphics g) {
		if(PlayerColor.playerColor != null)
			return;
		
		g.setColor(PlayerColor.getJavaColor(color));
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 60));
		g.drawString("Choose a Color", 280, 500);
		
		//rysowanie koloru postaci wybieranej
		g.drawImage(PlayerColor.intToAsset(color), width / 2 - 30, 600, 70, 55, null);
	}
	
	

	private void showDifficulty(Graphics g) {
		
		//rysuje dopiero jak jest wybrany kolor
		if(PlayerColor.playerColor == null)
			return;
		
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		g.drawString("Choose difficulty: ", 360, 750);
		
		
		g.drawString("Easy", 200, 850);
		g.drawString("Medium", 450, 850);
		g.drawString("Hard", 700, 850);
		
		//pokazuje ktory poziom trudnosci jest wybrany
		for(int i = 0 ; i < 3 ; i++) {
			if(difficulty == i)
				g.fillRect(i * 250 + 180, 840, 15, 15);
		}
	}
	
	//wybieranie koloru
	public void choosePlayerColor() {
		if(PlayerColor.playerColor != null)
			return;
		
		if(KeyManager.left && !KeyManager.keyHeld){
			color--;
			
			if(color < 0)
				color = PlayerColor.numberOfColors - 1;
			
			KeyManager.keyHeld = true;
		}
		
		if(KeyManager.right && !KeyManager.keyHeld) {
			color++;
			
			if(color >= PlayerColor.numberOfColors)
				color = 0;
			
			KeyManager.keyHeld = true;
		}
		
		if(KeyManager.space) {
			PlayerColor.playerColor = PlayerColor.intToEnum(color);
			KeyManager.keyHeld = true;
		}
	}
	
	private void chooseDifficulty() {
		
		// jesli nie wybrany kolor nie mozna wybierac trudnosci, najpierw uzytkownik wybiera kolor
		if (PlayerColor.playerColor == null)
			return;

		// wybor poziomu trudnosci
		if (KeyManager.left && !KeyManager.keyHeld) {
			if (difficulty > 0)
				difficulty--;
			KeyManager.keyHeld = true;
		}
		if (KeyManager.right && !KeyManager.keyHeld) {
			if (difficulty < 2)
				difficulty++;
			KeyManager.keyHeld = true;
		}

		// akceptowanie poziomu trudnosci i przejscie do gry
		if (KeyManager.space && !KeyManager.keyHeld) {
			DifficultyLevels.currentDifficulty = DifficultyLevels.intToEnum(difficulty);
			State.currentState = new GameState(width, height);
		}
	}

	
	//porusza w losowa strone potworem
	private void randDirection() {
		if(counter >= 1)
			return;
		
		if(Math.random() < 0.5) {
			xDirection = true;
		}
		else if(Math.random() >= 0.5) {
			xDirection = false;
		}
	}
	
	//poruszanie sie potwora na ekranie tytulowym
	private void monsterMovement() {
		
		randDirection();
		
		//pozwala potworowi ruszac sie po sekundzie
		if(counter < 60){
			counter++;
			return;
		}
		
		
		if(x <= 0)
			xDirection = true;
		else if(x + 100 >= width)
			xDirection = false;
		
		if(y <= 0)
			yDirection = true;
		else if(y + 80 >= height)
			yDirection = false;
		
		if(xDirection)
			x += 2;
		else
			x -= 2;
		
		if(yDirection)
			y += 1;
		else
			y -= 1;
	}

	
	@Override
	public void update() {

		// porusza potworem na ekranie tytulowym
		monsterMovement();
		
		//wybieranie koloru gracza
		choosePlayerColor();

		// wybieranie poziomu trudnosci
		chooseDifficulty();

	}

	@Override
	public void render(Graphics g) {
	
		// rysowanie tla
		//g.setColor(Color.BLACK);
		//g.fillRect(0, 0, width, height);

		g.drawImage(Assets.background,0,0,width,height,null);
		

		//potwor
		g.drawImage(Assets.monster[0], x, y, 100, 80, null);
		
		//napis tytulowy
		g.setColor(Color.GREEN);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 80));
		g.drawString("Space Invaders", 180,200);
		
		
		//rysowanie gracza
		showPlayerColor(g);
		
		//rysowanie poziomu trudnosci
		showDifficulty(g);
		
	}

}
