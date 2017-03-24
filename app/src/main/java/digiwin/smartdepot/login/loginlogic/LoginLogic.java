package digiwin.smartdepot.login.loginlogic;

import android.content.Context;
import android.support.annotation.Nullable;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ReqTypeName;
import digiwin.smartdepot.core.net.IRequestCallbackImp;
import digiwin.smartdepot.core.net.OkhttpRequest;
import digiwin.smartdepot.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.bean.PlantBean;


/**
 * @author xiemeng
 * @des 登录页面逻辑
 * @date 2017/1/18
 */
public class LoginLogic {

    private static final String TAG = "LoginLogic";

    private Context mContext;
    /**
     * 模块编码
     */
    private String mModule="";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp="";

    private static LoginLogic logic;

    private LoginLogic(Context context,String module,String timestamp) {
        mContext = context;
        mModule=module;
        mTimestamp=timestamp;
    }

    /**
     * 获取单例
     */
    public static LoginLogic getInstance(Context context,String module,String timestamp) {
        return logic = new LoginLogic(context, module,timestamp);
    }

    /**
     * 获取营用中心
     */
    public interface GetPlantListener {
        public void onSuccess(List<String> plants);

        public void onFailed(String msg);
    }

    /**
     * 获取营用中心
     */
    public void getPlant(final Map<String, String> map, final GetPlantListener listener) {
        try {
            String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETOC,mTimestamp).toXml();
            OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    String error =mContext.getString(R.string.unknow_error);
                    ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETOC, string);
                    if (null != xmlResp) {
                        if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                            List<PlantBean> plantBeens = xmlResp.getParameterDatas(PlantBean.class);
                            if (plantBeens.size() > 0) {
                                String plants = plantBeens.get(0).getPlant();
                                List<String> split = StringUtils.split(plants);
                                listener.onSuccess(split);
                                return;
                            } else {
                                error = mContext.getString(R.string.data_null);
                            }
                        } else {
                            error = xmlResp.getDescription();
                        }
                    }
                    listener.onFailed(error);
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "getPlant异常");
            listener.onFailed(mContext.getString(R.string.unknow_error));
        }
    }

    /**
     * 登录
     */
    public interface LoginListener {
        public void onSuccess(AccoutBean accoutBean);

        public void onFailed(String msg);
    }

    /**
     * 登录
     */
    public void login(final Map<String, String> map,String plant, final LoginListener listener) {
        try {
            final String xml = CreateParaXmlReqIm.getInstance(map,plant,mModule,ReqTypeName.GETAC,mTimestamp).toXml();
            OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETAC, string);
                    String error = mContext.getString(R.string.login_error);
                    if (null != xmlResp) {
                        if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                            List<AccoutBean> accoutBeen = xmlResp.getParameterDatas(AccoutBean.class);
                            if (accoutBeen.size() > 0) {
                                listener.onSuccess(accoutBeen.get(0));
                                return;
                            } else {
                                error = mContext.getString(R.string.data_null);
                            }
                        } else {
                            error = xmlResp.getDescription();
                        }
                    }
                    listener.onFailed(error);
                }
            });
        } catch (Exception e) {
            listener.onFailed(mContext.getString(R.string.unknow_error));
            LogUtils.e(TAG, "login异常");
        }

    }

    /**
     * 需要判断是否为null
     * @return
     */
    @Nullable
    public static AccoutBean getUserInfo(){
        Connector.getDatabase();
        List<AccoutBean> accoutBeen = DataSupport.findAll(AccoutBean.class);
        if (accoutBeen.size()>0){
            AccoutBean accoutBean = accoutBeen.get(0);
            return  accoutBean;
        }
        return  null;
    }
    /**
     *获取仓库
     */
    public static String getWare(){
        String ware="";
        Connector.getDatabase();
        List<AccoutBean> accoutBeen = DataSupport.findAll(AccoutBean.class);
        if (accoutBeen.size()>0){
            ware = accoutBeen.get(0).getWare();
        }
        return  ware;
    }
}
