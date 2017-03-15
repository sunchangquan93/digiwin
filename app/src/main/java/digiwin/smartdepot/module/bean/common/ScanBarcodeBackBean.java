package digiwin.smartdepot.module.bean.common;

/**
 * @des      扫码物料条码共同使用
 * @author  xiemeng
 * @date    2017/2/23
 */
public class ScanBarcodeBackBean {

//    barcode          STRING  #条码
//    item_no          STRING  #料号
//    item_name        STRING  #品名
//    item_spec         STRING  #规格
//    unit_no           STRING  #单位
//    lot_no            STRING  #批次号
//    lot_date           STRING  #批次日期
//    vld_date          STRING  #批次有效期至
//    barcode_qty       STRING  #条码数量
//    barcode_seq       STRING  #条码序列号
//    item_barcode_type  STRING  #物料条码类型
//    available_in_qty    STRING  #可入库量
//    scan_sumqty       STRING  #扫描汇总量
//    barcode_no        STRING  #返回的物料条码


    /**
     *  条码
     */
    private String barcode;
    /**
     * 实际条码
     */
    private String barcode_no;

    /**
     *  扫描汇总数量
     */
    private String scan_sumqty;
    /**
     *  条码数量
     */
    private String barcode_qty;
    /**
     * 展示
     */
    private  String show;
    /**
     * 料号
     */
    private String item_no;
    /**
     * 品名
     */
    private String item_name;
    /**
     * 规格
     */
    private String item_spec;

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

    public String getItem_barcode_type() {
        return item_barcode_type;
    }

    public void setItem_barcode_type(String item_barcode_type) {
        this.item_barcode_type = item_barcode_type;
    }

    /**
     * 物料条码类型
     */
    private String item_barcode_type;

    /**
     * 单位
     */
    private String unit_no ;
    /**
     * 批次号
     */
    private String lot_no ;

    /**
     * 可入库量--生产完工入库扫码条码使用
     */
    private  String available_in_qty;

    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode_qty() {
        return barcode_qty;
    }

    public void setBarcode_qty(String barcode_qty) {
        this.barcode_qty = barcode_qty;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getAvailable_in_qty() {
        return available_in_qty;
    }

    public void setAvailable_in_qty(String available_in_qty) {
        this.available_in_qty = available_in_qty;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }

    public String getBarcode_no() {
        return barcode_no;
    }

    public void setBarcode_no(String barcode_no) {
        this.barcode_no = barcode_no;
    }
}
