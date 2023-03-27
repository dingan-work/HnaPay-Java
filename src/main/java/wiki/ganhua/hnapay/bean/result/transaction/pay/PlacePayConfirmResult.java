package wiki.ganhua.hnapay.bean.result.transaction.pay;

import com.alibaba.fastjson.JSON;
import wiki.ganhua.hnapay.bean.request.transaction.pay.PlacePayConfirmRequest;
import wiki.ganhua.hnapay.bean.result.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 新账通平台一步
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class PlacePayConfirmResult extends BaseResult {

	/**
	 * 新账通订单号
	 */
	private String ncountOrderId;

	/**
	 * 交易金额
	 */
	private BigDecimal tranAmount;

	/**
	 * 对账日期
	 */
	private String checkDate;

	/**
	 * 商户请求时间
	 */
	private String submitTime;

	/**
	 * 交易完成时间
	 */
	private String tranFinishTime;

	/**
	 * 签约银行简码
	 */
	private String bankCode;

	/**
	 * 支付银行卡卡类型
	 */
	private String cardType;

	/**
	 * 支付银行卡后四位
	 */
	private String shortCardNo;

	/**
	 * 绑卡协议号
	 */
	private String bindCardAgrNo;

	/**
	 * 业务类型
	 */
	private String businessType;

	/**
	 * 分账订单明细
	 */
	private String divideAcctDtl;

	private List<PlacePayConfirmRequest.DivideAcctDtl> divideAcctDtls;


	public List<PlacePayConfirmRequest.DivideAcctDtl> getDivideAcctDtls() {
		if (this.divideAcctDtl == null || this.divideAcctDtl.isEmpty()) {
			return null;
		}
		return JSON.parseArray(this.divideAcctDtl, PlacePayConfirmRequest.DivideAcctDtl.class);
	}

	@Override
	public byte[] signContent() {
		Map<String, Object> map = new LinkedHashMap<>(16);
		map.put("ncountOrderId", this.ncountOrderId);
		map.put("tranAmount", this.tranAmount);
		map.put("checkDate", this.checkDate);
		map.put("submitTime", this.submitTime);
		map.put("tranFinishTime", this.tranFinishTime);
		map.put("bankCode", this.bankCode);
		map.put("cardType", this.cardType);
		map.put("shortCardNo", this.shortCardNo);
		map.put("bindCardAgrNo", this.bindCardAgrNo);
		return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
	}
}
