package wiki.ganhua;

import cn.hutool.core.util.IdUtil;
import wiki.ganhua.hnapay.bean.request.transaction.entry.OfflineTradeConfirmRequest;
import wiki.ganhua.hnapay.config.HnaPayConfig;
import wiki.ganhua.hnapay.service.HnaPayService;
import wiki.ganhua.hnapay.service.impl.HnaPayServiceImpl;

import java.math.BigDecimal;

public class TestDemo {
    private static final String privateKey = "";

    private static final String publicKey = "";

    public static void main(String[] args) {
        HnaPayConfig config = new HnaPayConfig();
        config.setMerId("");
        config.setSubMerchantId("");
        config.setPlatformPublicKey(publicKey);
        config.setBusinessPrivateKey(privateKey);
        HnaPayService hnaPayService = new HnaPayServiceImpl();
        hnaPayService.setConfig(config);
        OfflineTradeConfirmRequest request = new OfflineTradeConfirmRequest();
        request.setMerOrderId(IdUtil.getSnowflakeNextIdStr());
        request.setTranAmount(new BigDecimal("500000"));
        request.setRemark("线下入金：");
        request.setReceiveUserId("");
        request.setNotifyUrl("https://www.baidu.com");




        System.out.println(hnaPayService.getTransactionService().offlineDeposit(request));
    }
}
