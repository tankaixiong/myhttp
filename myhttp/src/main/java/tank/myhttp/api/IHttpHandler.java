package tank.myhttp.api;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpRequestImpl;
import org.apache.mina.http.HttpResponseImpl;

import tank.myhttp.HttpMsgException;

/**
 * @author tank
 * @date:2014-4-16 下午05:11:17
 * @description:
 * @version :
 */
public interface IHttpHandler {
	/**
	 * http URL映射到相应的类
	 * 
	 * @return
	 */
	public String getUrl();

	public HttpResponseImpl handler(IoSession session, HttpRequestImpl request) throws HttpMsgException;
}
