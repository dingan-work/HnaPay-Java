package wiki.ganhua.hnapay.bean.result.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 实名认证状态
 *
 * @author ganhua
 */
@Getter
@AllArgsConstructor
public enum AuthStat implements ResultEnumType{

	/**
	 * 实名认证成功
	 */
	SUCCESS("00","实名认证成功"),
	TO_BE_STAT("01","待认证"),
	FAIL("02","实名认证失败"),
	UNWANTED("03","无需实名认证"),
	TIMEOUT("04","认证超时");

	private final String code;

	private final String describe;

	@Override
	public ResultEnumType getEnumByCode(String code) {
		return Arrays.stream(values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
	}
}
