package com.gbi.hnapay.bean.result.accountmanage;

import com.alibaba.fastjson.annotation.JSONField;
import com.gbi.hnapay.bean.result.BaseResult;
import com.gbi.hnapay.bean.result.enums.BankCardType;
import com.gbi.hnapay.bean.result.enums.BankCodeProperties;
import com.gbi.hnapay.bean.result.serializer.ResultCodeDeserializer;
import com.gbi.hnapay.bean.result.serializer.ResultCodeSerializer;
import com.gbi.hnapay.config.HnaPayConfig;
import com.gbi.hnapay.config.HnaPayConfigHolder;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 绑卡确认接口返回
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardBindConfirmResult extends BaseResult {

	/**
	 * 帮卡协议号
	 */
	private String bindCardAgrNo;

	/**
	 * 签约银行简码
	 */
	private String bankCode;

	@JSONField(serializeUsing = ResultCodeSerializer.class, deserializeUsing = ResultCodeDeserializer.class)
	private BankCardType cardType;

	/**
	 * 签约银行卡后四位
	 */
	private String shortCardNo;

	@Override
	public byte[] signContent() {
		Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(4);
		map.put("bindCardAgrNo", this.bindCardAgrNo);
		map.put("bankCode", this.bankCode);
		map.put("cardType", this.cardType.getCode());
		map.put("shortCardNo", this.shortCardNo);
		return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
	}

	public String obtainBankName() {
		return HnaPayConfigHolder.getBankCodeProperties().getDescribe(this.bankCode);
	}

	public static String obtainBankName(String bankCode) {
		return HnaPayConfigHolder.getBankCodeProperties().getDescribe(bankCode);
	}
}
