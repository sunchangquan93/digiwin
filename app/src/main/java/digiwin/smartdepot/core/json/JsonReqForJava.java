package digiwin.smartdepot.core.json;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.TelephonyUtils;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseApplication;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;

/**
 * @des  java平台json请求格式
 * @author  xiemeng
 * @date    2017/3/29
 */
public class JsonReqForJava {

    public static final String TAG = "JsonReqForJava";
    public JsonReqForJava(Context context) {
        this.appid= TelephonyUtils.getDeviceId(context);
        this.appversion=String.valueOf(TelephonyUtils.getMAppVersion(context));
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null!=accoutBean){
            this.acct =accoutBean.getUsername();
            this.site =accoutBean.getPlant();
            this.warehouse_no=accoutBean.getWare();
        }
        this.pagesize= (String)SharedPreferencesUtils.get(context, SharePreKey.PAGE_SETTING,AddressContants.PAGE_NUM);
    }
    /**
     * 调用设备序号
     */
    private String appid;
    /**
     * 当前app版本
     */
    private String appversion;

    /**
     * 调用模块
     */
    private String appmodule;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 调用服务名
     */
    private String service;
    /**
     * 语言
     */
    private String lang;
    /**
     * 当前用户
     */
    private String acct;
    /**
     * 集团
     */
    private String entId;
    /**
     * 当前营运中心
     */
    private String site;

    /**
     * 当前页数
     */
    private String pagenow;
    /**
     * 每页显示笔数
     */
    private String pagesize;
    /**
     * 当前仓库
     */
    private String warehouse_no;

    private Object data;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getAppmodule() {
        return appmodule;
    }

    public void setAppmodule(String appmodule) {
        this.appmodule = appmodule;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPagenow() {
        return pagenow;
    }

    public void setPagenow(String pagenow) {
        this.pagenow = pagenow;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getWarehouse_no() {
        return warehouse_no;
    }

    public void setWarehouse_no(String warehouse_no) {
        this.warehouse_no = warehouse_no;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static String toJson(Object obj){
        String s = JSON.toJSONString(obj);
        LogUtils.i(TAG,"toJson-->"+s);
        return s;
    }

    public static String toJson(String appmodule,String service,String timestamp,Object obj){
        BaseApplication application = BaseApplication.getInstance();
        JsonReqForJava reqForJava = new JsonReqForJava(application);
        reqForJava.setAppmodule(appmodule);
        reqForJava.setService(service);
        reqForJava.setTimestamp(timestamp);
        reqForJava.setData(obj);
        reqForJava.setLang("zh_CN");
        String s = JSON.toJSONString(reqForJava);
        LogUtils.i(TAG,"toJson-->"+s);
        return s;
    }
}
