package tank.myhttp.api;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpRequestImpl;
import org.apache.mina.http.HttpResponseImpl;

/**
		* @author tank
		* @date:2014-4-16 下午05:13:57
		* @description:
		* @version :
		*/
public interface IHttpControl {
	HttpResponseImpl handler(IoSession session, HttpRequestImpl message);

	void init(IoSession session);

	void onSessionClosed(IoSession session);
}
