package digiwin.smartdepot.login.bean;

import org.litepal.crud.DataSupport;

/**
 * @author xiemeng
 * @des 用户信息
 * @date 2017/1/18
 */
public class AccoutBean extends DataSupport {
    /**
     * id
     */
    private  int id;
    /**
     * varchar2(10)  营运中心
     */
    private String plant;
    /**
     * varchar2(10)  用户编码
     */
    private String username;
    /**
     * varchar2(80)  用户名称
     */
    private String realname;
    /**
     * varchar2(10)  部门编码
     */
    private String departcode;
    /**
     * varchar2(80)   部门名称
     */
    private String departname;
    /**
     * varchar2(4000) 权限模组
     */
    private String access;
    /**
     * varchar2(4000) 更新地址
     */
    private String verurl;
    /**
     * varchar2(10)   服务器版本号
     */
    private String vernum;
    /**
     * varchar2(10)   本次更新说明
     */
    private String verwhat;

    /**
     * 是否记住密码。Y为记住，N为否
     */
    private String isRemeberPassWord;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 仓库
     */
    private String ware;

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDepartcode() {
        return departcode;
    }

    public void setDepartcode(String departcode) {
        this.departcode = departcode;
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getVerurl() {
        return verurl;
    }

    public void setVerurl(String verurl) {
        this.verurl = verurl;
    }

    public String getVernum() {
        return vernum;
    }

    public void setVernum(String vernum) {
        this.vernum = vernum;
    }

    public String getVerwhat() {
        return verwhat;
    }

    public void setVerwhat(String verwhat) {
        this.verwhat = verwhat;
    }

    public String getIsRemeberPassWord() {
        return isRemeberPassWord;
    }

    public void setIsRemeberPassWord(String isRemeberPassWord) {
        this.isRemeberPassWord = isRemeberPassWord;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWare() {
        return ware;
    }

    public void setWare(String ware) {
        this.ware = ware;
    }
}
