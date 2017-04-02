package digiwin.smartdepot.core.appcontants;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class AddressContants {
    /**
     * 语言常量
     */
    public static String EASY_CHINESE = "简体中文";
    public static String TRADITIONAL_CHINESE = "繁体中文";
    public static String ENGLISH = "English";

    /**
     * 下载好的安装包的名字
     */
    public static String APK_NAME = "updateDiGiWin.apk";
    /**
     *  默认测试区地址
     */
    public static String TEST_ADDRESS = "http://172.16.100.24/web/ws/r/aws_ttsrv2?wsdl";
    /**
     * 测试区地址标记
     */
    public static String TEST_FLAG = "2";
    /**
     *  默认正式区地址
     */
    public static String FORMAL_ADDRESS = "http://172.16.100.24/web/ws/r/aws_ttsrv2?wsdl";
    /**
     * 正式区地址标记
     */
    public static String FORMAL_FLAG = "1";
    /**
     * 上个页面有模块ID传入
     */
    public final  static String MODULEID_INTENT="moduleid_intent";
    /**
     * 分页标记默认值
     */
    public static String PAGE_NUM = "10";
    /**
     * 轮播默认时间
     */
    public static  String REPEATTIME="10";

    public static final String LoginModule="";
    /**
     *扫码延迟时间
     */
    public static final  int DELAYTIME=1;
    /**
     * 物料条码扫描传输xml的field值
     */
    public static final  String BARCODE_NO="barcode_no";
    /**
     * 库位扫描传输xml的field值
     */
    public static final  String STORAGE_SPACES_BARCODE="storage_spaces_barcode";
    /**
     * 删除标记
     */
    public static  final String DELETETPYE="D";
    /**
     * 修改标记
     */
    public static  final String UPDATETPYE="U";
    /**
     * 退出界面传输xml的field值
     */
    public static  final  String FLAG="flag";
    /**
     * 退出界面传输xml的value值，后台判断是否有数据
     */
    public static final  String EXIT0="0";

    /**
     * 退出界面传输xml的value值，执行删除
     */
    public static final  String EXIT1="1";

    /**
     * 退出界面传输xml的value值，前台判断是否需要删除
     */
    public static final  String ISDELETE="1";
    /**
     * ERP的抛转状态
     */
    public static  final  String  N="N";
    /**
     * ERP的抛转状态
     */
    public static  final  String  F="F";

    /**
     * 物料编号
     */
    public static final String ITEM_NO = "item_no";

    /**
     * 仓库代码
     */
    public static final String WAREHOUSE_NO = "warehouse_no";
    /**
     * 收货完成待检验看板中标记合格
     */
    public static  final String Y="Y";

    /**
     * 工单编号
     */
    public static final String WO_NO  = "wo_no";

    /**
     * 送货单号
     */
    public static final String DOC_NO = "doc_no";
    /**
     * 时间
     */
    public static final String DATE = "date";
    /**
     * 供应商
     */
    public static final String SUPPLIER = "supplier";
    /**
     * fifoY
     */
    public static final  String FIFOY="Y";
    /**
     * 物料条码标记1
     */
    public static  final String ONE="1";
    /**
     * 物料条码标记2
     */
    public static  final String TWO="2";
}
