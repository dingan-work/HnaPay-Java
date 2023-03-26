package com.gbi.hnapay.bean.request.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 提现业务类型
 *
 * @author ganhua
 */
@Getter
@AllArgsConstructor
public enum CashWithdrawalBusinessType {
	/**
	 * 协议号提现
	 */
	CARD_AGR_NO("08"),

	/**
	 * 同名非绑卡提现
	 */
	NON_BINDING_SAME_NAME("09"),

	/**
	 * 提现至企业号
	 */
	ENTERPRISE_NO("14");

	private final String type;

	@Override
	public String toString() {
		return this.type;
	}
}
