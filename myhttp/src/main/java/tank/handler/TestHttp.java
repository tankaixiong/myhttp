package tank.handler;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpRequestImpl;
import org.apache.mina.http.HttpResponseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import tank.myhttp.HttpMsgException;
import tank.myhttp.ResponseFactory;
import tank.myhttp.api.IHttpHandler;

/**
 * @author tank
 * @date:1 Dec 2014 09:56:45
 * @description:
 * @version :1.0
 */
@Controller
public class TestHttp implements IHttpHandler {
	private Logger logger = LoggerFactory.getLogger(TestHttp.class);

	private String id;
	private String name;

	@Override
	public String getUrl() {

		return "/test.do";
	}

	@Override
	public HttpResponseImpl handler(IoSession session, HttpRequestImpl request) throws HttpMsgException {

		HttpResponseImpl response = ResponseFactory.getResponse();

		response.appendBody("{\"id\":123,\"name\":\"tank\"}");

		logger.info("参数:{}", this.getId());
		logger.info("参数:{}", this.getName());
		

		return response;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
