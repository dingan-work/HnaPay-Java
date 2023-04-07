package wiki.ganhua.hnapay.bean.result.file;

import wiki.ganhua.hnapay.bean.result.BaseResult;

/**
 *
 * @author ganhua
 */
public class ElectronicReceiptResult extends BaseResult {

    @Override
    public byte[] signContent() {
        return new byte[0];
    }
}
