package game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import display.Display;
import graphics.Assets;
import input.KeyManager;
import sounds.SoundAssets;
import states.StartingState;
import states.State;

public class Game implements Runnable{
	
	private Thread thread;
	
	private final String title;
	private final int width, height; //rozmiar okna
	
	//grafika
	private BufferStrategy bs;
	private Graphics g;
	
	//obiekty klas
	private Display display; //tworzenie okienka
	private KeyManager keys; //wykrywanie nacisnietych przyciskow
	
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}
	
	//inicjalizuje wszystkie objekty klas
	public void init() {
		display = new Display(title,width,height); //stworzenie okienka
		
		Assets.setAssets(); //ustawia assety
		SoundAssets.setSoundAssets(); //ustawia dzwieki
		
		//ustawienie sledzenia klawiszy
		keys = new KeyManager();
		display.getJFrame().addKeyListener(keys);
		
		
		State.currentState = new StartingState(width, height); //poczatkowy stan gry
	}
	
	
	public void update() {
		keys.update(); //sledzenie klawiszy
		
		//updatowanie obecnego stanu gry
		if(State.currentState != null)
			State.currentState.update();
	}
	
	public void render() {
		bs = display.getCanvas().getBufferStrategy();
		
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		//Czysci ekran po odswiezeniu
		g.clearRect(0, 0, width, height);
		//Poczatek renderowania
		
		if(State.currentState != null)
			State.currentState.render(g);
		
		//Koniec renderowania
		bs.show();
		g.dispose();
	}


	@Override
	public void run() {
		init();
		
		long beforeTime = System.nanoTime();
		long nowTime;
		double deltaTime = 0;
		
		double fps = 60;

		while(true) {
			nowTime = System.nanoTime();
			deltaTime += nowTime - beforeTime;
			
			beforeTime = nowTime;
			
			if(deltaTime >= 1000_000_000 / fps) { //updatowanie gry 60 razy na sekunde (60 fps)
				deltaTime = 0;

				update();
			}
			render();
		}
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		
		thread.start();
	}
	

}
