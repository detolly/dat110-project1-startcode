package no.hvl.dat110.system.display;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCImpl;
import no.hvl.dat110.rpc.RPCUtils;

public class DisplayImpl implements RPCImpl {

	public void write(String message) {
		System.out.println("DISPLAY:" + message);
	}
	
	public byte[] invoke(byte[] request) {

		byte rpcid = request[0];

		String param = RPCUtils.unmarshallString(request);

		write(param);

		byte[] reply = RPCUtils.marshallVoid(rpcid);
		
		return reply;
	}
}
