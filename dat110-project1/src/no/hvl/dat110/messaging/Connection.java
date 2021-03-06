package no.hvl.dat110.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import no.hvl.dat110.TODO;

public class Connection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection

	public Connection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream(socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {
		byte[] bytes = message.encapsulate();
		try {
			outStream.write(bytes);
		} catch(IOException e) {
			System.out.println(e.getMessage());
			assert(false); // TODO: handle ASSERT_NOT_REACHED();
		}

	}

	public Message receive() {
		byte[] recvbuf = new byte[128];
		try {
			inStream.read(recvbuf);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			assert(false);
		}
		Message m = new Message();
		m.decapsulate(recvbuf);
		return m;
	}

	// close the connection by closing streams and the underlying socket
	public void close() {

		try {

			outStream.close();
			inStream.close();

			socket.close();
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}