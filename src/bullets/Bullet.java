package bullets;

import java.awt.Graphics;

import states.GameState;

public abstract class Bullet {

	public float x, y;
	
	public final int width, height;
	
	protected final float ySpeed;
	
	protected GameState state;
	
	public Bullet(float x, float y, float ySpeed, int width, int height, GameState state) {
		this.x = x;
		this.y = y;
		this.ySpeed = ySpeed;
		this.width = width;
		this.height = height;
		
		this.state = state;
	}
	
	
	public abstract void update();
	
	public abstract void render(Graphics g);
}
