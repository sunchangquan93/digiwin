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

    public String department_name = null;

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    /**
     * 部门
     */


    public String employee_no = null;

}
