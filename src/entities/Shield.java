package entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import states.GameState;

public class Shield extends Entity {
	
	private GameState state;
	
	// licznik ile pikseli jest zniszczonych
	private int destroyed;

	private List<List<Pixel>> shield = new ArrayList<>();

	public Shield(float x, float y, GameState state, byte[][] shieldLook) {
		super(x, y,20,15);
		this.state = state;
		
		// tymczasowy obiekt do odczytania wysokosci i szerokosci
		Pixel temp = new Pixel(0, 0);

		// zczytywanie z tablicy zawiarajaca dane z pliku tekstowego
		// jesli 1 to obiekt sie rysuje
		for (int i = 0; i < width; i++) {
			List<Pixel> list = new ArrayList<>();
			for (int j = 0; j < height; j++) {
				if (shieldLook[j][i] == 1)
					list.add(new Pixel(i * temp.height + x, j * temp.width + y));
			}
			shield.add(list);
		}
		destroyed = 0;
	}

	@Override
	public void update() {
		
		//sprawdzanie kolizji tarcza - pocisk
		for (List<Pixel> list : shield) {

			Iterator<Pixel> it = list.iterator();

			while (it.hasNext()) {
				Pixel pixel = it.next();

				// kolizja pocisku gracza
				for (int i = 0; i < state.getEM().getPlayer().pBullets.size(); i++) {
					if (pixel.hit(state.getEM().getPlayer().pBullets.get(i))) {
 
						// usuwanie piksela
						it.remove();
						destroyed++;

						// usuwanie pocisku - po 5 trafionych
						if (destroyed >= 5) {
							state.getEM().getPlayer().pBullets.remove(state.getEM().getPlayer().pBullets.get(i));
							destroyed = 0;
						}
						break; //wychodzenie z petli zeby sprawdzic kolizje innych pikseli
					}
				}

				// kolizja pocisku wrogow
				for (int i = 0; i < state.getEM().getEnemiesController().bullets.size(); i++) {
					if (pixel.hit(state.getEM().getEnemiesController().bullets.get(i))) {
					
						// usuwanie piksela
						it.remove();
						destroyed++;

						// usuwanie pocisku - po 5 trafionych
						if (destroyed >= 5) {
							state.getEM().getEnemiesController().bullets.remove(state.getEM().getEnemiesController().bullets.get(i));
							destroyed = 0;
						}
						break; //wychodzenie z petli zeby sprawdzic kolizje innych pikseli
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		for(List<Pixel> list : shield)
			for(Pixel pixel : list)
				pixel.render(g);
	}

}
