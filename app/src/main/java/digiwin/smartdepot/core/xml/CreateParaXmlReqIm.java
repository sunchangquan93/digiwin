package digiwin.smartdepot.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;
import java.util.Map;

import digiwin.smartdepot.core.base.BaseApplication;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.library.utils.MD5Util;
import digiwin.library.utils.TelephonyUtils;
import digiwin.library.xml.CreateParaXmlReq;

/**
 * @des 进一步使用xml
 * @author xiemeng
 * @date 2017/1/3
 */
@XStreamAlias("soapenv:Envelope")
public class CreateParaXmlReqIm extends CreateParaXmlReq {

   private CreateParaXmlReqIm(String userName, String plant, Map<String, String> map, String deviceId, String appmodule, String reqType, String timestamp){
        super( userName, plant, map, deviceId, appmodule,reqType,timestamp);
   }
    private CreateParaXmlReqIm(String userName, String masterName, String plant, List<Map<String, String>> maps, String deviceId, String appmodule, String reqType, String timestamp){
        super( userName,masterName, plant, maps, deviceId, appmodule,reqType,timestamp);
    }
    private CreateParaXmlReqIm(String userName, String plant, List<Map<String, String>> maps, String deviceId, String appmodule, String reqType, String timestamp){
        super( userName, plant, maps, deviceId, appmodule,reqType,timestamp);
    }
    /**
     * 无master，一个detail
     */
    private CreateParaXmlReqIm(String userName, String plant, String deviceId, String appmodule, String reqType, String timestamp, List<Map<String, String>> detailMaps) {
        super(userName, plant, deviceId, appmodule, reqType, timestamp, detailMaps);
    }
    /**
     * 无master，两个detail
     */
    private CreateParaXmlReqIm(String userName, String plant, List<Map<String, String>> detailMaps, List<Map<String, String>> detail2Maps, String deviceId, String appmodule, String reqType, String timestamp){
        super( userName, plant, detailMaps,detail2Maps, deviceId, appmodule,reqType,timestamp);
    }
    /**
     * 一个master，一个detail
     */
    private CreateParaXmlReqIm(String userName, String plant, String deviceId, String appmodule, String reqType, String timestamp, List<Map<String, String>> masters, List<Map<String, String>> details){
        super( userName, plant, deviceId, appmodule,reqType,timestamp, masters,details);
    }
    /**
     * 一个master，不同detail
     */
    private CreateParaXmlReqIm(String userName, String plant, String deviceId, String appmodule, String reqType, String timestamp, List<Map<String, String>> masters, List<List<Map<String, String>>> details,String flag){
        super( userName, plant, deviceId, appmodule,reqType,timestamp, masters,details,flag);
    }

    /**
     * 获取xml解析实体
     * @param map     parameter中集合
     * @param appmodule  模块名称
     * @return xml解析实体
     */
    public static CreateParaXmlReqIm getInstance( Map<String, String> map, String appmodule,String reqType,String timestamp ) {
        String userName="tiptop";
        String plant="SYSTEM";
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null!=accoutBean){
            userName = accoutBean.getUsername();
            plant = accoutBean.getPlant();
        }
        String deviceId= TelephonyUtils.getDeviceId(BaseApplication.getInstance());
        CreateParaXmlReqIm   createParaXmlReqIm = new CreateParaXmlReqIm( userName, plant, map,deviceId,appmodule,reqType,timestamp);
        return createParaXmlReqIm;
    }
    /**
     * 获取xml解析实体
     * @param maps     RecordSet中集合
     * @param appmodule  模块名称
     * @return xml解析实体
     */
    public static CreateParaXmlReqIm getInstance(List<Map<String, String>> maps, String appmodule, String reqType, String timestamp ) {
        String userName="tiptop";
        String plant="SYSTEM";
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null!=accoutBean){
            userName = accoutBean.getUsername();
            plant = accoutBean.getPlant();
        }
        String deviceId= TelephonyUtils.getDeviceId(BaseApplication.getInstance());
        CreateParaXmlReqIm   createParaXmlReqIm = new CreateParaXmlReqIm( userName, plant, maps,deviceId,appmodule,reqType,timestamp);
        return createParaXmlReqIm;
    }
    /**
     * 获取xml解析实体
     * @param maps     RecordSet中集合
     * @param appmodule  模块名称
     * @return xml解析实体
     */
    public static CreateParaXmlReqIm getInstance(List<Map<String, String>> maps, String masterName, String appmodule, String reqType, String timestamp ) {
        String userName="tiptop";
        String plant="SYSTEM";
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null!=accoutBean){
            userName = accoutBean.getUsername();
            plant = accoutBean.getPlant();
        }
        String deviceId= TelephonyUtils.getDeviceId(BaseApplication.getInstance());
        CreateParaXmlReqIm   createParaXmlReqIm = new CreateParaXmlReqIm( userName, masterName,plant, maps,deviceId,appmodule,reqType,timestamp);
        return createParaXmlReqIm;
    }
    /**
     * 获取xml解析实体
     * 登录专用接口
     * @param map     parameter中集合
     * @param plant   营用中心
     * @param appmodule  模块名称
     * @return xml解析实体
     */
    public static CreateParaXmlReqIm getInstance( Map<String, String> map,String plant, String appmodule ,String reqType,String timestamp) {
        String userName = "tiptop";
        String deviceId= MD5Util.md5EncodeDeviceId(TelephonyUtils.getDeviceId(BaseApplication.getInstance()));
        CreateParaXmlReqIm   createParaXmlReqIm = new CreateParaXmlReqIm( userName, plant, map,deviceId,appmodule,reqType,timestamp);
        return createParaXmlReqIm;
    }

    /**
     *  无单头，两个单身
     */
    public static CreateParaXmlReqIm getInstance(List<Map<String, String>> detailMaps, List<Map<String, String>> detail2Maps, String appmodule, String reqType, String timestamp ) {
        String userName="tiptop";
        String plant="SYSTEM";
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null!=accoutBean){
            userName = accoutBean.getUsername();
            plant = accoutBean.getPlant();
        }
        String deviceId= TelephonyUtils.getDeviceId(BaseApplication.getInstance());
        CreateParaXmlReqIm   createParaXmlReqIm = new CreateParaXmlReqIm( userName, plant, detailMaps,detail2Maps,deviceId,appmodule,reqType,timestamp);
        return createParaXmlReqIm;
    }
    /**
     *  一个单头，相同单身
     */
    public static CreateParaXmlReqIm getInstance( String appmodule, String reqType, String timestamp,
                                                  List<Map<String, String>> masters, List<Map<String, String>> details ) {
        String userName="tiptop";
        String plant="SYSTEM";
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null!=accoutBean){
            userName = accoutBean.getUsername();
            plant = accoutBean.getPlant();
        }
        String deviceId= TelephonyUtils.getDeviceId(BaseApplication.getInstance());
        CreateParaXmlReqIm   createParaXmlReqIm = new CreateParaXmlReqIm( userName, plant,deviceId,appmodule,reqType,timestamp, masters,details);
        return createParaXmlReqIm;
    }
    /**
     *  一个单头，不同单身
     */
    public static CreateParaXmlReqIm getInstance( String appmodule, String reqType, String timestamp,
                                                  List<Map<String, String>> masters, List<List<Map<String, String>>> details ,String flag) {
        String userName="tiptop";
        String plant="SYSTEM";
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null!=accoutBean){
            userName = accoutBean.getUsername();
            plant = accoutBean.getPlant();
        }
        String deviceId= TelephonyUtils.getDeviceId(BaseApplication.getInstance());
        CreateParaXmlReqIm   createParaXmlReqIm = new CreateParaXmlReqIm( userName, plant,deviceId,appmodule,reqType,timestamp, masters,details,flag);
        return createParaXmlReqIm;
    }

    /**
     * 无单头，一个单身
     */
    public static CreateParaXmlReqIm getInstance( String appmodule, String reqType, String timestamp,List<Map<String, String>> detailMaps) {
        String userName = "tiptop";
        String plant = "SYSTEM";
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null != accoutBean) {
            userName = accoutBean.getUsername();
            plant = accoutBean.getPlant();
        }
        String deviceId = TelephonyUtils.getDeviceId(BaseApplication.getInstance());
        CreateParaXmlReqIm createParaXmlReqIm = new CreateParaXmlReqIm(userName, plant,deviceId, appmodule, reqType, timestamp,detailMaps);
        return createParaXmlReqIm;
    }
}
