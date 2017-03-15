package digiwin.smartdepot.module.bean.common;


/**
 * @author xiemeng
 * @des 扫描仓库共同使用
 * @date 2017/2/23
 */
public class ScanLocatorBackBean {
    /**
     * 储位编号
     */
    private String storage_spaces_no;
    /**
     *  仓库编号
     */
    private String warehouse_no;
    /**
     * 展示
     */
    private String show;

    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
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
