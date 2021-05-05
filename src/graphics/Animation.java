package graphics;

import java.awt.image.BufferedImage;

public class Animation {

	//indeks animacji
	private int index;

	//co ile ma zmieniac sie animacja
	private int speed;

	private long timer, beforeTime;
	
	//tablica ze zdjeciami animacji
	private final BufferedImage[] frame;

	
	public Animation(BufferedImage[] frame, int speed) {
		this.frame = frame;
		this.speed = speed;
		
		index = 0;
		timer = 0;
		
		beforeTime = System.currentTimeMillis();
	}

	public void update() {
		//sprawdza ile czasu minelo od ostatniego updatu
		timer += System.currentTimeMillis() - beforeTime;
		beforeTime = System.currentTimeMillis();
		
		//jesli minelo tyle czasu co ile ma sie zmieniac animacja to zwieksza indeks
		if(timer >= speed) {
			index++;
			
			timer = 0;
		}
		
		//resetowanie indexu jesli wyszedl poza tablice
		if(index >= frame.length) {
			index = 0;
		}
	}

	
	public void changeSpeed(int x) {
		speed = x;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	
	public BufferedImage getAnimation() {
		return frame[index];
	}
}
