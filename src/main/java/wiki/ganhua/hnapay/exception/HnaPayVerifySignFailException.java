package wiki.ganhua.hnapay.exception;

import cn.hutool.core.util.StrUtil;


/**
 * 新生支付验签失败
 *
 * @author ganhua
 */
public class HnaPayVerifySignFailException extends HnaPayException {

	private static final long serialVersionUID = -6175855442088903401L;

	public HnaPayVerifySignFailException(byte[] data, String signValue) {
		super(StrUtil.format("验签失败:签名内容:{},签名信息{}", new String(data), signValue));
	}
}
