package entities;

import bullets.Bullet;
import game.Game;
import graphics.Assets;
import states.GameState;

import java.awt.*;

public class Ufo extends Entity{

    private final GameState state;

    //ustala w ktora strone porusza sie ufo
    private boolean direction;

    private float speed = 4f;

    //punkty za zestrzelenie ufa
    private final int points = 200;

    // zmienna okreslajaca czy ufo jest zniszczone
    private boolean destroyed = false;

    public Ufo(float x, float y, boolean direction, GameState state) {
        super(x, y,75,40);

        this.state = state;
        this.direction = direction;
    }

    private boolean hit(Bullet bullet){
        return x < bullet.x + bullet.width && y < bullet.y + bullet.height
                && bullet.x < x + width && bullet.y < y + height;
    }

    @Override
    public void update() {
        //poruszanie sie w prawo
        if(direction){
            x += speed;
        }
        //w lewo
        else{
            x -= speed;
        }

        //kolizja z pociskami gracza
        for(int i = 0 ; i < state.getEM().getPlayer().pBullets.size() ; i++){
            if(hit(state.getEM().getPlayer().pBullets.get(i))){
                state.getScore().addScore(points);
                state.getEM().getPlayer().pBullets.remove(state.getEM().getPlayer().pBullets.get(i));

                destroyed = true;
            }
        }

        //sprawdzanie czy ufo nie wylecialo poza ekran
        if(x > state.width + 100 || x < -100){
            destroyed = true;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ufo, (int) x, (int) y, width, height, null);
    }


    public boolean isDestroyed(){return destroyed;}
}
