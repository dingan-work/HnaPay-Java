package com.gbi.hnapay.exception;

/**
 * 新生支付参数验证失败异常
 *
 * @author ganhua
 */
public class HnaPayParamValidateException extends HnaPayException {

	private static final long serialVersionUID = 1137459098088805970L;

	public HnaPayParamValidateException(String customErrorMsg) {
		super(customErrorMsg);
	}

	public HnaPayParamValidateException(String customErrorMsg, Throwable e) {
		super(customErrorMsg, e);
	}
}
