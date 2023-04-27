package wiki.ganhua.hnapay.exception;

/**
 * 数据错误
 *
 * @author ganhua
 */
public class HnaPayDataException extends HnaPayException {

    public HnaPayDataException(String customErrorMsg) {
        super(customErrorMsg);
    }

    public HnaPayDataException(String errorCode, String customErrorMsg) {
        super(errorCode, customErrorMsg);
    }
}
