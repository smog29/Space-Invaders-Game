package bullets;

import java.awt.Color;
import java.awt.Graphics;

import entities.Player;
import flags.Flag;
import sounds.PlaySound;
import sounds.SoundAssets;
import states.GameState;

public class EnemyBullet extends Bullet{

	//konstruktor z domyslna szybkoscia pocisku
	public EnemyBullet(float x, float y, GameState state) {
		super(x, y, 4.5f, 3, 15,state); //x,y, ySpeed, width, height
	}
	
	//konstruktor z dowolna szybkoscia pocisku
	public EnemyBullet(float x, float y, GameState state, float ySpeed) {
		super(x,y, ySpeed, 3, 15, state);
	}
	
	@Override
	public void update() {
		y += ySpeed;
		
		//sprawdzanie czy pocisk trafil gracza
		if(state.getEM().getPlayer().getX() < x && state.getEM().getPlayer().getX() + state.getEM().getPlayer().width > x &&
				state.getEM().getPlayer().getY() < y + (float) height/2 &&
				state.getEM().getPlayer().getY() + state.getEM().getPlayer().height > y + (float) height/2) {

			//sprawdzanie czy nie jest wlaczony tryb do testowania, albo czy gracz nie jest juz trafiony
			if(!Player.PASSIVE_MODE && state.getEM().getPlayer().getFlag() == null)
				state.getScore().subtractHealth(); //zmniejszanie zycia gracza jest zostal trafiony przez pocisk

			// gracz jest trafiony
			if(state.getEM().getPlayer().getFlag() == null){
				state.getEM().getPlayer().setFlag(new Flag(2000));

				PlaySound.play(SoundAssets.playerHit);

				state.getEM().getEnemiesController().bullets.remove(this);
			}
		}
			
		//usuwanie pocisku jesli wyszedl poza ekran
		if(y >= state.height + 50)
			state.getEM().getEnemiesController().bullets.remove(this);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		
		g.fillRect((int) x, (int) y, width, height);
	}

}
