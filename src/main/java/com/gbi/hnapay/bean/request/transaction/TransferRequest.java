package com.gbi.hnapay.bean.request.transaction;

import com.gbi.hnapay.bean.request.BaseHnaPayRequest;
import com.gbi.hnapay.bean.result.enums.TranCode;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * 转账接口请求参数
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TransferRequest extends BaseHnaPayRequest {

	/**
	 * 付款方用户编号
	 */
	private String payUserId;

	/**
	 * 收款方ID
	 */
	private String receiveUserId;

	/**
	 * 转账金额
	 */
	private BigDecimal tranAmount;

	/**
	 * 业务类型
	 */
	private String businessType;

	@Override
	public void encryptBody(String publicKey) {
		LinkedHashMap<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(4);
		map.put("payUserId", this.payUserId);
		map.put("receiveUserId", this.receiveUserId);
		map.put("tranAmount", this.tranAmount);
		map.put("businessType", this.businessType == null ? "02" : this.businessType);
		super.encryptBody(map, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.T003.name());
	}
}
