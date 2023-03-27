package wiki.ganhua.hnapay.bean.request.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务类型
 *
 * @author ganhua
 */
@Getter
@AllArgsConstructor
public enum BusinessType {

	/**
	 * 消费
	 */
	CONSUME("03"),

	/**
	 * 担保下单
	 */
	GUARANTEE("04");


	private final String type;

	@Override
	public String toString() {
		return type;
	}
}
