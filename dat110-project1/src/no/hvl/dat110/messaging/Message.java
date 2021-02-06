package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

public class Message {

	private byte[] payload;

	public Message(byte[] payload) {
		this.payload = payload; // TODO: check for length within boundary
	}

	public Message() {
		super();
	}

	public byte[] getData() {
		return this.payload; 
	}

	public byte[] encapsulate() {

		assert(payload.length < 128);
		byte size = (byte)payload.length;

		byte[] encoded = new byte[128];
		encoded[0] = size;

		for(int i = 1; i < size+1; i++)
		{
			encoded[i] = payload[i-1];
		}

		return encoded;
		
	}

	public void decapsulate(byte[] received) {

		byte size = received[0];
		this.payload = new byte[size];
		for(int i = 0; i < size; i++) {
			payload[i] = received[i+1];
		}

	}
}
