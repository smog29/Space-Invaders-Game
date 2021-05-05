package score;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import customization.PlayerColor;
import difficulty.DifficultyLevels;
import states.GameOverState;
import states.GameState;
import states.State;

//klasa przechowuje wynik i zycia gracza
public class Score {

	private final GameState state;
	
	//wynik
	private int score;
	
	//zycie
	private int health = 0;
	
	//czas trwania gry
	private int time = 0;
	//licznik zwiekszany po kazdym updacie gry
	private int counter = 0;
	
	//czcionka napisu
	private final Font f = new Font("Comic Sans MS", Font.BOLD, 30); 
	
	public Score(GameState state) {
		this.state = state;
		
		score = 0;
		
		//ustawia ilosc zycia w zaleznosci od poziomu trudnosci	
		health = 3 - DifficultyLevels.currentDifficulty.ordinal();
	}
	
	private void gameOver() {
		State.currentState = new GameOverState(state);
	}
	
	private void drawHealth(Graphics g) {
		
		for(int i = 0 ; i < health ; i++) {
			g.drawImage(PlayerColor.getPlayerAsset(), 730 + i * 80, 20, 40, 20, null);
		}
	}
	
	public void update() {
		//koniec gry jesli zycie spadlo do 0
		if(health <= 0)
			gameOver();

		if(counter == 60) {
			counter = 0;
			time++;
		}

		counter++;
	}
	
	public void render(Graphics g) {
		g.setFont(f);
		
		g.setColor(Color.GREEN);
		
		g.drawString("Score:  " + score, state.width / 2 - 60, 30);
		
		drawHealth(g);
		
		g.drawString("Time: " + time,20, 30);
	}
	
	//zmienia wynik (dodaje lub odejmuje punkty)
	public void addScore(int a) {
		score += a;
		
		if(score < 0)
			score = 0;
	}
	
	//odejmuje zycia gracza
	public void subtractHealth() {
		health--;
	}
	
	//getters
	
	public int getScore() {
		return score;
	}
	
	
	public int getTime() {
		return time;
	}
	
	public int getCounter() {
		return counter;
	}
	
}
