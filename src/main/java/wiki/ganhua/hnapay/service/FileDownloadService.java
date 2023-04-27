package wiki.ganhua.hnapay.service;

import wiki.ganhua.hnapay.bean.request.enums.BusinessType;
import wiki.ganhua.hnapay.bean.request.file.ElectronicReceiptRequest;
import wiki.ganhua.hnapay.bean.result.enums.TranCode;

import java.io.InputStream;

/**
 * 文件下载相关接口
 *
 * @author ganhua
 */
public interface FileDownloadService {

    /**
     * 下载电子回执单
     *
     * @param businessType  业务类型
     * @param ncountOrderId 原交易订单号
     * @param orgTranCode   原交易代码
     * @return 返回数据 fund balance result
     */
    InputStream transferDownload(String ncountOrderId, BusinessType businessType, TranCode orgTranCode);

    InputStream transferDownload(ElectronicReceiptRequest request);


    /**
     * 下载电子回单返回base64码
     *
     * @param ncountOrderId 原交易订单号
     * @param businessType  业务类型
     * @param orgTranCode   原交易代码
     * @return 返回数据 base64
     */
    String transferDownloadBase64Pdf(String ncountOrderId, BusinessType businessType, TranCode orgTranCode);
}
