package digiwin.smartdepot.module.bean.produce;

import java.io.Serializable;

/**
 * @des 领料过账 FIFO
 * @author 唐孟宇
 */

public class PostMaterialFIFOBean implements Serializable {
//    item_no              string       料号
//    item_name           string       品名
//    item_spec            string       規格
//    storage_spaces_no     string       库位编号
//    barcode_no           string       物料条码
//    stock_qty             number       库存量
//    shortage_qty          number       需求量
//    recommended_qty     number       建议量
//    scan_sumqty          number       实发量
//    lot_no          number       批号

    /**
     * 料号
     */
    private String item_no;
    /**
     * 品名
     */
    private String item_name;
    /**
     * 規格
     */
    private String item_spec;

    /**
     * 库位编号
     */
    private String storage_spaces_no;
    /**
     * 物料条码
     */
    private String barcode_no;

    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }

    /**
     * 批号
     */
    private String lot_no;
    /**
     * 库存量
     */
    private String stock_qty;
    /**
     * 需求量
     */
    private String shortage_qty;
    /**
     * 建议量
     */
    private String recommended_qty;
    /**
     * 实发量
     */
    private String scan_sumqty;

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_spec() {
        return item_spec;
    }

    public void setItem_spec(String item_spec) {
        this.item_spec = item_spec;
    }

    public String getShortage_qty() {
        return shortage_qty;
    }

    public void setShortage_qty(String shortage_qty) {
        this.shortage_qty = shortage_qty;
    }

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
