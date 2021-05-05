package entities;

import difficulty.DifficultyLevels;
import graphics.Animation;
import graphics.Assets;
import states.GameState;

public class Octopus extends Enemy{

	public Octopus(float x, float y, int id, GameState state, int level) {
		super(x, y, id, state, level);

		animation = new Animation(Assets.octopus, 1000); // zmiana co sekunde

		probabilityFactor = 1.06;
	}

	@Override
	protected void addPoints() {
		//dodawanie punktow w zaleznosci od wybranego poziomu trudnosci
		switch (DifficultyLevels.currentDifficulty) {
			case EASY -> state.getScore().addScore(70);
			case MEDIUM -> state.getScore().addScore(90);
			case HARD -> state.getScore().addScore(110);
		}
	}
}
