package entities;

import java.awt.Graphics;
import java.util.LinkedList;

import bullets.PlayerBullet;
import customization.PlayerColor;
import flags.Flag;
import input.KeyManager;
import sounds.PlaySound;
import sounds.SoundAssets;
import states.GameState;

public class Player extends Entity{

	// tryb do testow, obrazenia dla gracza wylaczone
	public static final boolean PASSIVE_MODE = false;

	public LinkedList <PlayerBullet> pBullets = new LinkedList<>();

	private final GameState state;

	private Flag hit;
	
	//ustala po jakim czasie gracz moze strzelic 
	private int shootingTimer;
	private boolean canShoot;
						
			
	public Player(GameState state) {
		super((float) state.width / 2, state.height - 100, 40, 20); //domyslne ustawienie pozycji gracza
		this.state = state;
		
		shootingTimer = 0;
		canShoot = false;

		hit = null;
	}
	
	//ustala co ile gracz moze oddac strzac
	private void shootCheck() {
		//mozliwosc strzelania co pol sekundy
		if(shootingTimer >= 30) {
			shootingTimer = 0;
			canShoot = true;
			return;
		}
		
		if(!canShoot)
			shootingTimer++;
	}
	
	@Override
	public void update() {
		if(KeyManager.left)
			x -= 10;

		if (KeyManager.right)
			x += 10;

		shootCheck(); //aktualizuje mozliwosc oddania strzalu
		
		//tworzenie pocisku
		if (KeyManager.space && canShoot && !KeyManager.keyHeld && hit == null) {
			canShoot = false;
			
			pBullets.add(new PlayerBullet(x + (float) width / 2, y, state));
			
			PlaySound.play(SoundAssets.playerShoot); //dzwiek strzelania

			KeyManager.keyHeld = true;
		}

		if(hit != null){
			hit.update();

			if(!hit.isRunning())
				hit = null;
		}
			

		// update pociskow gracza
		for (int i = 0; i < pBullets.size(); i++)
			pBullets.get(i).update();

		// ustawienie granicy poruszania sie gracza
		if (x <= 0)
			x = 0;
		if (x + width >= state.width)
			x = state.width - width;
	}

	@Override
	public void render(Graphics g) {

		//rysowanie gracza
		if(hit == null)
			g.drawImage(PlayerColor.getPlayerAsset(), (int) x, (int) y, width, height, null);
		else{
			//Animacja jesli gracz jest trafiony, miganie co 1/6 sekundy
			if(state.getScore().getCounter() % 10 == 0){
				g.drawImage(PlayerColor.getPlayerAsset(), (int) x, (int) y, width, height, null);
			}
		}
		
		//rysowanie pociskow gracza
		for(PlayerBullet pBullet : pBullets)
			pBullet.render(g);
	}


	public void setFlag(Flag flag){
		hit = flag;
	}


	public Flag getFlag(){
		return hit;
	}

}
