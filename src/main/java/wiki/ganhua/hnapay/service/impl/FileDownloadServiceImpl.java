package wiki.ganhua.hnapay.service.impl;

import cn.hutool.core.codec.Base64;
import lombok.RequiredArgsConstructor;
import wiki.ganhua.hnapay.bean.request.enums.BusinessType;
import wiki.ganhua.hnapay.bean.request.file.ElectronicReceiptRequest;
import wiki.ganhua.hnapay.bean.result.enums.TranCode;
import wiki.ganhua.hnapay.bean.result.file.ElectronicReceiptResult;
import wiki.ganhua.hnapay.exception.HnaPayDataException;
import wiki.ganhua.hnapay.service.FileDownloadService;
import wiki.ganhua.hnapay.service.HnaPayService;
import wiki.ganhua.hnapay.service.impl.base.BaseRequestImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author ganhua
 */
@RequiredArgsConstructor
public class FileDownloadServiceImpl extends BaseRequestImpl implements FileDownloadService {

    private final HnaPayService hnaPayService;

    @Override
    public InputStream transferDownload(String ncountOrderId, BusinessType businessType, TranCode orgTranCode) {
        ElectronicReceiptRequest request = new ElectronicReceiptRequest();
        request.setNcountOrderId(ncountOrderId);
        request.setBusinessType(businessType);
        request.setOrgTranCode(orgTranCode);
        return this.transferDownload(request);
    }

    @Override
    public InputStream transferDownload(ElectronicReceiptRequest request) {
        String url = String.format("%s/t011.htm", this.hnaPayService.getPayBaseUrl());
        request.setUrl(url);
        ElectronicReceiptResult result = this.request(hnaPayService, request, ElectronicReceiptResult.class);
        boolean base64 = Base64.isBase64(result.getReciptBase64Pdf());
        if (base64) {
            byte[] decode = Base64.decode(result.getReciptBase64Pdf());
            return new ByteArrayInputStream(decode);
        }
        throw new HnaPayDataException("数据异常，下载失败");
    }

    @Override
    public String transferDownloadBase64Pdf(String ncountOrderId, BusinessType businessType, TranCode orgTranCode) {
        ElectronicReceiptRequest request = new ElectronicReceiptRequest();
        request.setNcountOrderId(ncountOrderId);
        request.setBusinessType(businessType);
        request.setOrgTranCode(orgTranCode);
        String url = String.format("%s/t011.htm", this.hnaPayService.getPayBaseUrl());
        request.setUrl(url);
        ElectronicReceiptResult result = this.request(hnaPayService, request, ElectronicReceiptResult.class);
        return result.getReciptBase64Pdf();
    }
}
