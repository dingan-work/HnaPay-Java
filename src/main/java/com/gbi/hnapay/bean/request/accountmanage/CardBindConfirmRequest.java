package com.gbi.hnapay.bean.request.accountmanage;


import com.gbi.hnapay.bean.request.BaseHnaPayRequest;
import com.gbi.hnapay.bean.result.enums.TranCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 绑卡确认接口请求
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class CardBindConfirmRequest extends BaseHnaPayRequest {

	private static final long serialVersionUID = 3654237878514843780L;

	/**
	 * 签约订单号
	 */
	private String ncountOrderId;

	/**
	 * 签约短信验证码
	 */
	private String smsCode;

	/**
	 * 商户用户IP
	 */
	private String merUserIp;

	@Override
	public void encryptBody(String publicKey) {
		super.encryptBody(this, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.R008.name());
	}
}
