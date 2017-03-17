package digiwin.smartdepot.module.logic.board;

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
import digiwin.smartdepot.module.bean.board.RcctboardBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;

/**
 * @author xiemeng
 * @des 收货完成待检验看板
 * @date 2017/3/8
 */
public class RcctboardLogic {
    private static final String TAG = "RcctboardLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static RcctboardLogic logic;

    private RcctboardLogic(Context context, String module, String timestamp) {
        mContext = context;
        mModule = module;
        mTimestamp = timestamp;

    }

    public static RcctboardLogic getInstance(Context context, String module, String timestamp) {

        return logic = new RcctboardLogic(context, module, timestamp);
    }

    /**
     * 获取看板资料
     */
    public interface GetRcctBoardListener {
        public void onSuccess(List<RcctboardBean> list,String msg);

        public void onFailed(String error);
    }

    /**
     * 获取看板资料
     */
    public void getRcctBoard(final Map<String, String> map, final GetRcctBoardListener listener) {
        try {
            String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.RCCTBOARD, mTimestamp).toXml();
            OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.RCCTBOARD, string);
                    String error = mContext.getString(R.string.unknow_error);
                    if (null != xmlResp) {
                        if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                            String fieldString = xmlResp.getFieldString();
                            List<RcctboardBean> rcctboardBeen = xmlResp.getMasterDatas(RcctboardBean.class);
                            listener.onSuccess(rcctboardBeen,fieldString);
                            return;
                        } else {
                            error = xmlResp.getDescription();
                        }
                    }
                    listener.onFailed(error);
                }
            });
        } catch (Exception e) {
            listener.onFailed(mContext.getString(R.string.unknow_error));
            LogUtils.e(TAG, "getTctsBoard" + e);

        }


    }

}
