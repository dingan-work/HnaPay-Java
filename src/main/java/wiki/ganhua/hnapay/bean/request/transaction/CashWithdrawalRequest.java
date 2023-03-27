package wiki.ganhua.hnapay.bean.request.transaction;

import cn.hutool.core.lang.Assert;
import wiki.ganhua.hnapay.bean.request.BaseHnaPayRequest;
import wiki.ganhua.hnapay.bean.request.enums.CashWithdrawalBusinessType;
import wiki.ganhua.hnapay.bean.result.enums.TranCode;
import wiki.ganhua.hnapay.exception.HnaPayParamValidateException;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CashWithdrawalRequest extends BaseHnaPayRequest {

	/**
	 * 提现金额
	 */
	private BigDecimal tranAmount;

	/**
	 * 提现用户编号
	 */
	private String userId;

	/**
	 * 绑卡协议号
	 */
	private String bindCardAgrNo;

	/**
	 * 异步通知地址
	 */
	private String notifyUrl;

	/**
	 * 付款方终端信息
	 */
	private String paymentTerminalInfo;

	/**
	 * 设备信息
	 */
	private String deviceInfo;

	/**
	 * 服务费
	 */
	private BigDecimal serviceAmount;

	/**
	 * 业务类型
	 */
	private CashWithdrawalBusinessType businessType;

	/**
	 * 银行卡号
	 */
	private String cardNo;

	@Override
	public void encryptBody(String publicKey) {
		LinkedHashMap<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(9);
		Assert.notNull(this.tranAmount, () -> new HnaPayParamValidateException("订单支付金额为空"));
		map.put("tranAmount", this.tranAmount.stripTrailingZeros().toPlainString());
		map.put("userId", this.userId);
		map.put("bindCardAgrNo", this.bindCardAgrNo);
		map.put("notifyUrl", this.notifyUrl);
		map.put("paymentTerminalInfo", this.paymentTerminalInfo);
		map.put("deviceInfo", this.deviceInfo);
		if (this.serviceAmount != null) {
			map.put("serviceAmount", this.serviceAmount.toPlainString());
		}
		map.put("businessType", this.businessType.getType());
		map.put("cardNo", this.cardNo);
		super.encryptBody(map, publicKey);
	}

	@Override
	public void tranCode() {
		super.tranCode(TranCode.T002.name());
	}
}
