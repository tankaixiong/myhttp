package org.apache.mina.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.http.api.DefaultHttpResponse;
import org.apache.mina.http.api.HttpStatus;
import org.apache.mina.http.api.HttpVersion;

/**
		* @author tank
		* @date:2014-4-16 ÏÂÎç12:09:15
		* @description:
		* @version :
		*/
public class HttpResponseImpl extends DefaultHttpResponse {

	/** Storage for body of HTTP response. */
	private final ByteArrayOutputStream body = new ByteArrayOutputStream(1024);

	public HttpResponseImpl(HttpVersion version, HttpStatus status, Map<String, String> headers) {
		super(version, status, headers);
		// TODO Auto-generated constructor stub
	}

	public void appendBody(byte[] b) {
		try {
			body.write(b);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void appendBody(String s) {
		try {
			body.write(s.getBytes());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public IoBuffer getBody() {
		return IoBuffer.wrap(body.toByteArray());
	}

	public int getBodyLength() {
		return body.size();
	}

}
