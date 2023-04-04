package wiki.ganhua.hnapay.bean.request.transaction.entry;

import com.google.common.collect.Maps;
import wiki.ganhua.hnapay.bean.request.BaseHnaPayRequest;
import wiki.ganhua.hnapay.bean.result.enums.TranCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wiki.ganhua.hnapay.exception.HnaPayParamValidateException;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * 线下入金确认
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OfflineTradeConfirmRequest extends BaseHnaPayRequest {

    private static final long serialVersionUID = 8016553832006291923L;

    /**
     * 充值金额
     */
    private BigDecimal tranAmount;

    /**
     * 银行编码 固定 PBOC
     */
    private String bankCode = "PBOC";

    /**
     * 币种 暂且支持人民币 1：人民币
     */
    private String currencyType = "1";

    /**
     * 备注
     */
    private String remark;

    /**
     * 充值商户新账通商户ID 默认是商户账户ID
     */
    private String receiveUserId;

    /**
     * 用户浏览器IP
     */
    private String merUserIp;

    /**
     * 异步通知地址
     */
    private String notifyUrl;

    /**
     * 业务类型 （默认支持 01：充值 ）
     */
    private String businessType = "01";

    @Override
    public void encryptBody(String publicKey) {
        LinkedHashMap<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(8);
        if (tranAmount == null) {
            throw new HnaPayParamValidateException("充值金额不能为空");
        }
        map.put("tranAmount", this.tranAmount.stripTrailingZeros().toPlainString());
        map.put("bankCode", this.bankCode);
        map.put("currencyType", this.currencyType);
        map.put("remark", this.remark);
        map.put("receiveUserId", this.receiveUserId);
        map.put("merUserIp", this.getMerUserIp());
        map.put("notifyUrl", this.notifyUrl);
        map.put("businessType", this.businessType);
        super.encryptBody(map, publicKey);
    }

    public String getMerUserIp() {
        return super.getMerUserIp(this.merUserIp);
    }

    @Override
    public String getMerUserIp(String merUserIp) {
        return super.getMerUserIp(merUserIp);
    }

    @Override
    public void tranCode() {
        super.tranCode(TranCode.T004.name());
    }
}
