import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.zeromq.ZMQ;


public class EDDNListener implements Runnable {

	public void run() {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket sub = context.socket(ZMQ.SUB);
        sub.subscribe(new byte[0]);
        sub.connect("tcp://eddn-relay.elite-markets.net:9500");
        
        while(true){
        	byte[] raw = sub.recv();
        	Inflater decompress = new Inflater();
        	decompress.setInput(raw);
        	byte[] dec = new byte[1000];
        	try {
				decompress.inflate(dec);
				DataParserThreadPool.instance.sumbitJob(new String(dec));
				
			} catch (DataFormatException e) {
			}
        	
        	
        }
		
	}

}
