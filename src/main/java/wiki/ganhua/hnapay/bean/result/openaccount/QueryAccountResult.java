package wiki.ganhua.hnapay.bean.result.openaccount;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import wiki.ganhua.hnapay.bean.result.BaseResult;
import wiki.ganhua.hnapay.bean.result.enums.AuditStat;
import wiki.ganhua.hnapay.bean.result.enums.AuthStat;
import wiki.ganhua.hnapay.bean.result.enums.UserStat;
import wiki.ganhua.hnapay.bean.result.serializer.ResultCodeDeserializer;
import wiki.ganhua.hnapay.bean.result.serializer.ResultCodeSerializer;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户查询接口同步返回
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class QueryAccountResult extends BaseResult {

	/**
	 * 失败原因
	 */
	private String failReason;

	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 用户标识
	 */
	private String outUserId;

	/**
	 * 用户状态
	 */
	@JSONField(serializeUsing = ResultCodeSerializer.class, deserializeUsing = ResultCodeDeserializer.class)
	private UserStat userStat;

	/**
	 * 审核状态
	 */
	@JSONField(serializeUsing = ResultCodeSerializer.class, deserializeUsing = ResultCodeDeserializer.class)
	private AuditStat auditStat;

	/**
	 * 实名状态
	 */
	@JSONField(serializeUsing = ResultCodeSerializer.class, deserializeUsing = ResultCodeDeserializer.class)
	private AuthStat authStat;

	/**
	 * 账户余额
	 */
	private BigDecimal balAmount;

	/**
	 * 待清算余额
	 */
	private BigDecimal unclearAmount;

	/**
	 * 待清算余额汇总
	 */
	private BigDecimal unclearSumAmount;

	/**
	 * 可用余额
	 */
	private BigDecimal availableBalance;

	/**
	 * 待结转余额
	 */
	private BigDecimal unsettleBalance;

	/**
	 * 绑卡协议号列表
	 */
	@JSONField(deserializeUsing = CardAgrNoListSerializer.class)
	private List<BindCardAgrNo> bindCardAgrNoList;

	@Data
	public static class BindCardAgrNo {
		/**
		 * 绑卡协议号
		 */
		private String bindCardAgrNo;

		/**
		 * 银行简码
		 */
		private String bankCode;

		/**
		 * 卡号掩码
		 */
		private String cardNo;

	}

	public static class CardAgrNoListSerializer implements ObjectDeserializer {

		@Override
		public <T> T deserialze(DefaultJSONParser parser, Type type, Object o) {
			String value = parser.parseObject(String.class);
			if (StrUtil.isBlank(value)) {
				return (T) new ArrayList<>();
			}
			String val = StrUtil.removeSuffix(StrUtil.removePrefix(value, "\""), "\"");
			return Convert.convert(new TypeReference<T>() {}, JSON.parseArray(val,BindCardAgrNo.class));
		}

		@Override
		public int getFastMatchToken() {
			return 0;
		}
	}

	@Override
	public byte[] signContent() {
		Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(6);
		map.put("userId", this.userId);
		map.put("outUserId", this.outUserId== null ? "" : this.outUserId);
		map.put("userStat", this.userStat.getCode());
		map.put("auditStat", this.auditStat.getCode());
		map.put("balAmount", this.balAmount.toString());
		map.put("bindCardAgrNoList", this.bindCardAgrNoList.isEmpty() ? "" : JSON.toJSONString(this.getBindCardAgrNoList()));
		return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
	}
}
