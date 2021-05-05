package flags;



public class Flag {

	private int time;
	
	private long timePassed;
	private long beforeTime;
	
	private boolean running;
	
	public Flag(int time) {
		this.time = time;
		
		running = true;
		timePassed = 0;
		
		beforeTime = System.currentTimeMillis();
	}
	
	//odliczanie czy minelo tyle czasu ile podane jest w konstruktorze - jesli tak to nic juz sie w klasie nie dzieje
	public void update() {
		if(!running) {
			return;
		}
		
		timePassed += System.currentTimeMillis() - beforeTime;
		beforeTime = System.currentTimeMillis();
		
		if(timePassed >= time) {
			running = false;
		}
	}
	
	public boolean isRunning() {
		return running;
	}

}
