package digiwin.smartdepot.core.appcontants;

/**
 * @des    JSON请求时模块路径
 * @author  xiemeng
 * @date    2017/3/31
 */
public interface URLPath {

    /**
     * 参数key
     */
    static  final  String PARAMS="inParam";
    /**180.167.0.42:9018
     * 主URL   http://binglee.wicp.net/api/act
     */
    static final String MAINURL="http://180.167.0.42:9018/api/act";

    /**
     * 图片http://binglee.wicp.net/api/act
     */
    static final String FILEURL="http://180.167.0.42:9018/api/res/v1/file/upload";
    /**
     * 待检验看板
     */
    static  final String RCCTBOARD ="/apm/v1/getListToCheckData";
    /**
     * 检验完成待入库看板
     */
    static  final String TCTSBOARD ="/apm/v1/getListToInData";
    /**
     * 收货检验图片访问url
     */
    static  final String CHECKPIC ="/apm/v1/getListToInData";
}
