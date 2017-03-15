package digiwin.smartdepot.module.bean.produce;

import java.io.Serializable;

/**
 * @author 赵浩然
 * @module
 * @date 2017/3/3
 */

public class FiFoBean implements Serializable {
    /**
     * 储位编号
     */
    private String storage_spaces_no;

    /**
     * 物料条码
     */
    private String barcode_no;

    /**
     * 库存量
     */
    private String stock_qty;

    /**
     * 建议量
     */
    private String recommended_qty;

    /**
     * 实发量
     */
    private String scan_sumqty;

    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
    }

    public String getBarcode_no() {
        return barcode_no;
    }

    public void setBarcode_no(String barcode_no) {
        this.barcode_no = barcode_no;
    }

    public String getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(String stock_qty) {
        this.stock_qty = stock_qty;
    }

    public String getRecommended_qty() {
        return recommended_qty;
    }

    public void setRecommended_qty(String recommended_qty) {
        this.recommended_qty = recommended_qty;
    }

    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }
}
