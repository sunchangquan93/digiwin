package digiwin.smartdepot.module.bean.sale.scanout;

/**
 * @author maoheng
 * @des 扫码出货
 * @date 2017/4/3
 */

public class ScanOutSaveBean {
    /**
     * 箱条码
     */
    private String package_no;
    /**
     * 出仓库
     */
    private String warehouse_out_no;
    /**
     * 出库位
     */
    private String storage_spaces_out_no;
    /**
     * 入仓库
     */
    private String warehouse_in_no;
    /**
     * 入库位
     */
    private String storage_spaces_in_no;
    /**
     * 单号
     */
    private String doc_no;



    public String getPackage_no() {
        return package_no;
    }

    public void setPackage_no(String package_no) {
        this.package_no = package_no;
    }

    public String getWarehouse_out_no() {
        return warehouse_out_no;
    }

    public void setWarehouse_out_no(String warehouse_out_no) {
        this.warehouse_out_no = warehouse_out_no;
    }

    public String getStorage_spaces_out_no() {
        return storage_spaces_out_no;
    }

    public void setStorage_spaces_out_no(String storage_spaces_out_no) {
        this.storage_spaces_out_no = storage_spaces_out_no;
    }

    public String getWarehouse_in_no() {
        return warehouse_in_no;
    }

    public void setWarehouse_in_no(String warehouse_in_no) {
        this.warehouse_in_no = warehouse_in_no;
    }

    public String getStorage_spaces_in_no() {
        return storage_spaces_in_no;
    }

    public void setStorage_spaces_in_no(String storage_spaces_in_no) {
        this.storage_spaces_in_no = storage_spaces_in_no;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }
}
