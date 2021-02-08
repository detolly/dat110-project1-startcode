package no.hvl.dat110.rpc;

import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.Connection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private Connection connection;
	
	// hashmap to register RPC methods which are required to implement RPCImpl
	
	private HashMap<Byte, RPCImpl> services;
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Byte, RPCImpl>();
		
		// the stop RPC methods is built into the server
		services.put(RPCCommon.RPIDSTOP,new RPCServerStopImpl());
	}
	
	public void run() {
		
		System.out.println("RPC SERVER RUN - Services: " + services.size());
		
		connection = msgserver.accept(); 
		
		System.out.println("RPC SERVER ACCEPTED");
		
		boolean stop = false;
		
		while (!stop) {
			Message m = connection.receive();
			byte[] data = m.getData();
			byte rpcid = data[0];
			switch(rpcid) {
				case RPCCommon.RPIDSTOP:
					stop = true;
					continue;
				default:
					if (services.containsKey(rpcid)) {
						byte[] response = services.get(rpcid).invoke(data);
						Message response_message = new Message(response);
						connection.send(response_message);
					} else {
						System.out.println("Trying to call uninvokable method. Ignoring.");
					}
					break;

			}
		}
	}
	
	public void register(byte rpcid, RPCImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {
		connection.close();
		msgserver.stop();
	}
}
