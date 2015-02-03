import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class DataParserThreadPool {
	
	public static DataParserThreadPool instance;
	public static int poolSize;
	//oldestThread is the Thread that is most likely finished by now
	public static int oldestThread;
	private static DataParser[] pool;
	
	public static Lock poolSelectorLock;

	public DataParserThreadPool(int size){
		
		DataParserThreadPool.poolSelectorLock = new ReentrantLock();

		DataParserThreadPool.poolSize = size;
		DataParserThreadPool.instance = this;
		DataParserThreadPool.pool = new DataParser[size];
		for(int i=0; i<size; i++){
			pool[i] = new DataParser(i);
			Thread t = new Thread( pool[i]);
			t.start();
		}
		DataParserThreadPool.oldestThread=0;
	}
	/**
	 * Stop all running Threads
	 */
	public void cleanup() {
		for(int i=0;i<poolSize;i++){
			poolSelectorLock.lock();
			pool[i].stop();
			pool[i].condition.signal();
			poolSelectorLock.unlock();
		}
	}
	
	/**
	 * Submit a job to the longest inactive DataParser thread.
	 * 
	 * @param jsonMessage Decompressed EDDN data message
	 */
	public void sumbitJob(String jsonMessage){
		//TODO Change busy-flag to semaphore
		
		
		while(pool[oldestThread].isBusy()){
			oldestThread = (oldestThread + 1) % poolSize;
		}
		poolSelectorLock.lock();
			pool[oldestThread].jsonMessage = jsonMessage;
			pool[oldestThread].condition.signal();
			oldestThread = 0;
		poolSelectorLock.unlock();
	}
	

}
