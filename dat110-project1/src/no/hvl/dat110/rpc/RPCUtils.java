package no.hvl.dat110.rpc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import no.hvl.dat110.TODO;

public class RPCUtils {

	// Utility methods for marshalling and marshalling of parameters and return values
	// in RPC request and RPC responses
	// data bytearrays and return byte arrays is according to the 
	// RPC message syntax [rpcid,parameter/return value]
	
	public static byte[] marshallString(byte rpcid, String str) {

		byte[] str_bytes = str.getBytes(StandardCharsets.UTF_8);

		byte[] encoded = new byte[str_bytes.length+1];

		encoded[0] = rpcid;
		for(int i = 0; i < str_bytes.length; i++) {
			encoded[i+1] = str_bytes[i];
		}

		return encoded;
	}

	public static String unmarshallString(byte[] data) {
		String decoded;
		decoded = new String(data, 1, data.length-1, StandardCharsets.UTF_8);
		return decoded;
	}

	public static byte[] marshallVoid(byte rpcid) {
		byte[] encoded = new byte[1];
		encoded[0] = rpcid;
		return encoded;
	}

	public static void unmarshallVoid(byte[] data) {

	}

	public static byte[] marshallBoolean(byte rpcid, boolean b) {

		byte[] encoded = new byte[2];

		encoded[0] = rpcid;

		if (b) {
			encoded[1] = 1;
		} else {
			encoded[1] = 0;
		}

		return encoded;
	}

	public static boolean unmarshallBoolean(byte[] data) {

		return (data[1] > 0);

	}

	public static byte[] marshallInteger(byte rpcid, int x) {
		byte[] encoded = new byte[5];
		encoded[0] = rpcid;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		os.write(x);
		byte[] b = os.toByteArray();
		for(int i = 1; i < b.length+1; i++) {
			encoded[i] = b[i-1];
		}
		return encoded;
	}

	public static int unmarshallInteger(byte[] data) {
		ByteArrayInputStream io = new ByteArrayInputStream(data);
		io.skip(1);
		return io.read();
	}
}
