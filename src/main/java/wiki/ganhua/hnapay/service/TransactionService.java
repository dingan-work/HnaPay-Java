package wiki.ganhua.hnapay.service;

import wiki.ganhua.hnapay.bean.request.transaction.CashWithdrawalRequest;
import wiki.ganhua.hnapay.bean.request.transaction.ReceiptConfirmRequest;
import wiki.ganhua.hnapay.bean.request.transaction.TransferRequest;
import wiki.ganhua.hnapay.bean.request.transaction.entry.OfflineTradeConfirmRequest;
import wiki.ganhua.hnapay.bean.request.transaction.pay.PlaceOrderPayRequest;
import wiki.ganhua.hnapay.bean.request.transaction.pay.PlacePayConfirmRequest;
import wiki.ganhua.hnapay.bean.result.transaction.CashWithdrawalResult;
import wiki.ganhua.hnapay.bean.result.transaction.OfflineTradeConfirmResult;
import wiki.ganhua.hnapay.bean.result.transaction.TransferResult;
import wiki.ganhua.hnapay.bean.result.transaction.pay.PlaceOrderPayResult;
import wiki.ganhua.hnapay.bean.result.transaction.pay.PlacePayConfirmResult;

import java.math.BigDecimal;

/**
 * 交易相关
 *
 * @author ganhua
 */
public interface TransactionService extends BaseRequestService {

    /**
     * 快捷支付下单发动验证码
     *
     * @param request 请求对象
     * @return 主要返回新账通订单号
     */
    PlaceOrderPayResult quickPaymentOrder(PlaceOrderPayRequest request);

    /**
     * 快捷支付确认接口
     *
     * @param request 请求对象
     * @return 新账通平台快捷支付确认同步返回参数
     */
    PlacePayConfirmResult confirmPay(PlacePayConfirmRequest request);

    /**
     * 收货确认接口
     *
     * @param request       请求对象
     * @param ncountOrderId 新账通订单号
     * @return 同步返回是否确认成功
     */
    Boolean confirmReceipt(ReceiptConfirmRequest request, String ncountOrderId);

    /**
     * 提现接口
     *
     * @param request 提现参数
     * @return 提现同步返回
     */
    CashWithdrawalResult cashOut(CashWithdrawalRequest request);

    /**
     * 转账接口
     *
     * @param request 转账相关参数
     * @return 转账同步返回
     */
    TransferResult transfer(TransferRequest request);

    /**
     * 转账接口
     *
     * @param payUserId     付款方用户编号
     * @param receiveUserId 接收方用户编号
     * @param tranAmount    转账金额
     * @return 是否转账成功
     */
    Boolean transfer(String payUserId, String receiveUserId, BigDecimal tranAmount);

    /**
     * 线下入金
     *
     * @param request 充值入金请求
     * @return 返回结果
     */
    OfflineTradeConfirmResult offlineDeposit(OfflineTradeConfirmRequest request);

}
