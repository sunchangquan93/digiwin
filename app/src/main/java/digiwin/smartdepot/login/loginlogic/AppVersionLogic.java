package digiwin.smartdepot.login.loginlogic;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ReqTypeName;
import digiwin.smartdepot.core.net.IRequestCallbackImp;
import digiwin.smartdepot.core.net.OkhttpRequest;
import digiwin.smartdepot.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepot.login.bean.AppVersionBean;

/**
 * @author xiemeng
 * @des 版本更新
 * @date 2017/1/17
 */
public class AppVersionLogic {
    private static final String TAG = "AppVersionLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private  String mModule="";
    /**
     * 设备号+模组+时间
     */
    private  String mTimestamp="";

    private static AppVersionLogic logic;

    private AppVersionLogic(Context context,String module,String timestamp) {
        mContext = context.getApplicationContext();
        mModule=module;
        mTimestamp=timestamp;

    }

    /**
     * 获取单例
     */
    public static AppVersionLogic getInstance(Context context,String module,String timestamp) {
//        if (null == logic) {
//
//        }
        return logic = new AppVersionLogic(context,module,timestamp);
    }

    /**
     * 获取服务器版本信息
     */
    public interface GetNewVersionListener {
        public void onSuccess(AppVersionBean bean);

        public void onFailed(String msg);
    }

    /**
     * 获取服务器版本信息
     */
    public void getNewVersion(Map<String, String> map, final GetNewVersionListener listener) {
        try {
            String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETVERUP,mTimestamp).toXml();
            OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    String error =mContext.getString(R.string.unknow_error);
                    ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETVERUP, string);
                    if (null != xmlResp) {
                        if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                            List<AppVersionBean> versionBeans = xmlResp.getParameterDatas(AppVersionBean.class);
                            if (versionBeans.size() > 0) {
                                listener.onSuccess(versionBeans.get(0));
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
            LogUtils.e(TAG, "getNewVersion异常");
            listener.onFailed(mContext.getString(R.string.unknow_error));
        }
    }
}
