package wiki.ganhua.hnapay.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import wiki.ganhua.hnapay.bean.request.enums.CashWithdrawalBusinessType;
import wiki.ganhua.hnapay.bean.request.enums.PayType;
import wiki.ganhua.hnapay.bean.request.transaction.CashWithdrawalRequest;
import wiki.ganhua.hnapay.bean.request.transaction.ReceiptConfirmRequest;
import wiki.ganhua.hnapay.bean.request.transaction.TransferRequest;
import wiki.ganhua.hnapay.bean.request.transaction.entry.OfflineTradeConfirmRequest;
import wiki.ganhua.hnapay.bean.request.transaction.pay.PlaceOrderPayRequest;
import wiki.ganhua.hnapay.bean.request.transaction.pay.PlacePayConfirmRequest;
import wiki.ganhua.hnapay.bean.result.enums.ResultCode;
import wiki.ganhua.hnapay.bean.result.transaction.CashWithdrawalResult;
import wiki.ganhua.hnapay.bean.result.transaction.OfflineTradeConfirmResult;
import wiki.ganhua.hnapay.bean.result.transaction.TransferResult;
import wiki.ganhua.hnapay.bean.result.transaction.pay.PlaceOrderPayResult;
import wiki.ganhua.hnapay.bean.result.transaction.pay.PlacePayConfirmResult;
import wiki.ganhua.hnapay.exception.HnaPayTransactionException;
import wiki.ganhua.hnapay.service.HnaPayService;
import wiki.ganhua.hnapay.service.TransactionService;
import wiki.ganhua.hnapay.service.impl.base.BaseRequestImpl;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;


/**
 * @author ganhua
 */
@RequiredArgsConstructor
public class TransactionServiceImpl extends BaseRequestImpl implements TransactionService {

    private final HnaPayService hnaPayService;

    @Override
    public PlaceOrderPayResult quickPaymentOrder(PlaceOrderPayRequest request) {
        String url = String.format("%s/t007.htm", this.hnaPayService.getPayBaseUrl());
        request.setUrl(url);
        request.setMerUserIp(hnaPayService.getConfig().getMerUserIp());
        request.setNotifyUrl(hnaPayService.getConfig().getQuickPayAsyncCallbackUrl());
        // 收款方ID为空时默认担保交易
        if (StrUtil.isBlank(request.getReceiveUserId())) {
            request.setReceiveUserId(hnaPayService.getConfig().getMerId());
        }
        request.setSubMerchantId(hnaPayService.getConfig().getSubMerchantId());
        if (StrUtil.isAllNotBlank(request.getUserId(), request.getBindCardAgrNo())) {
            request.setPayType(PayType.BANK_CARD_AGR_NO);
        } else {
            request.setPayType(PayType.BANK_CARD_NO);
        }
        PlaceOrderPayResult result = this.request(hnaPayService, request, PlaceOrderPayResult.class);
        if (result.isOk()) {
            if (!result.getSubmitTime().equals(request.getSubmitTime())) {
                throw new HnaPayTransactionException("提交时间不一致");
            }
        }
        return result;
    }

    @Override
    public PlacePayConfirmResult confirmPay(PlacePayConfirmRequest request) {
        String url = String.format("%s/t008.htm", this.hnaPayService.getPayBaseUrl());
        request.setUrl(url);
        request.setMerUserIp(hnaPayService.getConfig().getMerUserIp());
        return this.request(hnaPayService, request, PlacePayConfirmResult.class);
    }

    @Override
    public Boolean confirmReceipt(ReceiptConfirmRequest request, String ncountOrderId) {
        String url = String.format("%s/f001.htm", this.hnaPayService.getPayBaseUrl());
        request.setUrl(url);
        HashMap<String, Object> map = Maps.newHashMapWithExpectedSize(1);
        map.put("ncountOrderId", ncountOrderId);
        return this.requestBool(hnaPayService, request, map);
    }

    @Override
    public CashWithdrawalResult cashOut(CashWithdrawalRequest request) {
        String url = String.format("%s/t002.htm", this.hnaPayService.getPayBaseUrl());
        request.setUrl(url);
        request.setNotifyUrl(hnaPayService.getConfig().getCashOutAsyncCallbackUrl());
        if (StrUtil.isNotBlank(request.getCardNo())) {
            request.setBusinessType(CashWithdrawalBusinessType.NON_BINDING_SAME_NAME);
        }
        return this.request(hnaPayService, request, CashWithdrawalResult.class);
    }

    @Override
    public TransferResult transfer(TransferRequest request) {
        String url = String.format("%s/t003.htm", this.hnaPayService.getPayBaseUrl());
        request.setUrl(url);
        return this.request(hnaPayService, request, TransferResult.class);
    }

    @Override
    public Boolean transfer(String payUserId, String receiveUserId, BigDecimal tranAmount) {
        TransferRequest request = new TransferRequest();
        request.setPayUserId(payUserId);
        request.setReceiveUserId(receiveUserId);
        request.setTranAmount(tranAmount);
        TransferResult transfer = transfer(request);
        return transfer.getResultCode() == ResultCode.SUCCESS;
    }

    @Override
    public OfflineTradeConfirmResult offlineDeposit(OfflineTradeConfirmRequest request) {
        String url = String.format("%s/t004.htm", this.hnaPayService.getPayBaseUrl());
        request.setUrl(url);
        return this.request(hnaPayService, request, OfflineTradeConfirmResult.class);
    }
}
