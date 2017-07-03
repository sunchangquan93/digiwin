package digiwin.smartdepot.module.logic.purchase;

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
import digiwin.smartdepot.module.bean.purchase.PQCValueBean;

/**
 * @author 毛衡
 * @module PQC检验
 * @date 2017/4/30
 */

public class QCLogic {

    private static final String TAG = "QCLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static QCLogic logic;

    private QCLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;
    }

    public static QCLogic getInstance(Context context, String module, String timestamp) {

        return logic = new QCLogic(context, module, timestamp);
    }
    /**
     * 获取PQC测量值
     */
    public interface getPQCValueListener {

        public void onSuccess(PQCValueBean data);

        public void onFailed(String error);
    }

    public void getPQCValueData(final Map<String,String> map, final getPQCValueListener listener){

        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.PQCCHECKVALUE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.PQCCHECKVALUE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<PQCValueBean> list = xmlResp.getMasterDatas(PQCValueBean.class);
                                    if (list.size() > 0) {
                                        listener.onSuccess(list.get(0));
                                        return;
                                    } else {
                                        error = mContext.getString(R.string.data_null);
                                    }
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
                }
            }
        }, null);
    }

    /**
     * PQC提交
     */
    public interface postPQCListener {

        public void onSuccess(String msg);

        public void onFailed(String error);
    }
    public void postPQCData(final List<Map<String, String>> maps, final List<List<Map<String, String>>> details, final postPQCListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(mModule, ReqTypeName.POSTPQC,mTimestamp,maps,details,null).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.POSTPQC, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    String msg = xmlResp.getFieldString();
                                    listener.onSuccess(msg);
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
                    LogUtils.e(TAG, "scanLocator--->" + e);
                }
            }
        }, null);
    }

    /**
     * FQC提交
     */
    public interface postFQCListener {

        public void onSuccess(String msg);

        public void onFailed(String error);
    }
    public void postFQCData(final List<Map<String, String>> maps, final List<List<Map<String, String>>> details, final postFQCListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(mModule, ReqTypeName.POSTFQC,mTimestamp,maps,details,null).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.POSTFQC, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    String msg = xmlResp.getDescription();
                                    listener.onSuccess(msg);
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
                    LogUtils.e(TAG, "scanLocator--->" + e);
                }
            }
        }, null);
    }

    /**
     * 检测值保存
     */
    public interface SavePQCListener {

        public void onSuccess(String msg);

        public void onFailed(String error);
    }
    public void savePQCData(final List<Map<String, String>> maps, final List<Map<String, String>> details, final SavePQCListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(mModule, ReqTypeName.SAVEPQC,mTimestamp,maps,details).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.SAVEPQC, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    String msg = xmlResp.getFieldString();
                                    listener.onSuccess(msg);
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
                    LogUtils.e(TAG, "scanLocator--->" + e);
                }
            }
        }, null);
    }
}
