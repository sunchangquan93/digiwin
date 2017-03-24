package digiwin.smartdepot.module.bean.purchase;

import java.io.Serializable;

/**
 * @author 唐孟宇
 * @module 收获检验
 * @date 2017/3/8
 */

public class PurchaseCheckBean implements Serializable{
//    receipt_no            string        收货单号
//    suppier_name         string        供应商
//    delivery_bill_no        string        送货单号
//    turn_order            string        分批顺序
//    receipt_date           string        收货时间
//    wait_min              number        等待分钟
//    seq                   string        项次
//    item_no               string        料号
//    item_name             string        品名
//    item_spec             string        规格
//    drawing_no            string        图号
//    qty					  string        待检数量

    /**
     * 收货单号
     */
    public String receipt_no;
    /**
     * 供应商
     */
    public String suppier_name;
    /**
     *  送货单号
     */
    public String delivery_bill_no;
    /**
     * 分批顺序
     */
    public String turn_order;
    /**
     * 收货时间
     */
    public String receipt_date;
    /**
     * 等待分钟
     */
    public String wait_min;

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getSuppier_name() {
        return suppier_name;
    }

    public void setSuppier_name(String suppier_name) {
        this.suppier_name = suppier_name;
    }

    public String getDelivery_bill_no() {
        return delivery_bill_no;
    }

    public void setDelivery_bill_no(String delivery_bill_no) {
        this.delivery_bill_no = delivery_bill_no;
    }

    public String getTurn_order() {
        return turn_order;
    }

    public void setTurn_order(String turn_order) {
        this.turn_order = turn_order;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    public String getWait_min() {
        return wait_min;
    }

    public void setWait_min(String wait_min) {
        this.wait_min = wait_min;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
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

    public String getDrawing_no() {
        return drawing_no;
    }

    public void setDrawing_no(String drawing_no) {
        this.drawing_no = drawing_no;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     * 项次

     */
    public String seq;
    /**
     * 料号
     */
    public String item_no;
    /**
     * 品名
     */
    public String item_name;
    /**
     * 规格
     */
    public String item_spec;
    /**
     * 图号
     */
    public String drawing_no;
    /**
     * 待检数量
     */
    public String qty;

    public String getQc_state() {
        return qc_state;
    }

    public void setQc_state(String qc_state) {
        this.qc_state = qc_state;
    }

    /**
     * 判定状态
     */
    public String qc_state;
    /**
     * 判定状态
     */
    public String ok_qty;

    public String getOk_qty() {
        return ok_qty;
    }

    public void setOk_qty(String ok_qty) {
        this.ok_qty = ok_qty;
    }
}
