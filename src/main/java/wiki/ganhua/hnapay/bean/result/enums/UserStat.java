package wiki.ganhua.hnapay.bean.result.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 用户状态
 *
 * @author ganhua
 */
@Getter
@AllArgsConstructor
public enum UserStat implements ResultEnumType{

	/**
	 *
	 */
	NORMAL("00", "正常"),
	TO_BE_ACTIVATED("01", "待激活"),
	LOCKING("02", "锁定"),
	ACCOUNT_OPENING_FAILED("03", "开户失败"),
	CANCELLATION("99", "注销");

	private final String code;

	private final String describe;

	@Override
	public ResultEnumType getEnumByCode(String code) {
		return Arrays.stream(values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
	}
}
