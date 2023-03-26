package com.gbi.hnapay.bean.request.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 手续费扣费类型
 *
 * @author ganhua
 */

@Getter
@AllArgsConstructor
public enum FeeType {
	/**
	 * 内扣
	 */
	INNER(1),

	/**
	 * 外扣
	 */
	OUTER(0);


	private final Integer type;

	@Override
	public String toString() {
		return type + "";
	}
}
