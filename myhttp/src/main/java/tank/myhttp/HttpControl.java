package tank.myhttp;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpRequestImpl;
import org.apache.mina.http.HttpResponseImpl;
import org.apache.mina.http.api.HttpRequest;
import org.apache.mina.http.api.HttpStatus;
import org.apache.mina.http.api.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import tank.myhttp.api.IHttpControl;
import tank.myhttp.api.IHttpHandler;

/**
 * @author tank
 * @date:2014-4-16 下午05:10:40
 * @description:
 * @version :
 */
@Controller
public class HttpControl implements IHttpControl {
	private static Logger log = LoggerFactory.getLogger(HttpControl.class);

	@Override
	public HttpResponseImpl handler(IoSession session, HttpRequest httpRequest) {
		HttpResponseImpl response;
		HttpRequestImpl request = (HttpRequestImpl) httpRequest;
		IHttpHandler handler = HttpAppContext.getHttpHandler(request.getRequestPath());
		if (handler != null) {
			// 映射请求参数
			Map<String, String> params = getSimpleParameters(request);
			Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				String key = entry.getKey();
				try {
					org.apache.commons.beanutils.BeanUtils.setProperty(handler, key, entry.getValue());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					log.warn("设置参数异常{}", e);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					log.warn("设置参数异常{}", e);
				}
			}

			try {
				return handler.handler(session, request);
			} catch (HttpMsgException e) {
				e.printStackTrace();
				log.error("{}", e);
				response = ResponseFactory.getResponse();
				response.appendBody(e.toString());
				return response;
			}
		} else {
			log.error("找不到相应的处理handler:{}", request.getRequestPath());
			response = new HttpResponseImpl(HttpVersion.HTTP_1_1, HttpStatus.CLIENT_ERROR_BAD_REQUEST, HttpHeaders.getHanders());
			return response;
		}

	}

	public Map<String, String> getSimpleParameters(HttpRequestImpl request) {
		Map<String, String> parameters = new HashMap<String, String>();
		String[] params = request.getQueryString().split("&");
		if (params.length == 1) {
			return parameters;
		}
		for (int i = 0; i < params.length; i++) {
			String[] param = params[i].split("=");
			String name = param[0];
			String value = param.length == 2 ? param[1] : "";

			parameters.put(name, value);
		}
		return parameters;
	}

}
