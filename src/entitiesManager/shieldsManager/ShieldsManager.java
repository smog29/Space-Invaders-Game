package entitiesManager.shieldsManager;


import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import difficulty.DifficultyLevels;
import entities.Shield;
import states.GameState;

public class ShieldsManager {
	
	private final GameState state;
	
	//ilosc oslon
	private Shield[] shields;
	
	//wyglad oslony pobierany z pliku
	private final byte[][] shieldLook;
	
	//wysokosc i szerokosc pojedynczej oslony
	public final int width = 20, height = 15;


	public ShieldsManager(GameState state) {
		this.state = state;

		shields = new Shield[3 - DifficultyLevels.currentDifficulty.ordinal()];
		
		shieldLook = new byte[height][width];
	
		try {
			setShieldLook();
		} catch (FileNotFoundException | NullPointerException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		init();
	}

	//tworzenie oslon
	private void init() {
		Shield left = new Shield((float) state.width / 2 - width - 300, 700, state,  shieldLook);
		Shield middle = new Shield((float) state.width / 2 - width, 700, state,  shieldLook);
		Shield right = new Shield((float) state.width / 2 - width + 300, 700, state,  shieldLook);

		switch (DifficultyLevels.currentDifficulty) {
			case EASY -> {
				shields[0] = left;
				shields[1] = middle;
				shields[2] = right;
			}
			case MEDIUM -> {
				shields[0] = left;
				shields[1] = right;
			}
			case HARD -> shields[0] = middle;
		}
	}

	// odczytywanie wygladu z pliku tekstowego
	private void setShieldLook() throws FileNotFoundException, NullPointerException {

		InputStream file = ShieldsManager.class.getClassLoader().getResourceAsStream("text/shield.txt");

		assert file != null;
		Scanner scan = new Scanner(file);

		for (int i = 0; i < height; i++) {
			String[] line = scan.nextLine().trim().split(" ");

			for (int j = 0; j < width; j++) {
				shieldLook[i][j] = Byte.parseByte(line[j]);
			}
		}

		scan.close();
	}

	public void update() {

		for (int i = 0; i < shields.length ; i++) {
			shields[i].update();
		}
	}
	
	public void render(Graphics g) {

		for(int i = 0 ; i < shields.length ; i++) {
			shields[i].render(g);
		}
	}
	

}
