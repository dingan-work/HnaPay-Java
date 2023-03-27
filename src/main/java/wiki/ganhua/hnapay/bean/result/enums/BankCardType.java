package wiki.ganhua.hnapay.bean.result.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 支付银行卡卡类型
 *
 * @author ganhua
 */
@Getter
@AllArgsConstructor
public enum BankCardType implements ResultEnumType {
	/**
	 *
	 */
	DEBITCARD("1", "借记卡"),

	CREDITCARD("2", "信用卡");

	private final String code;

	private final String describe;

	@Override
	public ResultEnumType getEnumByCode(String code) {
		return Arrays.stream(values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
	}
}
