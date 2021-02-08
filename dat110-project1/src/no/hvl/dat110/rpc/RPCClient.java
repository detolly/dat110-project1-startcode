package no.hvl.dat110.rpc;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.*;

public class RPCClient {

	private MessagingClient msgclient;
	private Connection connection;
	
	public RPCClient(String server, int port) {
		msgclient = new MessagingClient(server,port);
	}
	
	public void register(RPCStub remote) {
		remote.register(this);
	}
	
	public void connect() {
		connection = msgclient.connect();
	}
	
	public void disconnect() {
		//TODO: implement
	}
	
	public byte[] call(byte[] rpcrequest) {
		
		byte[] rpcreply;

		Message request = new Message(rpcrequest);
		connection.send(request);
		Message response = connection.receive();
		rpcreply = response.getData();
		
		return rpcreply;
		
	}

}
