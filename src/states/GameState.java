package states;

import java.awt.Graphics;
import entitiesManager.EntitiesManager;
import graphics.Assets;
import score.Score;

//stan podczas trwania rozrywki
public class GameState extends State{
	
	
	//rozmiar okna
	public final int width, height;
	
	//objekty klas
	private EntitiesManager eManager;
	private Score score;
	
	
	public GameState(int width, int height) {
		this.width = width;
		this.height = height;
		
		init();
	}

	//inicjalizuje objekty klas
	public void init() {
		eManager = new EntitiesManager(this);
		
		score = new Score(this);
	}


	@Override
	public void update() {
		eManager.update();
		score.update();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.background, 0, 0, width, height, null);
		
		eManager.render(g);
		score.render(g);
	}
	
	
	//getters
	public EntitiesManager getEM() {
		
		return eManager;
	}
	
	public Score getScore() {
		
		return score;
	}

}
