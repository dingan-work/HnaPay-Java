package wiki.ganhua.hnapay.bean.result.transaction;

import com.alibaba.fastjson.annotation.JSONField;
import wiki.ganhua.hnapay.bean.result.BaseResult;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OfflineTradeConfirmResult extends BaseResult {

    /**
     * 充值汇款标识号
     */
    private String hnapayOrderId;

    /**
     * 新账通订单号
     */
    private String ncountOrderId;

    /**
     * 充值金额
     */
    private String tranAmount;

    /**
     * 线下收款银行
     */
    private String bankName;

    /**
     * 线下银行收款账号
     */
    private String bankAcct;

    /**
     * 线下银行收款户名
     */
    private String bankAcctName;

    /**
     * 线下银行开户行
     */
    private String branchName;

    /**
     * 交易完成时间
     */
    @JSONField(format = "yyyyMMddHHmmss")
    private Date tranFinishTime;

    /**
     * 业务类型
     */
    private String businessType;


    @Override
    public byte[] signContent() {
        Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(3);
        map.put("tranAmount", this.tranAmount);
        map.put("hnapayOrderId", this.hnapayOrderId);
        map.put("bankAcct", this.bankAcct);
        return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
    }
}
