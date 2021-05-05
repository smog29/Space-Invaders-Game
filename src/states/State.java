package states;

import java.awt.Graphics;

//klasa przechowuje aktualny stany gry (przegrana, ekran startowy, w trakcie gry)
public abstract class State {

	//statyczna czesc klasy
	public static State currentState = null;
	
	
	//abstrakcyjna czesc klasy
	public State() {}

	public abstract void update();
	
	public abstract void render(Graphics g);
	
}
