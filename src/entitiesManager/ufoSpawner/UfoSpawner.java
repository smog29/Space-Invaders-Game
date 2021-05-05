package entitiesManager.ufoSpawner;

import entities.Ufo;
import sounds.PlaySound;
import sounds.SoundAssets;
import states.GameState;

import java.awt.*;

public class UfoSpawner {

    private Ufo ufo = null;

    private final GameState state;

    //prawdopodobienstwo pojawienia sie ufa
    private final double probability = 0.6;

    private long beforeTime;
    private long deltaTime;

    // co 15 sekund proba spawnowania ufa
    private final int delay = 15000;

    public UfoSpawner(GameState state){
        this.state = state;

        deltaTime = 0;
        beforeTime = System.currentTimeMillis();
    }

    public void update(){
        if(ufo != null){
            ufo.update();

            if(ufo.isDestroyed()){
                ufo = null;
            }
        }

        deltaTime += System.currentTimeMillis() - beforeTime;
        beforeTime = System.currentTimeMillis();

        // proba spawnowania ufa co jakis czas
        if(deltaTime >= delay) {
            if(ufo != null)
                return;

            // spawnowanie ufa z prawej lub lewej strony
            if(Math.random() < probability){
                if(Math.random() < 0.5)
                    ufo = new Ufo(-50, 100, true, state);
                else
                    ufo = new Ufo(state.width + 50, 100, false, state);

                PlaySound.play(SoundAssets.ufo);
            }
           deltaTime = 0;
        }
    }


    public void render(Graphics g){
        if(ufo != null)
            ufo.render(g);
    }
}
