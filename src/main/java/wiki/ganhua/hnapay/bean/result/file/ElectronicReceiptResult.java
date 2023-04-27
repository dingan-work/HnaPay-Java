package wiki.ganhua.hnapay.bean.result.file;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wiki.ganhua.hnapay.bean.result.BaseResult;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author ganhua
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ElectronicReceiptResult extends BaseResult {

    /**
     * 电子回执单base64码
     */
    private String reciptBase64Pdf;

    @Override
    public byte[] signContent() {
        Map<String, Object> map = Maps.newLinkedHashMapWithExpectedSize(1);
        map.put("reciptBase64Pdf", this.reciptBase64Pdf);
        return basePlatformSignData(map).getBytes(StandardCharsets.UTF_8);
    }
}
