package tank.myhttp;

import org.apache.mina.http.HttpResponseImpl;
import org.apache.mina.http.api.HttpStatus;
import org.apache.mina.http.api.HttpVersion;

/**
		* @author tank
		* @date:2014-4-16 下午06:12:08
		* @description:
		* @version :
		*/
public class ResponseFactory {

	public static HttpResponseImpl getResponse() {
		return new HttpResponseImpl(HttpVersion.HTTP_1_1, HttpStatus.SUCCESS_OK, HttpHeaders.getHanders());
	}

}
