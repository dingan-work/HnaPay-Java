package wiki.ganhua.hnapay.bean.request.accountmanage;

import wiki.ganhua.hnapay.bean.request.BaseHnaPayRequest;
import wiki.ganhua.hnapay.bean.result.enums.TranCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 绑卡请求接口对象
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class CardBindRequest extends BaseHnaPayRequest {

	private static final long serialVersionUID = 1085559856224124261L;

	/**
	 * 支付银行卡
	 */
	private String cardNo;

	/**
	 * 持卡人姓名
	 */
	private String holderName;

	/**
	 * 信用卡有效期
	 */
	private String cardAvailableDate;

	/**
	 * 信用卡cvv2
	 */
	private String cvv2;

	/**
	 * 银行签约手机号
	 */
	private String mobileNo;

	/**
	 * 证件类型 暂仅支持 1：身份证
	 */
	private String identityType = "1";

	/**
	 * 证件号码
	 */
	private String identityCode;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 商户用户IP
	 */
	private String merUserIp;

	public String getMerUserIp() {
		return getMerUserIp(this.merUserIp);
	}

	@Override
	public void encryptBody(String publicKey) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("cardNo", this.cardNo);
		map.put("holderName", this.holderName);
		map.put("cardAvailableDate", this.cardAvailableDate);
		map.put("cvv2", this.cvv2);
		map.put("mobileNo", this.mobileNo);
		map.put("identityType", this.identityType);
		map.put("identityCode", this.identityCode);
		map.put("userId", this.userId);
		map.put("merUserIp", this.merUserIp);
		super.encryptBody(map, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.R007.name());
	}
}
