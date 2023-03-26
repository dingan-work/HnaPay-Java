package com.gbi.hnapay.bean.request.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式
 *
 * @author ganhua
 */
@Getter
@AllArgsConstructor
public enum PayType {

	/**
	 * 银行卡卡号
	 */
	BANK_CARD_NO(2),

	/**
	 * 绑卡协议号
	 */
	BANK_CARD_AGR_NO(3);

	/**
	 * 支付方式请求类型值
	 */
	private final Integer type;

}
