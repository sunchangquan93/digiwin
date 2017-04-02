package digiwin.smartdepot.core.net;

import android.content.Context;

import digiwin.library.net.IRequestManager;
import digiwin.library.net.RequestFactory;
import digiwin.smartdepot.core.appcontants.URLPath;

/**
 * @des    Json网络请求
 * @author  xiemeng
 * @date    2017/3/29
 */
public class OkhttpRequestJson {

    private IRequestManager RequestManager;
    private static OkhttpRequestJson instance;
    /**
     * webService地址
     */
    private String url;
    public static OkhttpRequestJson getInstance(Context context){
        return instance=new OkhttpRequestJson(context);
    }

    private OkhttpRequestJson(Context context) {
         RequestManager= RequestFactory.getRequestManager(context);
         url= URLPath.MAINURL;
    }

    public void get(String url,IRequestCallbackImp requestCallBack){
        RequestManager.get(url,requestCallBack);
    }

    public void post(String urlPath,String requestBody, IRequestCallbackImp requestCallBack){
        RequestManager.post(url+urlPath,"token",requestBody,requestCallBack);
    }

    public void downLoad(String downLoadUrl,String filePath,String apkName,IDownLoadCallBackImp downLoadCallBack){
        RequestManager.downLoadFile(downLoadUrl,filePath,apkName,downLoadCallBack);
    }

}
