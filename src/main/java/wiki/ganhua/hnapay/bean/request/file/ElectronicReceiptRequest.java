package wiki.ganhua.hnapay.bean.request.file;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wiki.ganhua.hnapay.bean.request.BaseHnaPayRequest;
import wiki.ganhua.hnapay.bean.request.enums.BusinessType;
import wiki.ganhua.hnapay.bean.result.enums.TranCode;

import java.util.Map;

/**
 * 电子回执单下载请求
 *
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ElectronicReceiptRequest extends BaseHnaPayRequest {

    /**
     * 原交易订单号
     */
    private String ncountOrderId;

    /**
     * 业务类型 其他交易类型为空
     */
    private BusinessType businessType;

    /**
     * 原交易代码
     */
    private TranCode orgTranCode;

    @Override
    public void encryptBody(String publicKey) {
        Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(3);
        map.put("ncountOrderId", this.ncountOrderId);
        map.put("businessType", this.businessType.getType());
        map.put("orgTranCode", this.orgTranCode.name());
        super.encryptBody(map, publicKey);
    }

    @Override
    public void tranCode() {
        super.tranCode(TranCode.T011.name());
    }
}
