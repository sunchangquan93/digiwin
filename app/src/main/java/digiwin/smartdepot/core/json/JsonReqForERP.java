package digiwin.smartdepot.core.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;

import digiwin.library.utils.LogUtils;

/**
 * 
 * 发送Json
 * 
 * @author xiemeng
 * @version [V1.00, 2016-8-8]
 * @see [相关类/方法]
 * @since V1.00
 */
public  class JsonReqForERP
{
    /**
     * parameter数据
     */
    public static Object parameter = null;
    
    /**
     * data数据
     */
    public static Object data = null;
    
    public static final String TAG = "JsonReqForERP";
    
    public static class Request
    {
        @JSONField(name = "key", ordinal = 1)
        public String key = "d41d8cd98f00b204e9800998ecf8427e";
        
        @JSONField(name = "type", ordinal = 2)
        public String type = "sync";
        
        @JSONField(name = "host", ordinal = 3)
        public Host host = new Host();
        
        @JSONField(name = "datakey", ordinal = 4)
        public Datakey datakey = new Datakey();
        
        @JSONField(name = "service", ordinal = 5)
        public Service service = new Service();
        
        @JSONField(name = "payload", ordinal = 6)
        public Payload payload = new Payload();
    }
    
    public static class Host
    {
        @JSONField(name = "prod", ordinal = 1)
        public String prod = "APP";
        
        @JSONField(name = "ip", ordinal = 2)
        public String ip = "192.168.10.1";
        
        @JSONField(name = "lang", ordinal = 3)
        public String id = "zh_CN";
        
        @JSONField(name = "acct", ordinal = 4)
        public String acct = "tiptop";
        
        @JSONField(name = "timestamp", ordinal = 5)
        public String timestamp = "20151211123204361";
    }
    
    public static class Service
    {
        @JSONField(name = "prod", ordinal = 1)
        public String prod = "T100";
        
        @JSONField(name = "name", ordinal = 2)
        public String name = "recipe_upload";
        
        @JSONField(name = "ip", ordinal = 1)
        public String ip = "10.40.40.18";
        
        @JSONField(name = "id", ordinal = 2)
        public String id = "topprd";
        
    }
    
    public static class Datakey
    {
        
        @JSONField(name = "EntId", ordinal = 1)
        public String EntId = "1";
        
        @JSONField(name = "CompanyId", ordinal = 2)
        public String CompanyId = "ZMB";
        
    }
    
    public static class Payload
    {
        @JSONField(name = "std_data", ordinal = 1)
        public Std_Data std_data = new Std_Data();
    }
    
    public static class Std_Data
    {
        @JSONField(name = "parameter", ordinal = 1)
        public Object parameter = JsonReqForERP.parameter;
        
        @JSONField(name = "data", ordinal = 2)
        public Object data = JsonReqForERP.data;
    }
    
    /**
     * 没有查询条件获取数据,没有payload节点
     */
    public static String noWhereJson(String type)
    {
        String req = "";
        try {
            Request main = new Request();
            inithead(main, type);
            JSONObject object = (JSONObject) JSON.toJSON(main);
            object.remove("payload");
            req = object.toJSONString();
            LogUtils.i(TAG, "noWhereJson----->" + req);
        }catch (Exception e){
            LogUtils.e(TAG,"noWhereJson错误");
        }
        return req;
    }
    /**
     * 没有查询条件获取数据,有payload节点
     */
    public static String noWhereJson1(String type)
    {
        String req = "";
        try {
            Request main = new Request();
            inithead(main, type);
            JSONObject object = (JSONObject) JSON.toJSON(main);
            req = object.toJSONString();
            LogUtils.i(TAG, "noWhereJson----->" + req);
        }catch (Exception e){
            LogUtils.e(TAG,"noWhereJson1错误");
        }
        return req;
    }
    
    /**
     * 将parameter中数据组成hashmap方式传入
     */
    public static String mapCreateJson(HashMap<String, Object> map, String serviceName)
    {
        String req = "";
        try {
            Std_Data param = new Std_Data();
            JSONObject object = (JSONObject) JSON.toJSON(param);
            object.putAll(map);
            object.remove("data");
            Request main = new Request();
            inithead(main, serviceName);
            main.payload.std_data.parameter = object;
            req = JSON.toJSONString(main);
            LogUtils.i(TAG, "mapjson------>" + req);
        }catch (Exception e){
            LogUtils.e(TAG,"mapCreateJson异常");
        }
        return req;
    }
    
    /**
     * param内数据传递
     * 当中数据需根据具体模块封装
     */
    public static String objCreateJson(Object objs, String serviceName)
    {
        String req = "";
        try{
        if (!(objs instanceof HashMap))
        {
            parameter = objs;
            Std_Data param = new Std_Data();
            JSONObject object = (JSONObject)JSON.toJSON(param);
            object.remove("data");
            object.remove("parameter");
            object.put("parameter", parameter);
            Request main = new Request();
            inithead(main, serviceName);
            req = JSON.toJSONString(main);
            LogUtils.i(TAG, "objs------>" + req);
        }}catch (Exception e ){
            LogUtils.e(TAG,"objCreateJson异常");
        }
        return req;
    }
    
    /**
     * 初始化用户名等信息
     */
    public   static  void inithead(Request main, String type){
        main.service.name = type;
        if (type.equals("user.infomation.get"))
        {
//            main.datakey.CompanyId = SystemConstants.COMID;
//            main.datakey.EntId = SystemConstants.ENTID;
//            main.host.acct = SystemConstants.USER;
        }
        else
        {
//            UserData userData = LoginManager.getInstance(ErpApplication.getInstance()).getUserData();
//            if (null != userData && null != userData.site_no && null != userData.enterprise_no)
//            {
//                main.datakey.CompanyId = userData.site_no;
//                main.datakey.EntId = userData.enterprise_no;
//            }
//            if (null != userData && null != userData.username)
//            {
//                main.host.acct = userData.username;
//            }
        }
    };

}
