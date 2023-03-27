package wiki.ganhua.hnapay.exception;

/**
 * 支付签名异常
 *
 * @author ganhua
 */
public class HnaPayCryptoException extends HnaPayException {

	public HnaPayCryptoException() {
		super("支付签名异常");
	}

	public HnaPayCryptoException(String customErrorMsg) {
		super(customErrorMsg);
	}

	public HnaPayCryptoException(String customErrorMsg, Throwable e) {
		super(customErrorMsg, e);
	}
}
