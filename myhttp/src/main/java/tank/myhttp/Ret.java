package tank.myhttp;

/**
		* @author tank
		* @date:2014-4-17 上午11:50:31
		* @description:
		* @version :
		*/
public enum Ret {
	SUCCESS(0, "OK"), BUSY(1, "系统繁忙"), TOKEN_INVALID(2, "token已过期"), TOKEN_NO_EXIT(3, "token不存在"), PARAM_ERROR(4, "请求参数错误")

	;

	/** The code associated with this status, for example "404" for "Not Found". */
	private int code;

	/**
	 * The line associated with this status, "HTTP/1.1 501 Not Implemented".
	 */
	private String msg;

	/**
	 * Create an instance of this type.
	 * 
	 * @param code the status code.
	 * @param phrase the associated phrase.
	 */
	private Ret(int code, String phrase) {
		this.code = code;
		msg = phrase;
	}

	/**
	 * Retrieve the status code for this instance.
	 * 
	 * @return the status code.
	 */
	public int code() {
		return code;
	}

	/**
	 * Retrieve the status line for this instance.
	 * 
	 * @return the status line.
	 */
	public String msg() {
		return msg;
	}

	public String toJson() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"ret\":").append(this.code).append(",\"msg\":\"").append(this.msg).append("\"}");
		return sb.toString();
	}

	public String toJson(String extMsg) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"ret\":").append(this.code).append(",\"msg\":\"").append(this.msg).append("(").append(extMsg).append(")").append("\"}");
		return sb.toString();
	}

}
