package digiwin.smartdepot.module.bean.purchase;

import java.io.Serializable;

/**
 * Created by qGod
 * 2017/3/22
 * 解析供应商bean
 */

public class ScanSupplierBean implements Serializable{
    /**
     * 供应商编号
     */
    private String supplier_no;
    /**
     * 供应商名称
     */
    private String supplier_shortname;
    /**
     * show
     */
    private String show;

    public String getSupplier_no() {
        return supplier_no;
    }

    public void setSupplier_no(String supplier_no) {
        this.supplier_no = supplier_no;
    }

    public String getSupplier_shortname() {
        return supplier_shortname;
    }

    public void setSupplier_shortname(String supplier_shortname) {
        this.supplier_shortname = supplier_shortname;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }
}
