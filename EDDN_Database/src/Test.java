
import java.util.zip.*;
import java.io.*;

import org.zeromq.ZMQ;
 
//
// Hello World server in Java
// Binds REP socket to tcp://*:5555
// Expects "Hello" from client, replies with "World"
//
 
public class Test {
 
    /**
     * @param args standard params
     * @throws DataFormatException  you may die
     * @throws IOException tonight
     */
    public static void main(String[] args) throws DataFormatException, IOException {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket sub = context.socket(ZMQ.SUB);
        sub.subscribe(new byte[0]);
        sub.connect("tcp://eddn-relay.elite-markets.net:9500");
        
        while(true){
        	byte[] raw = sub.recv();
        	Inflater decompress = new Inflater();
        	decompress.setInput(raw);
        	byte[] dec = new byte[9999];
        	decompress.inflate(dec);
        	System.out.println(new String(dec));
        }
    }
 
}