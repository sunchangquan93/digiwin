package digiwin.smartdepot.module.bean.common;

import java.io.Serializable;

/**
 * 筛选结果 待办事项 数据
 */

public class FilterResultOrderBean implements Serializable {
    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    /**
     * 单号
     */
    private String doc_no;
    /**
     * 日期
     */
    private String create_date;

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    /**
     * 部门
     */

    private String department_name ;

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    /**
     * 人员
     */
    private String employee_name ;
    /**
     * 供应商名称
     */
    private String supplier_name ;
    /**
     * 送货地址
     */
    private String delivery_address;
    /**
     * 料号
     */
    private String item_no;
    /**
     * 品名
     */
    private String item_name;

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
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
}
