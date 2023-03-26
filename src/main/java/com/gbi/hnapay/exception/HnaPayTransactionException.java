package com.gbi.hnapay.exception;

import lombok.Getter;

/**
 * 新生支付交易失败
 *
 * @author ganhua
 */
public class HnaPayTransactionException extends HnaPayException {

	@Getter
	private String errorCode;

	private static final long serialVersionUID = -8479732105235309156L;

	public HnaPayTransactionException(String customErrorMsg) {
		super(customErrorMsg);
	}

	public HnaPayTransactionException(String errorCode, String customErrorMsg) {
		super(errorCode, customErrorMsg);
		this.errorCode = errorCode;
	}
}
