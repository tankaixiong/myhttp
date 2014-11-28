package tank.myhttp;

import javax.annotation.Resource;

import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.HttpRequestImpl;
import org.apache.mina.http.HttpResponseImpl;
import org.apache.mina.http.api.HttpEndOfContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import tank.myhttp.api.IHttpControl;

/**
 * @author tank
 * @version :1.0
 * @date:Oct 24, 2012 10:21:07 AM
 * @description:消息处理器
 */
@Controller
public class HttpHandlerAdapter extends IoHandlerAdapter {
	private Logger log = LoggerFactory.getLogger(HttpHandlerAdapter.class);
	@Resource
	private IHttpControl control;

	/**
	 * 当一个客户端连接进入时
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.info("sessionOpened");
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
	}

	/**
	 * 当一个客户端关闭时
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("sessionClosed");
	}

	/**
	 * 异常时断开连接
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {

		log.error("服务端处理产生异常:{}", cause);
		session.close(false);
	}

	/**
	 * 当接收到客户端的信息
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		log.trace("messageReceived收到消息:{}", message);
		if (message instanceof HttpEndOfContent) {
			HttpEndOfContent endContent = (HttpEndOfContent) message;
			log.trace("收到HttpEndOfContent: {}", endContent);

		} else if (message instanceof HttpRequestImpl) {
			HttpRequestImpl request = (HttpRequestImpl) message;
			log.trace("收到HttpRequestImpl:{}", request.getRequestPath());
			HttpResponseImpl response = control.handler(session, request);
			if (response != null) {
				session.write(response).addListener(IoFutureListener.CLOSE);
			} else {
				log.error("响应给客户端为空");
			}
		}

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		session.close(false);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.trace("发送数据:{}", message);
		super.messageSent(session, message);
	}

}
