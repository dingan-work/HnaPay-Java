package com.gbi.hnapay.bean.result.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 审核状态
 *
 * @author ganhua
 */
@Getter
@AllArgsConstructor
public enum AuditStat implements ResultEnumType {

	/**
	 *
	 */
	THROUGH("00", "审核通过"),
	TO_BE_REVIEWED("01", "待审核"),
	NOT_THROUGH("02", "审核不通过"),
	UNWANTED("03", "无需审核");

	private final String code;

	private final String describe;

	@Override
	public ResultEnumType getEnumByCode(String code) {
		return Arrays.stream(values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
	}
}
