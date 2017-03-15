package digiwin.library.net;

/**
 * Created by ChangQuan.Sun on 2016/12/23
 * 请求接口父类接口,目前只添加get，post方法，后续有需要可以添加
 */

public interface IRequestManager {

    void get(String url,IRequestCallBack requestCallBack);

    void post(String url,String requestBodyXml,IRequestCallBack requestCallBack);
    /**
     * 文件下载
     */
    void downLoadFile(String url,String filepath,String apkName,IDownLoadCallBack callBack);

}
