package digiwin.smartdepot.module.bean.common;

import java.io.Serializable;

/**
 * 点击待办事项其中一笔返回的 汇总数据 list.detail 接口
 */

public class ListSumBean implements Serializable {
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
    /**
     * 单位
     */
    private String unit_no;
    /**
     * 需求量
     */
    private String shortage_qty;
    /**
     * 库存量
     */
    private String stock_qty;
    /**
     * 实发量
     */
    private String scan_sumqty;

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    /**
     * 供应商
     */
    private String supplier_name;

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    /**
     * 收货日期
     * 调拨日期
     */
    private String receipt_date;
    /**
     * 收货单号
     * 调拨单号
     */
    private String receipt_no;

    public String getReceipt_qty() {
        return receipt_qty;
    }

    public void setReceipt_qty(String receipt_qty) {
        this.receipt_qty = receipt_qty;
    }

    /**
     * 收货量
     */
    private String receipt_qty;
    /**
     * 物料条码类型
     */
    private String item_barcode_type;
    /**
     *  先进先出管控否
     */
    private String fifo_check;

    public String getItem_barcode_type() {
        return item_barcode_type;
    }

    public void setItem_barcode_type(String item_barcode_type) {
        this.item_barcode_type = item_barcode_type;
    }

    public String getFifo_check() {
        return fifo_check;
    }

    public void setFifo_check(String fifo_check) {
        this.fifo_check = fifo_check;
    }

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

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getShortage_qty() {
        return shortage_qty;
    }

    public void setShortage_qty(String shortage_qty) {
        this.shortage_qty = shortage_qty;
    }

    public String getStock_qty() {
        return stock_qty;
    }

    public void setStock_qty(String stock_qty) {
        this.stock_qty = stock_qty;
    }

    public String getScan_sumqty() {
        return scan_sumqty;
    }

    public void setScan_sumqty(String scan_sumqty) {
        this.scan_sumqty = scan_sumqty;
    }


    /**
     * 发料仓
     */
    private String warehouse_out_no;
    /**
     * 接收仓
     */
    private String warehouse_in_no;
    /**
     * 部门名称
     */
    private String department_name;
    /**
     * 人员名称
     */
    private String employee_name;
    /**
     * 班组名称
     */
    private String workgroup_name;
    /**
     * 调拨项次
     */
    private String receipt_seq;
    /**
     * 申请数量
     */
    private String req_qty;
    /**
     * 备货数量
     */
    private String distribute_qty;
    /**
     * 接收数量
     */
    private String accept_qty;

    public String getWarehouse_out_no() {
        return warehouse_out_no;
    }

    public void setWarehouse_out_no(String warehouse_out_no) {
        this.warehouse_out_no = warehouse_out_no;
    }

    public String getWarehouse_in_no() {
        return warehouse_in_no;
    }

    public void setWarehouse_in_no(String warehouse_in_no) {
        this.warehouse_in_no = warehouse_in_no;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getWorkgroup_name() {
        return workgroup_name;
    }

    public void setWorkgroup_name(String workgroup_name) {
        this.workgroup_name = workgroup_name;
    }

    public String getReceipt_seq() {
        return receipt_seq;
    }

    public void setReceipt_seq(String receipt_seq) {
        this.receipt_seq = receipt_seq;
    }

    public String getReq_qty() {
        return req_qty;
    }

    public void setReq_qty(String req_qty) {
        this.req_qty = req_qty;
    }

    public String getDistribute_qty() {
        return distribute_qty;
    }

    public void setDistribute_qty(String distribute_qty) {
        this.distribute_qty = distribute_qty;
    }

    public String getAccept_qty() {
        return accept_qty;
    }

    public void setAccept_qty(String accept_qty) {
        this.accept_qty = accept_qty;
    }

    /**
     * 下阶料
     */
    private String low_order_iitem_no;

    /**
     * 下阶料品名
     */

    private String low_order_item_name;

    /**
     * 下阶料规格
     */
    private String low_order_item_spec;

    public String getLow_order_iitem_no() {
        return low_order_iitem_no;
    }

    public void setLow_order_iitem_no(String low_order_iitem_no) {
        this.low_order_iitem_no = low_order_iitem_no;
    }

    public String getLow_order_item_name() {
        return low_order_item_name;
    }

    public void setLow_order_item_name(String low_order_item_name) {
        this.low_order_item_name = low_order_item_name;
    }

    public String getLow_order_item_spec() {
        return low_order_item_spec;
    }

    public void setLow_order_item_spec(String low_order_item_spec) {
        this.low_order_item_spec = low_order_item_spec;
    }

    /**
     * 可入库量
     */
    private String available_in_qty;

    /**
     * 匹配量
     */
    private String qty;

    /**

     * 仓库
     */
    private String warehouse_no;

    public String getAvailable_in_qty() {
        return available_in_qty;
    }

    public void setAvailable_in_qty(String available_in_qty) {
        this.available_in_qty = available_in_qty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }


    /**
     * 到货单号
     */
    private String doc_no;

    /**
     * 到货日期
     */
    private String create_date;

    /**
     * 是否确认数量 1为未确认 2为确认
     */
    private String check;

    /**
     * 储位
     */
    private String storage_spaces_no;


    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
