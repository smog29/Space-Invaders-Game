package entitiesManager;

import java.awt.Graphics;

import entitiesManager.enemiesController.EnemiesController;
import entitiesManager.shieldsManager.ShieldsManager;
import entities.Player;
import entitiesManager.ufoSpawner.UfoSpawner;
import states.GameState;

public class EntitiesManager {

	//obiekt stanu gry
	private GameState state;
	
	//obiekty klas
	private Player player;
	private EnemiesController ec;
	private ShieldsManager shields;
	private UfoSpawner ufoSpawner;
	
	
	public EntitiesManager(GameState state) {
		this.state = state;
		
		init();
	}

	// inicjalizuje objekty klas
	public void init() {
		player = new Player(state);
		shields = new ShieldsManager(state);
		ec = new EnemiesController(state, 0);
		ufoSpawner = new UfoSpawner(state);
	}

	//restartuje przeciwnikow jesli wszyscy sa pokonani
	public void restartEnemies(){
		ec = new EnemiesController(state, ec.getLevel() + 1);
	}


	public void update() {
		player.update();
		ec.update();
		shields.update();
		ufoSpawner.update();
	}
	
	public void render(Graphics g) {
		player.render(g);
		ec.render(g);
		shields.render(g);
		ufoSpawner.render(g);
	}
	
	
	// getters
	public Player getPlayer() {
		return player;
	}

	public EnemiesController getEnemiesController() {
		return ec;
	}
	
	public ShieldsManager getShields() {
		return shields;
	}
}
