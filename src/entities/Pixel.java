package entities;

import java.awt.Color;
import java.awt.Graphics;

import bullets.Bullet;

public class Pixel extends Entity{
	
	
	public Pixel(float x, float y) {
		super(x, y,3 ,3 );
		
	}
	
	//sprawdzanie czy piksel zostal trafiony przez jakikolwiek rodzaj pocisku (gracza lub wroga)
	public boolean hit(Bullet bullet) {
		return x < bullet.x + bullet.width && y < bullet.y + bullet.height
				&& bullet.x < x + width && bullet.y < y + height;
	}

	@Override
	public void update() {}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int) x, (int) y, width, height);
	}

}
