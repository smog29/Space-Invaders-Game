package bullets;

import java.awt.Graphics;

import customization.PlayerColor;
import difficulty.DifficultyLevels;
import graphics.Assets;
import flags.Flag;
import states.GameState;


public class PlayerBullet extends Bullet{

	//animacja zniszczenia pocisku
	private Flag destroyed = null;


	public PlayerBullet(float x, float y, GameState state) {
		super(x, y, 10f, 5, 15,state); //x, y, ySpeed, width, height
	}
	
	// sprawdza czy picisk trafil ktorys z pociskow wroga
	public boolean hitBullet() {
		for(EnemyBullet bullet : state.getEM().getEnemiesController().bullets) {
			if(x < bullet.x + width && x > bullet.x - width && y <= bullet.y && y - height < bullet.y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void update() {
		if(destroyed != null){
			destroyed.update();

			if(!destroyed.isRunning()){
				state.getEM().getPlayer().pBullets.remove(this); //usuwanie pocisku
			}
			return;
		}

		y -= ySpeed;
		
		//sprawdza czy pocisk wylecial poza ekran i odejmuje punkty za nie trafienie potwora
		if(y < -50) {
			switch (DifficultyLevels.currentDifficulty) {
				case EASY -> state.getScore().addScore(-20);
				case MEDIUM -> state.getScore().addScore(-30);
				case HARD -> state.getScore().addScore(-40);
			}
			
			state.getEM().getPlayer().pBullets.remove(this); //usuwanie pocisku
		}
		
		if(hitBullet()) {
			destroyed = new Flag(500); //animacja przez pol sekundy
		}
	}

	@Override
	public void render(Graphics g) {
		if(destroyed != null){
			g.drawImage(Assets.explosion, (int) x, (int) y, width * 4, height, null);
			return;
		}

		g.setColor(PlayerColor.getJavaColor()); //ustawianie koloru pocisku takiego jak kolor gracza
		
		g.fillRect((int) x, (int) y, width, height);
	}
	
	

}
