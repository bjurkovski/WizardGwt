package wizardGwt.client;

import com.google.gwt.user.client.Timer;

public abstract class AsyncCall extends Timer {
	private int PERIOD = 200;
	private int MAX_RECALLS = 100;
	private int numCalls = 0;
	
	public AsyncCall() {
		schedule(PERIOD);
	}
	
	public AsyncCall(int period) {
		PERIOD = period;
		schedule(PERIOD);
	}
	
	public AsyncCall(int period, int maxRecalls) {
		PERIOD = period;
		MAX_RECALLS = maxRecalls;
		schedule(PERIOD);
	}
	
	@Override
	public void run() {
		try { process(); }
		catch(Exception e) {
			if(numCalls < MAX_RECALLS) {
				numCalls++;
				schedule(PERIOD);
			}
		}
	}
	
	public abstract void process() throws Exception;
}
