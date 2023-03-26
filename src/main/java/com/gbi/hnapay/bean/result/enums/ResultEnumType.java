package com.gbi.hnapay.bean.result.enums;


/**
 * @author ganhua
 */
public interface ResultEnumType {

	/**
	 * code
	 *
	 * @return code
	 */
	String getCode();

	/**
	 * 描述
	 *
	 * @return 描述信息
	 */
	String getDescribe();

	/**
	 * 根据code获取枚举类型
	 *
	 * @param code code
	 * @return 枚举类型
	 */
	ResultEnumType getEnumByCode(String code);

}
