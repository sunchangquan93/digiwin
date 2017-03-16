package digiwin.smartdepot.module.bean.common;

/**
 * Created by 唐孟宇 on 2017/3/13.
 */

/**
 * 点击待办事项其中一笔传入的数据
 */
public class ClickItemPutBean {
    /**
     * 单号
     */
    private String doc_no = null;

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

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

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    /**
     * 发出仓
     */
    private String warehouse_out_no ;
    /**
     * 拨入仓
     */
    private String warehouse_in_no ;
    /**
     * 日期
     */
    private String receipt_date ;
    /**
     * 调拨单号
     */
    private String receipt_no;


    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    private String warehouse_no = null;

    private String item_no = null;

    /**
     * 工单编号
     */
    private String wo_no;

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    /**
     * 数量
     */
    private String qty;

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}