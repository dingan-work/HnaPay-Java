package wiki.ganhua.hnapay.bean.request;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import wiki.ganhua.hnapay.config.HnaPayConfig;
import wiki.ganhua.hnapay.config.HnaPayConfigHolder;
import wiki.ganhua.hnapay.exception.HnaPayParamValidateException;
import wiki.ganhua.hnapay.util.HnaPayRsaUtil;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


/**
 * <pre>
 *     新生支付请求对象共用的参数存放类
 * </pre>
 *
 * @author ganhua
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public abstract class BaseHnaPayRequest implements Serializable {

    private static final long serialVersionUID = -3354074399716080083L;


    private static final TreeSet<String> FROM_PARAMS = new TreeSet<>();
    private static final String VERSION = "version";
    private static final String TRAN_CODE = "tranCode";
    private static final String MER_ID = "merId";
    private static final String MER_ORDER_ID = "merOrderId";
    private static final String SUBMIT_TIME = "submitTime";
    private static final String MSG_CIPHER_TEXT = "msgCiphertext";
    private static final String SIGN_TYPE = "signType";
    private static final String SIGN_VALUE = "signValue";
    private static final String MER_ATTACH = "merAttach";
    private static final String CHARSET = "charset";

    private static final SimplePropertyPreFilter PARAMS_FILTER = new SimplePropertyPreFilter();

    static {
        FROM_PARAMS.addAll(ListUtil.of("url", "version", "tranCode",
                "merId", "merOrderId", "submitTime", "msgCiphertext", "signType", "signValue", "merAttach", "charset"));
        PARAMS_FILTER.getExcludes().addAll(FROM_PARAMS);
    }

    @Getter(AccessLevel.NONE)
    private String url;

    /**
     * 版本号 目前必须为1.0
     */
    private final String version = "1.0";

    /**
     * 交易代码
     */
    protected String tranCode;

    /**
     * 商户ID
     * 新账通平台提供给商户 的唯一 ID
     */
    @Getter(AccessLevel.NONE)
    protected String merId;

    /**
     * 商户订单号
     * 格式：数字，字母，下 划线，竖划线，中划线
     */
    @Getter(AccessLevel.NONE)
    private String merOrderId;

    /**
     * 请求提交时间
     */
    protected String submitTime;

    /**
     * 报文密文
     */
    protected String msgCiphertext;

    /**
     * 签名类型 1: RSA
     */
    private String signType = "1";

    /**
     * 签名密文串
     */
    protected String signValue;

    /**
     * 附加数据 长度限制0-80
     */
    protected String merAttach;

    /**
     * 编码方式 1: UTF8
     */
    private String charset = "1";


    public void setMerAttach(String merAttach) {
        // 附加数据长度限制 0-80
        int attachDataLen = 80;
        if (merAttach.length() > attachDataLen) {
            throw new HnaPayParamValidateException("附加数据长度限制80以内");
        }
        this.merAttach = merAttach;
    }

    public Map<String, Object> baseFormParams() {
        Map<String, Object> commonParams = new HashMap<>(10);
        commonParams.put(VERSION, this.version);
        commonParams.put(TRAN_CODE, this.tranCode);
        commonParams.put(MER_ID, this.merId);
        commonParams.put(MER_ORDER_ID, this.merOrderId);
        commonParams.put(SUBMIT_TIME, this.submitTime);
        commonParams.put(MSG_CIPHER_TEXT, this.msgCiphertext);
        commonParams.put(SIGN_TYPE, this.signType);
        commonParams.put(SIGN_VALUE, this.signValue);
        commonParams.put(MER_ATTACH, this.merAttach);
        commonParams.put(CHARSET, this.charset);
        return commonParams;
    }

    /**
     * 商户报文签名
     */
    private void bodySign(String privateKey, String signatureKey) {
        String formatData = "version=[%s]tranCode=[%s]merId=[%s]merOrderId=[%s]submitTime=[%s]msgCiphertext=[%s]signType=[%s]";
        String bodyOriginalText = String.format(formatData, this.version, this.tranCode,
                this.getMerId(), this.merOrderId, this.submitTime, this.msgCiphertext, this.signType);
        this.signValue = HnaPayRsaUtil.sign(bodyOriginalText.getBytes(StandardCharsets.UTF_8), privateKey, signatureKey);
    }

    /**
     * 获取调用接口需要的参数
     */
    public Map<String, Object> formParams(String platformPublicKey, String businessPrivateKey) {
        this.submitTime = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);
        this.encryptBody(platformPublicKey);
        this.bodySign(businessPrivateKey, HnaPayConfig.SignatureAlgorithm.SHA1withRSA.name());
        return baseFormParams();
    }

    /**
     * 获取调用接口需要的参数
     */
    public Map<String, Object> formParams(HnaPayConfig hnaPayConfig) {
        this.submitTime = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);
        this.merId = hnaPayConfig.getMerId();
        this.charset = hnaPayConfig.getCharset();
        this.signType = hnaPayConfig.getSignType();
        this.tranCode();
        this.encryptBody(hnaPayConfig.getPlatformPublicKey());
        this.bodySign(hnaPayConfig.getBusinessPrivateKey(), hnaPayConfig.getRsaAlgorithm().name());
        return baseFormParams();
    }

    /**
     * 签名
     *
     * @param publicKey 公钥
     */
    public abstract void encryptBody(String publicKey);

    public void encryptBody(BaseHnaPayRequest request, String publicKey) {
        String dataStr = JSON.toJSONString(request, PARAMS_FILTER);
        this.msgCiphertext = HnaPayRsaUtil.encryptSliceToBase64(publicKey, dataStr);
    }

    public void encryptBody(Map<String, Object> request, String publicKey) {
        request.forEach((k, v) -> {
            if (v == null) {
                request.put(k, "");
            }
        });
        String dataStr = JSON.toJSONString(request, PARAMS_FILTER);
        log.info("请求参数信息:{}", dataStr);
        this.msgCiphertext = HnaPayRsaUtil.encryptSliceToBase64(publicKey, dataStr);
    }

    /**
     * 每笔交易的固定 tranCode
     */
    public abstract void tranCode();

    public void tranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getUrl() {
        if (StrUtil.isBlank(this.url)) {
            return String.format("%s/%s.htm", HnaPayConfig.DEFAULT_PAY_BASE_URL, StrUtil.lowerFirst(this.getTranCode()));
        }
        return url;
    }

    public String getMerUserIp(String merUserIp) {
        if (StrUtil.isBlank(merUserIp)) {
            // 默认网络获取
            return getPubNetIp();
        }
        return merUserIp;
    }

    public String getMerId() {
        if (this.merId == null) {
            this.merId = HnaPayConfigHolder.get();
        }
        return this.merId;
    }

    public String getMerOrderId() {
        if (StrUtil.isBlank(this.merOrderId)) {
            return StrUtil.concat(true, this.tranCode, IdUtil.getSnowflakeNextIdStr());
        }
        return this.merOrderId;
    }

    /**
     * 获取本机公网ip
     */
    private String getPubNetIp() {
        try {
            String ip = HttpUtil.get("https://www.taobao.com/help/getip.php");
            ip = ip.replace("ipCallback(", "").replace(")", "");
            return JSON.parseObject(ip).getString("ip");
        } catch (Exception e) {
            return HttpUtil.get("https://ifconfig.me/ip");
        }
    }

}
