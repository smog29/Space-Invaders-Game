package entities;


import java.awt.Graphics;

import bullets.EnemyBullet;
import graphics.Animation;
import graphics.Assets;
import flags.Flag;
import states.GameState;

//abstrakcyjna klasa, zawierajaca domyslne ustawienia kazdego wroga, z ktorej moze dziedziczyc kazdy rodzaj wroga
public abstract class Enemy extends Entity {

	//zmienna okresla czy przeciwnik jest widoczny
	//dla efektu tworzenia przeciwnikow po kolei
	public boolean isVisible;

	//zmienne do pokazania animacji kiedy przeciwnik jest zestrzelony
	protected Flag deathFlag = null;

	// numer rzedu wroga do sprawdzania czy jest ostatni w rzedzie
	protected int id;

	protected GameState state; //referencja do stanu gry

	public boolean canShoot; //zmienna bool ktora ustala czy przeciwnik moze strzelac
	protected double probability; // prawdopodobienstwo oddania strzalu
	
	protected double probabilityFactor; //wspolczynnik zwiekszania prawdopodobienstwa
	
	//animacja przeciwnika
	public Animation animation;

	public Enemy(float x, float y, int id, GameState state, int level) {
		super(x, y, 40, 30);

		this.id = id;
		this.state = state;

		canShoot = false; 
		probability = 0.004 + level / 1000.;

		isVisible = false;
	}

	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	//sprawdza czy potwor jest trafiony przez pocisk gracza
	private boolean hit() {
		for(int i = 0 ; i < state.getEM().getPlayer().pBullets.size() ; i++)
			if(state.getEM().getPlayer().pBullets.get(i).x > x && state.getEM().getPlayer().pBullets.get(i).x < x + width &&
					state.getEM().getPlayer().pBullets.get(i).y > y && state.getEM().getPlayer().pBullets.get(i).y < y + height) {

				state.getEM().getPlayer().pBullets.remove(state.getEM().getPlayer().pBullets.get(i)); //usuwanie pocisku po trafieniu
				return true;
			}
		return false;
	}
	
	//dodawanie punktow za trafienie przeciwnika
	protected abstract void addPoints();
	
	//zmienia prawdopodobienstwo w zaleznosci od typu wroga
	public void changeProbability() {
		probability *= probabilityFactor;
	}
	
	
	@Override
	public void update() {

		if (deathFlag != null) {
			deathFlag.update();

			if (!deathFlag.isRunning()) {
				state.getEM().getEnemiesController().enemies.remove(this); // usuwanie przeciwnika
			}
			return;
		}

		// sprawdza czy potwor zostal trafiony przez pocisk
		if (hit()) {
			addPoints();
			
			state.getEM().getEnemiesController().enemiesSizeChanged(); //zmiana ilosci przeciwnikow
			
			deathFlag = new Flag(500); //animacja eksplozji na pol sekundy po smierci potwora
			return;
		}

		// stworzenie pocisku przez potwora
		if (canShoot) {
			if (Math.random() < probability)
				state.getEM().getEnemiesController().bullets.add(new EnemyBullet(x + (float) width / 2, y + height, state));
		}
		
		//update animacji
		animation.update();
	}

	
	@Override
	public void render(Graphics g) {
		//rysowanie animacji trafienia jesli przeciwnik jest trafiony
		if(deathFlag != null)
			g.drawImage(Assets.explosion, (int) x, (int) y, width, height, null);

		//rysowanie obecnej animacji wroga
		else
			g.drawImage(animation.getAnimation(), (int) x, (int) y, width, height, null);
	}

	
	
	//getters
	public int getId() {
		return id;
	}

}
