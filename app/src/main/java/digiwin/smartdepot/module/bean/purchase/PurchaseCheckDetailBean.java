package digiwin.smartdepot.module.bean.purchase;

import java.io.Serializable;

import digiwin.smartdepot.module.bean.sale.scanout.ScanOutDetailKeyBean;

/**
 * @author 唐孟宇
 * @module 收获检验
 * @date 2017/3/8
 */

public class PurchaseCheckDetailBean implements Serializable{
//    seq                    string                项次
//    inspection_item        string                检验项目
//    aql                    number				AQL
//    sample_qty             number				抽验量
//    ac_qty                 number				允收量
//    re_qty                 number				拒收量
//    item_deter             string					项目判定
//    item_no             string					料号

    /**
     * 料号
     */

    private String item_no;
    /**
     * 单头项次
     */
    private String head_seq;
    /**
     * 项次
     */
    private String seq;
    /**
     * 检验项目
     */
    private String inspection_item = null;
    /**
     * AQL
     */
    private String aql = null;
    /**
     * 抽验量
     */
    private String sample_qty = null;
    /**
     * 允收量
     */
    private String ac_qty = null;
    /**
     * 缺点数
     */
    private String defect_qty = null;
    /**
     * 拒收量

     */
    private String re_qty = null;
    /**
     * 项目判定
     */
    private String item_deter = null;
    /**
     * 是否需要检验
     */
    private String qc_state;
    /**
     * pqc单号
     */
    private String doc_no;
    /**
     * 收货单号
     */
    private String receipt_no;
    /**
     * 行序
     */
    private String turn_order;

    public String getHead_seq() {
        return head_seq;
    }

    public void setHead_seq(String head_seq) {
        this.head_seq = head_seq;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }


    public String getDefect_qty() {
        return defect_qty;
    }

    public void setDefect_qty(String defect_qty) {
        this.defect_qty = defect_qty;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getInspection_item() {
        return inspection_item;
    }

    public void setInspection_item(String inspection_item) {
        this.inspection_item = inspection_item;
    }

    public String getAql() {
        return aql;
    }

    public void setAql(String aql) {
        this.aql = aql;
    }

    public String getSample_qty() {
        return sample_qty;
    }

    public void setSample_qty(String sample_qty) {
        this.sample_qty = sample_qty;
    }

    public String getAc_qty() {
        return ac_qty;
    }

    public void setAc_qty(String ac_qty) {
        this.ac_qty = ac_qty;
    }

    public String getRe_qty() {
        return re_qty;
    }

    public void setRe_qty(String re_qty) {
        this.re_qty = re_qty;
    }

    public String getItem_deter() {
        return item_deter;
    }

    public void setItem_deter(String item_deter) {
        this.item_deter = item_deter;
    }
    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getQc_state() {
        return qc_state;
    }

    public void setQc_state(String qc_state) {
        this.qc_state = qc_state;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getTurn_order() {
        return turn_order;
    }

    public void setTurn_order(String turn_order) {
        this.turn_order = turn_order;
    }

    /**
     * 重写hashcode和equals方法
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + seq.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PurchaseCheckDetailBean && this.seq.equals(((PurchaseCheckDetailBean)o).seq);
    }



}
