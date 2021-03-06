import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DataParser implements Runnable{

	private int poolID;
	private Lock lock;	
	private DBWorker db;
	public Condition condition;
	private boolean running;
	public String jsonMessage;
	
	
	public DataParser(int id){
		this.poolID = id;
		this.lock = DataParserThreadPool.poolSelectorLock;
		this.condition = lock.newCondition();
		this.running = true;
		this.db = DBWorker.getObject();
	}
	
	/**
	 * Main loop for a DataParser-thread.
	 * While the running flag is set, the Thread waits for a signal.
	 * Receiving a signal starts parsing the message stored in the field jsonMessage.
	 * 
	 * TODO Give the resulting MarketData to DBWorker to save it.
	 * TODO Change Busy-Flag to Semaphore
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
				MarketData data;
				data = fillMarketData();
				if(data!=null){
					//data.print();
					//give to DBWorker queue
					try {

						db.test(data, this.poolID);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				if(this.poolID==0){
				try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
				//clean message:
				this.jsonMessage = null;
				System.out.println("Worked "+this.poolID);
			}
			//===================
			this.lock.unlock();

		}
	}

	/**
	 * 
	 * @return MarketData object containing the market prices
	 */
	private MarketData fillMarketData() {
		if(this.jsonMessage==null){return null;}
		
		MarketData data = new MarketData();
		JSONTokener tokener = new JSONTokener(this.jsonMessage);
		JSONObject root = new JSONObject(tokener);
		JSONObject body = root.getJSONObject("message");
		try{
			data.systemName = body.getString("systemName");
			data.stationName = body.getString("stationName");
			data.supply = body.getInt("stationStock");
			data.itemName = body.getString("itemName");
			data.timestamp = body.getString("timestamp");
			data.sellPrice = body.getInt("sellPrice");
			data.demand = body.getInt("demand");
		}catch(JSONException e){
			// TODO implement logger functionality
			System.out.println("Invalid EDDN data: "+e.getMessage());
			return null;
		}
		try {
			data.supplyLevel = body.getString("supplyLevel");
		} catch (JSONException e) {
			data.supplyLevel = null;
		}
		try{
			data.demandLevel = body.getString("demandLevel");
		}catch(JSONException e){
			data.demandLevel = null;
		}
		try{
			data.buyPrice = body.getInt("buyPrice");	
		}catch(JSONException e){
			data.buyPrice = -1;
		}

		if(data.validate()){
			return data;
		}else{
			return null;
		}

	}
	
	public int getPoolID(){
		return this.poolID;
	}
	
	public void stop(){
		this.running=false;
	}

	
}
