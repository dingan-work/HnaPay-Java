package com.gbi.hnapay.bean.request.transaction.pay;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONType;
import com.gbi.hnapay.bean.request.BaseHnaPayRequest;
import com.gbi.hnapay.bean.request.enums.BusinessType;
import com.gbi.hnapay.bean.request.enums.FeeType;
import com.gbi.hnapay.bean.result.enums.TranCode;
import com.gbi.hnapay.exception.HnaPayParamValidateException;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * 快捷支付确认接口
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class PlacePayConfirmRequest extends BaseHnaPayRequest {

	private static final long serialVersionUID = -3205110244819362855L;

	/**
	 * 新账通订单号
	 */
	private String ncountOrderId;

	/**
	 * 短信验证码
	 */
	private String smsCode;

	/**
	 * 商户用户IP
	 */
	@Getter(AccessLevel.NONE)
	private String merUserIp;

	/**
	 * 付款方终端信息
	 */
	private String paymentTerminalInfo;

	/**
	 * 收款方终端信息
	 */
	private String receiverTerminalInfo;

	/**
	 * 设备信息
	 */
	private String deviceInfo;

	/**
	 * 业务类型
	 */
	@Getter(AccessLevel.NONE)
	private BusinessType businessType;

	/**
	 * 手续费内扣外扣
	 */
	private FeeType feeType;

	/**
	 * 分账明细
	 */
	private List<DivideAcctDtl> divideAcctDtl;

	/**
	 * 手续费承担方ID
	 */
	private String feeAmountUser;

	/**
	 * 支付金额
	 */
	private BigDecimal tranAmount;

	public String getMerUserIp() {
		return getMerUserIp(this.merUserIp);
	}

	@Data
	@JSONType(orders = {"ledgerUserId", "amount"})
	public static class DivideAcctDtl {
		/**
		 * 分账方用户ID
		 */
		private String ledgerUserId;

		/**
		 * 金额
		 */
		private BigDecimal amount;
	}

	public BusinessType getBusinessType() {
		if (BusinessType.GUARANTEE.equals(this.businessType)) {
			if (this.getDivideAcctDtl() == null || this.getDivideAcctDtl().isEmpty()) {
				throw new HnaPayParamValidateException("担保交易必填分账明细");
			}
		}
		return this.businessType;
	}

	@Override
	public void encryptBody(String publicKey) {
		LinkedHashMap<String, Object> newMap = Maps.newLinkedHashMapWithExpectedSize(8);
		newMap.put("ncountOrderId", this.ncountOrderId);
		newMap.put("smsCode", this.smsCode);
		newMap.put("merUserIp", this.merUserIp);
		newMap.put("paymentTerminalInfo", this.paymentTerminalInfo);
		newMap.put("receiverTerminalInfo", this.receiverTerminalInfo);
		newMap.put("deviceInfo", this.deviceInfo);
		newMap.put("businessType", this.businessType.getType());
		newMap.put("feeType", this.feeType.getType());
		if (CollUtil.isNotEmpty(this.divideAcctDtl)) {
			// 总金额不为空开启验证
			if (tranAmount != null) {
				boolean flag = tranAmount.compareTo(this.divideAcctDtl.stream().map(DivideAcctDtl::getAmount).filter(Objects::isNull).reduce(BigDecimal.ZERO, BigDecimal::add)) == 0;
				Assert.isTrue(flag, () -> new HnaPayParamValidateException("分账总金额与支付金额不同"));
			}
			newMap.put("divideAcctDtl", JSON.toJSONString(this.divideAcctDtl));
		}
		newMap.put("feeAmountUser", this.feeAmountUser);
		super.encryptBody(newMap, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.T008.name());
	}
}
