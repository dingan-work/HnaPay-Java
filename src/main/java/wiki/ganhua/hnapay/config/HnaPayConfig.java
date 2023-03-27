package wiki.ganhua.hnapay.config;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新生支付配置
 *
 * @author ganhua
 */
@Data
@EqualsAndHashCode
public class HnaPayConfig {

	private static final String DEFAULT_PAY_BASE_URL = "https://ncount.hnapay.com/api/";

	/**
	 * 新生支付接口请求地址域名部分.
	 */
	private String payBaseUrl = DEFAULT_PAY_BASE_URL;

	/**
	 * http请求连接超时时间.
	 */
	private int httpConnectionTimeout = 5000;

	/**
	 * http请求数据读取等待时间.
	 */
	private int httpTimeout = 10000;

	/**
	 * 签名类型 1: RSA
	 */
	protected String signType = "1";

	/**
	 * 编码方式 1: UTF8
	 */
	protected String charset = "1";

	/**
	 * 商户号
	 */
	private String merId;

	/**
	 * 新账通平台公钥
	 */
	private String platformPublicKey;

	/**
	 * 商户私钥
	 */
	private String businessPrivateKey;

	/**
	 * 商户用户IP
	 */
	private String merUserIp;

	/**
	 * 快捷支付异步回调地址
	 */
	private String quickPayAsyncCallbackUrl;

	/**
	 * 提现异步通知回调地址
	 */
	private String cashOutAsyncCallbackUrl;

	/**
	 * 商户渠道进件ID
	 */
	private String subMerchantId;

	/**
	 * RSA签名算法
	 */
	public SignatureAlgorithm rsaAlgorithm = SignatureAlgorithm.SHA1withRSA;

	public enum SignatureAlgorithm {
		/**
		 *
		 */
		MD5withRSA,
		SHA1withRSA,
		SHA256withRSA,
		SHA512withRSA,

	}

	/**
	 * 返回所设置的新生支付接口请求地址域名.
	 *
	 * @return 新生支付接口请求地址域名
	 */
	public String getPayBaseUrl() {
		if (StrUtil.isEmpty(this.payBaseUrl)) {
			return DEFAULT_PAY_BASE_URL;
		}
		return this.payBaseUrl;
	}

}
