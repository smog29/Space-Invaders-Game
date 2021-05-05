package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import input.KeyManager;

public class GameOverState extends State{

	
	
	private final Font gameOverFont = new Font("Comic Sans MS", Font.BOLD, 100); //czcionka Game Over
	
	private final Font scoreFont = new Font("Comic Sans MS", Font.BOLD, 40); //czcionka wyniku
	
	private final GameState gameState;
	
	public GameOverState(GameState gameState) {
		this.gameState = gameState;
	} 
	
	@Override
	public void update() {
		if(KeyManager.R) {
			State.currentState = new StartingState(gameState.width, gameState.height);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, gameState.width, gameState.height);
		
		g.setColor(Color.GREEN);
		
		
		g.setFont(gameOverFont);
		g.drawString("GAME OVER", 200, gameState.height / 2 - 100);
		
		g.setFont(scoreFont);
		g.drawString("Your Score: " + gameState.getScore().getScore(), 350, gameState.height / 2 + 50);
		
		g.setColor(Color.RED);
		g.drawString("Press R to restart...", 300, gameState.height / 2 + 200);
	
	}

}
