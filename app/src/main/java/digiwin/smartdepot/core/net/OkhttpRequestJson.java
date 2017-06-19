package digiwin.smartdepot.core.net;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import digiwin.library.net.IRequestManager;
import digiwin.library.net.IUpdateCallBack;
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
         RequestManager= RequestFactory.getRequestJsonManager(context);
         url= URLPath.MAINURL;
    }

    public void get(String url,IRequestCallbackImp requestCallBack){
        RequestManager.get(url,requestCallBack);
    }


    public void post(String urlPath, Map<String,String> postMap, IRequestCallbackImp requestCallBack){
        if (null==postMap){
            postMap=new HashMap<>();
        }
        postMap.put("token","token");
        RequestManager.post(url+urlPath,postMap,requestCallBack);
    }

    public void post(String urlPath,String requestBody, IRequestCallbackImp requestCallBack){
        RequestManager.post(url+urlPath,requestBody,requestCallBack);
    }


    public void downLoad(String downLoadUrl,String filePath,String apkName,IDownLoadCallBackImp downLoadCallBack){
        RequestManager.downLoadFile(downLoadUrl,filePath,apkName,downLoadCallBack);
    }

    public void updateFile(String urlPath, Map<String,Object> postMap, IUpdateCallBack requestCallBack){
        if (null==postMap){
            postMap=new HashMap<>();
        }
        RequestManager.updateFile(urlPath,postMap,requestCallBack);
    }

}
