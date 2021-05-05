package entities;

import difficulty.DifficultyLevels;
import graphics.Animation;
import graphics.Assets;
import states.GameState;

public class Headcrab extends Enemy{

    public Headcrab(float x, float y, int id, GameState state, int level) {
        super(x, y, id, state, level);

        animation = new Animation(Assets.headcrab, 1000); // zmiana co sekunde

        probabilityFactor = 1.08;
    }

    @Override
    protected void addPoints() {
        //dodawanie punktow w zaleznosci od poziomu trudnosci
        switch (DifficultyLevels.currentDifficulty) {
            case EASY -> state.getScore().addScore(80);
            case MEDIUM -> state.getScore().addScore(100);
            case HARD -> state.getScore().addScore(120);
        }
    }


    public void test(){

    }
}
