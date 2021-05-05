 package entities;

import difficulty.DifficultyLevels;
import graphics.Animation;
import graphics.Assets;
import states.GameState;

public class Monster extends Enemy{

	public Monster(float x, float y, int id, GameState state, int level) {
		super(x, y, id, state, level);
		
		animation = new Animation(Assets.monster, 1000); //zmiana animacji potwora co sekunde

		probabilityFactor = 1.04;
	}

	@Override
	protected void addPoints() {
		//dodawanie punktow w zaleznosci od poziomu trudnosci
		switch (DifficultyLevels.currentDifficulty) {
			case EASY -> state.getScore().addScore(60);
			case MEDIUM -> state.getScore().addScore(80);
			case HARD -> state.getScore().addScore(100);
		}
	}
}
