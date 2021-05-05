package entities;

import java.awt.Graphics;

public abstract class Entity {

	protected float x, y;
	
	public final int width, height;

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;

		this.width = width;
		this.height = height;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
