import java.util.concurrent.Semaphore;

public class DBWorker {
	
	static DBWorker instance;
	static Semaphore s;
	
	public DBWorker(){
		if(instance==null){
		s = new Semaphore(1);
		instance = this;
		}else{
			System.out.println("DBWORKER ERROR");
		}
		
	}
	
	public void test(MarketData d, int nr) throws InterruptedException{
		s.acquire();
			System.out.println(nr);
			d.print();
		s.release();	
			
	}
	
	public static DBWorker getObject(){
		return instance;
	}
	
}
