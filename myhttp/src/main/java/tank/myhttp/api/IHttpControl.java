package tank.myhttp.api;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpResponseImpl;
import org.apache.mina.http.api.HttpRequest;

/**
 * @author tank
 * @date:2014-4-16 下午05:13:57
 * @description:
 * @version :
 */
public interface IHttpControl {
	HttpResponseImpl handler(IoSession session, HttpRequest request);

}
