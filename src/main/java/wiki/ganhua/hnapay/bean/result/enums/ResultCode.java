package wiki.ganhua.hnapay.bean.result.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 处理结果码
 *
 * @author ganhua
 */
@AllArgsConstructor
@Getter
public enum ResultCode implements ResultEnumType{

	/**
	 *
	 */
	SUCCESS("0000", "交易成功"),

	FILED("4444", "交易失败"),

	IN_PROGRESS("9999", "交易进行中"),

	ERROR("7777", "交易失败");

	private final String code;

	private final String describe;

	@Override
	public ResultEnumType getEnumByCode(String code) {
		return Arrays.stream(values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
	}
}
