package com.gbi.hnapay.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <pre>
 *     新生支付异常结果类
 * </pre>
 *
 * @author ganhua
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HnaPayException extends RuntimeException {

	private static final long serialVersionUID = -6028557672219507747L;

	/**
	 * 自定义错误讯息
	 */
	private String customErrorMsg;

	/**
	 * 返回状态码.
	 */
	private String returnCode;
	/**
	 * 返回信息.
	 */
	private String returnMsg;

	/**
	 * 业务结果.
	 */
	private String resultCode;

	/**
	 * 错误代码.
	 */
	private String errorCode;

	/**
	 * 错误代码描述.
	 */
	private String errorCodeDes;

	public HnaPayException(String errorCode, String customErrorMsg) {
		super(customErrorMsg);
		this.errorCode = errorCode;
		this.errorCodeDes = customErrorMsg;
		this.customErrorMsg = customErrorMsg;
	}

	public HnaPayException(String customErrorMsg) {
		super(customErrorMsg);
		this.customErrorMsg = customErrorMsg;
	}

	public HnaPayException(String customErrorMsg, Throwable e) {
		super(customErrorMsg, e);
		this.customErrorMsg = customErrorMsg;
	}
}
