import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class DataParser implements Runnable{

	private int poolID;
	private Lock lock;
	public Condition condition;
	public boolean running;
	
	public String jsonMessage;
	
	
	public DataParser(int id){
		this.poolID = id;
		this.lock = DataParserThreadPool.poolSelectorLock;
		this.condition = lock.newCondition();
		this.running = true;
	}
	
	/**
	 * Main loop for a DataParser-thread.
	 * While the running flag is set, the Thread waits for a signal.
	 * Receiving a signal starts parsing the message stored in the field jsonMessage.
	 * 
	 * TODO Give the resulting MarketData to DBWorker to save it.
	 */
	public void run() {
		while(this.running){
			
			this.lock.lock();
			try {
			
				this.condition.await();
			} catch (InterruptedException e) {
			}
			//Do the stuff below!
			if(this.running){
				//System.out.println(this.poolID + " " + this.jsonMessage);
				
				//clean message:
				this.jsonMessage = null;
			}
			//===================
			this.lock.unlock();

		}
	}

	
	/**
	 * Parses the jsonMessage field
	 * @return MarketData-Object containing all market information 
	 * @see    MarketData
	 */
	private MarketData parseJSON(){
		return new MarketData();
	}

}
