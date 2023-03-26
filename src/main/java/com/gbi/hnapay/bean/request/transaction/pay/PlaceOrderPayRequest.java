package com.gbi.hnapay.bean.request.transaction.pay;

import cn.hutool.core.lang.Assert;
import com.gbi.hnapay.bean.request.BaseHnaPayRequest;
import com.gbi.hnapay.bean.request.enums.PayType;
import com.gbi.hnapay.bean.result.enums.TranCode;
import com.gbi.hnapay.exception.HnaPayParamValidateException;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.LinkedHashMap;

/**
 * 快捷支付下单接口
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class PlaceOrderPayRequest extends BaseHnaPayRequest {

	private static final long serialVersionUID = -990437264237438667L;

	/**
	 * 支付金额
	 */
	private BigDecimal tranAmount;

	/**
	 * 支付方式 2:银行卡卡号 3：绑卡协议号
	 */
	private PayType payType;

	/**
	 * 支付银行卡卡号 payType=2 不可空
	 */
	private String cardNo;

	/**
	 * 持卡人姓名 payType=2 不可空
	 */
	private String holderName;

	/**
	 * 信用卡有效期 payType=2，且为信用卡时不可空
	 */
	private String cardAvailableDate;

	/**
	 * 信用卡CVV2
	 */
	private String cvv2;

	/**
	 * 银行签约手机号
	 */
	private String mobileNo;

	/**
	 * 证件类型
	 */
	private String identityType = "1";

	/**
	 * 证件号码
	 */
	private String identityCode;

	/**
	 * 绑卡协议号
	 */
	private String bindCardAgrNo;

	/**
	 * 商户异步通知地址
	 */
	private String notifyUrl;

	/**
	 * 订单过期时常
	 */
	private Duration orderExpireTime;

	/**
	 * 用户编号 协议支付时，必填，要素支付时，可空
	 */
	private String userId;

	/**
	 * 收款方ID
	 */
	private String receiveUserId;

	/**
	 * 商户用户IP
	 */
	private String merUserIp;

	/**
	 * 风控扩展信息
	 */
	private String riskExpand;

	/**
	 * 商品信息
	 */
	private String goodsInfo;

	/**
	 * 商户渠道进件ID
	 */
	private String subMerchantId;

	/**
	 * 是否分账
	 * 0：否 （默认）
	 * 1：是
	 */
	private Boolean divideFlag;

	/**
	 * 分账明细信息
	 */
	private String divideDetail;

	/**
	 * 分期期数 只支持 3、6、12、24
	 */
	private String instalmentNum;

	/**
	 * 商户补贴分期手续费方式 0-不贴息，1-贴息，2-全额贴息
	 */
	private String instalmentType;

	/**
	 * 商户分期贴息费率
	 */
	private String instalmentRate;

	public String getMerUserIp() {
		return getMerUserIp(this.merUserIp);
	}

	@Override
	public void encryptBody(String publicKey) {
		LinkedHashMap<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(23);
		Assert.notNull(this.tranAmount, () -> new HnaPayParamValidateException("支付金额为空"));
		map.put("tranAmount", this.tranAmount.stripTrailingZeros().toPlainString());
		map.put("payType", this.payType.getType());
		map.put("cardNo", this.cardNo);
		map.put("holderName", this.holderName);
		map.put("cardAvailableDate", this.cardAvailableDate);
		map.put("cvv2", this.cvv2);
		map.put("mobileNo", this.mobileNo);
		map.put("identityType", this.identityType);
		map.put("identityCode", this.identityCode);
		map.put("bindCardAgrNo", this.bindCardAgrNo);
		map.put("notifyUrl", this.notifyUrl);
		map.put("orderExpireTime", this.orderExpireTime.toMinutes());
		map.put("userId", this.userId);
		map.put("receiveUserId", this.receiveUserId);
		map.put("merUserIp", this.merUserIp);
		map.put("riskExpand", this.riskExpand);
		map.put("goodsInfo", this.goodsInfo);
		map.put("subMerchantId", this.subMerchantId);
		map.put("divideFlag", this.divideFlag ? "1" : "0");
		map.put("divideDetail", this.divideDetail);
		map.put("instalmentNum", this.instalmentNum);
		map.put("instalmentType", this.instalmentType);
		map.put("instalmentRate", this.instalmentRate);
		super.encryptBody(map, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.T007.name());
	}
}
