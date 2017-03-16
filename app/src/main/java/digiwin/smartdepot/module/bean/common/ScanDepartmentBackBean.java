package digiwin.smartdepot.module.bean.common;


/**
 * @author xiemeng
 * @des 扫描部门共同使用
 * @date 2017/2/23
 */
public class ScanDepartmentBackBean {
//    department_no                 string  部门编号
//    department_name	              string	 部门名称
//    warehouse_no	              string	 仓库编号
//    Show                          string  app展示



    /**
     * 部门编号
     */
    private String department_no;
    /**
     *  部门名称
     */
    private String department_name;
    /**
     *  仓库编号
     */
    private String warehouse_no;
    /**
     * 展示
     */
    private String show;

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }
}
