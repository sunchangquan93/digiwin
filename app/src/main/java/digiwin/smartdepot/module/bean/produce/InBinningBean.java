package digiwin.smartdepot.module.bean.produce;

/**
 * Created by qGod on 2017/4/5
 * Thank you for watching my code
 */

public class InBinningBean {
    /**
     * 单号
     */
    private String wo_no ;
    /**
     * 部门编号
     */
    private String department_no;
    /**
     * 物料条码
     */
    private String item_no;

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }
}
