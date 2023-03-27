package wiki.ganhua.hnapay.bean.result.transaction;

import com.alibaba.fastjson.annotation.JSONField;
import wiki.ganhua.hnapay.bean.result.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 转账平台同步返回参数
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class TransferResult extends BaseResult {

	private String ncountOrderId;

	@JSONField(format = "yyyyMMdd")
	private Date orderDate;

	/**
	 * 付款方账户余额
	 */
	private BigDecimal payAcctAmount;

	/**
	 * 收款方账户余额
	 */
	private BigDecimal recvAcctAmount;

	/**
	 * 业务类型
	 */
	private BigDecimal businessType;


	@Override
	public byte[] signContent() {
		return basePlatformSignData(null).getBytes(StandardCharsets.UTF_8);
	}
}
