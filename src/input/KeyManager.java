package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	private boolean[] keys;
	
	public static boolean left, right, space;
	public static boolean R;
	
	public static boolean keyHeld = false;
	
	public KeyManager() {
		keys = new boolean[256];
		left = false;
		right = false;
		space = false;
		
		R = false;
	}
	
	public void update() {
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		space = keys[KeyEvent.VK_SPACE];
		
		R = keys[KeyEvent.VK_R];
		
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = false;
		
		keyHeld = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}

}
