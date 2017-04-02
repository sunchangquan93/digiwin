package digiwin.smartdepot.core.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;
import java.util.Map;

import digiwin.library.utils.MD5Util;
import digiwin.library.utils.TelephonyUtils;
import digiwin.library.xml.CreateParaXmlReq;
import digiwin.smartdepot.core.base.BaseApplication;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.bean.purchase.BadReasonBean;

/**
 * @des 进一步使用xml
 * @author xiemeng
 * @date 2017/1/3
 */
@XStreamAlias("soapenv:Envelope")
public class CreateParaXmlReqIm extends CreateParaXmlReq {

   private CreateParaXmlReqIm(String userName,String plant,Map<String, String> map,String deviceId,String appmodule,String reqType,String timestamp){
        super( userName, plant, map, deviceId, appmodule,reqType,timestamp);
   }
    private CreateParaXmlReqIm(String userName,String plant,List<Map<String, String>> maps,String deviceId,String appmodule,String reqType,String timestamp){
        super( userName, plant, maps, deviceId, appmodule,reqType,timestamp);
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
}
