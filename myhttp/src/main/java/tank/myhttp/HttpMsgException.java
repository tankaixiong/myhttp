package tank.myhttp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tankaixiong
 * @version 1.0
 * @description 自定义业务逻辑异常类
 */
public class HttpMsgException extends Exception {
	private static final long serialVersionUID = -6893355986440178289L;
	protected Logger LOGGER = LoggerFactory.getLogger(HttpMsgException.class);

	private Ret errorCode;

	public HttpMsgException() {
		super("unknown error");
	}

	public HttpMsgException(Throwable cause) {
		super(cause);
		LOGGER.error("发生异常了: 异常信息:{}", errorCode);
	}

	public HttpMsgException(Ret errorCode) {
		this.errorCode = errorCode;
		LOGGER.error("发生异常了: 错误代码:{}", errorCode);
	}

	public HttpMsgException(Ret errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		LOGGER.error("发生异常了: 错误代码:{}, 异常信息:{}", errorCode, cause);
	}

	public HttpMsgException(Ret errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		LOGGER.error("发生异常了: 错误代码:{}, 描述:{}", errorCode, message);
	}

	public HttpMsgException(Ret errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		LOGGER.error("发生异常了: 错误代码:{}, 描述:{}, 异常信息:{}", errorCode, message, cause);
	}

	public HttpMsgException(Ret errorCode, String message, Object... params) {
		this(errorCode, String.format(message.replace("{}", "%s"), params));
		this.errorCode = errorCode;
		LOGGER.error("发生异常了: 错误代码:{}, 描述:{}", errorCode, message);
	}

	/**
	 * @return 返回自定义的错误代码, 常用错误代码定义在ErrorCode类中
	 */
	public Ret getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"ret\":").append(this.errorCode.code())
		  .append(",\"msg\":\"").append(this.errorCode.msg())
		  .append("(").append(this.getMessage()).append(")").append("\"}");
		return sb.toString();
	}
}
