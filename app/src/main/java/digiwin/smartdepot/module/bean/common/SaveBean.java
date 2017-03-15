package digiwin.smartdepot.module.bean.common;

/**
 * @author xiemeng
 * @des 保存通用
 * @date 2017/2/23
 */
public class SaveBean {
    /**
     * 物料条码
     */
    private String barcode_no;
    /**
     * 料号
     */
    private String item_no;

    /**
     * 出仓库代码
     */
    private String warehouse_out_no;
    /**
     * 出储位代码
     */
    private String storage_spaces_out_no;
    /**
     * 入仓库代码
     */
    private String warehouse_in_no;
    /**
     * 入储位代码
     */
    private String storage_spaces_in_no;
    /**
     * 批次号
     */
    private String lot_no;
    /**
     * 料件单位
     */
    private String unit_no;
    /**
     * 库存/欠料量
     */
    private String qty;
    /**
     * 可入库量
     */
    private String available_in_qty;

    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }

    /**
     * 扫描汇总量
     */
    private String scan_sumqty;
    /**
     * 班组
     */
    private String workgroup_no;
    /**
     * 部门
     */
    private String department_no;
    /**
     * 工作站
     */
    private String workstation_no;
    /**
     * 客户
     */
    private String customer_no;
    /**
     * 工单号
     */
    private String wo_no;
    /**
     * 来源单号
     */
    private String doc_no;
    /**
     * 理由码
     */
    private String reason_code_no;


    public String getWorkgroup_no() {
        return workgroup_no;
    }

    public void setWorkgroup_no(String workgroup_no) {
        this.workgroup_no = workgroup_no;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getWorkstation_no() {
        return workstation_no;
    }

    public void setWorkstation_no(String workstation_no) {
        this.workstation_no = workstation_no;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getBarcode_no() {
        return barcode_no;
    }

    public void setBarcode_no(String barcode_no) {
        this.barcode_no = barcode_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getWarehouse_in_no() {
        return warehouse_in_no;
    }

    public void setWarehouse_in_no(String warehouse_in_no) {
        this.warehouse_in_no = warehouse_in_no;
    }

    public String getStorage_spaces_in_no() {
        return storage_spaces_in_no;
    }

    public void setStorage_spaces_in_no(String storage_spaces_in_no) {
        this.storage_spaces_in_no = storage_spaces_in_no;
    }

    public String getLot_no() {
        return lot_no;
    }

    public void setLot_no(String lot_no) {
        this.lot_no = lot_no;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getAvailable_in_qty() {
        return available_in_qty;
    }

    public void setAvailable_in_qty(String available_in_qty) {
        this.available_in_qty = available_in_qty;
    }

    public String getWarehouse_out_no() {
        return warehouse_out_no;
    }

    public void setWarehouse_out_no(String warehouse_out_no) {
        this.warehouse_out_no = warehouse_out_no;
    }

    public String getStorage_spaces_out_no() {
        return storage_spaces_out_no;
    }

    public void setStorage_spaces_out_no(String storage_spaces_out_no) {
        this.storage_spaces_out_no = storage_spaces_out_no;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getReason_code_no() {
        return reason_code_no;
    }

    public void setReason_code_no(String reason_code_no) {
        this.reason_code_no = reason_code_no;
    }
}
