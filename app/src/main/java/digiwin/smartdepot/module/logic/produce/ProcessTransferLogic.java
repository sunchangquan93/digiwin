package digiwin.smartdepot.module.logic.produce;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.ReqTypeName;
import digiwin.smartdepot.core.net.IRequestCallbackImp;
import digiwin.smartdepot.core.net.OkhttpRequest;
import digiwin.smartdepot.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepot.module.bean.produce.ProcessTransferBean;

/**
 * @des     工序转移
 * @author  maoheng
 * @date    2017/06/01
 */

public class ProcessTransferLogic {
    private static final String TAG = "ProcessTransferLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private  String mModule="";
    /**
     * 设备号+模组+时间
     */
    private  String mTimestamp="";

    private static ProcessTransferLogic logic;

    private ProcessTransferLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule=module;
        mTimestamp=timestamp;

    }
    /**
     * 获取单例
     */
    public static ProcessTransferLogic getInstance(Context context, String module, String timestamp) {
        if (null == logic) {
            logic = new ProcessTransferLogic(context,module,timestamp);
        }
        return logic;
    }

    /**
     * 扫描流转卡号
     */
    public interface ScanRunCardListener {

        public void onSuccess(ProcessTransferBean scanBackBean);

        public void onFailed(String error);
    }

    /**
     * 扫描流转卡号
     */
    public void scanRunCardCode(final Map<String, String> map, final ScanRunCardListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.PROCESSCARD, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.PROCESSCARD, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ProcessTransferBean> backBeen = xmlResp.getParameterDatas(ProcessTransferBean.class);
                                    if (backBeen.size() > 0) {
                                        listener.onSuccess(backBeen.get(0));
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
                    LogUtils.e(TAG, "scanLocator--->" + e);
                }
            }
        }, null);
    }

}
