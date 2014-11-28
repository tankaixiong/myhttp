package tank.myhttp;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.http.DateUtil;

/**
		* @author tank
		* @date:2014-4-16 上午11:38:56
		* @description:
		* @version :
		*/
public class HttpHeaders {

	public static Map<String, String> getHanders() {
		Map<String, String> headers = new HashMap<String, String>();

		headers.put("Server", "slime HttpServer ");
		headers.put("Cache-Control", "private");
		headers.put("Content-Type", "application/json; charset=utf-8");
		headers.put("Connection", "keep-alive");
		headers.put("Keep-Alive", "timeout=5, max=99");

		headers.put("Date", DateUtil.getCurrentAsString());//Wed, 16 Apr 2014 08:55:05 GMT
		headers.put("Expires", "-1");
		//Last-Modified和Expires针对浏览器,你要设置了会死人的

		return headers;
	}
	
	
	public static Map<String, String> getHandersTextHtml() {
		Map<String, String> headers = new HashMap<String, String>();

		headers.put("Server", "slime HttpServer ");
		headers.put("Cache-Control", "private");
		headers.put("Content-Type", "text/html; charset=utf-8");
		headers.put("Connection", "keep-alive");
		headers.put("Keep-Alive", "timeout=5, max=99");

		headers.put("Date", DateUtil.getCurrentAsString());//Wed, 16 Apr 2014 08:55:05 GMT
		headers.put("Expires", "-1");
		//Last-Modified和Expires针对浏览器,你要设置了会死人的

		return headers;
	}

}
