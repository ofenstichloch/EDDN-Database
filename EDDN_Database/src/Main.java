
public class Main {

	/**
	 * @author alexanderlandmesser
	 *
	 * Main 
	 * 		@see EDDNListener
	 * 			Listens to EDDN messages
	 * 			Decompresses messages to JSON string
	 * 			Passes JSON to a DataParser Thread from ThreadPool
	 * 		@see DataParser
	 * 			Parses JSON data
	 * 			Checks for invalid data
	 * 			Passes data to DBWorker queue to be saved in DB
	 * 		@see DBWorker
	 * 			(Single Thread, has Job-queue it works on)
	 * 			Gets jobs from DataParse to save
	 * 			save to MySQL
	 */
	
	/**
	 * 
	 * 
	 * @param args standard arguments
	 */
	public static void main(String[] args) {
		//Create DataParserThreadPool
		DataParserThreadPool pool = new DataParserThreadPool(10);
		System.out.println("Threadpool created: "+DataParserThreadPool.poolSize+" Threads");
		// TODO Create DBWorker
		//Create EDDNListener
		EDDNListener listener = new EDDNListener();
		Thread t = new Thread(listener);
		t.run();
		

		//Bring it to an end!
		pool.cleanup();
	
	}

}
