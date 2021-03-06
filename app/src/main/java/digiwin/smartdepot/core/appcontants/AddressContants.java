package digiwin.smartdepot.core.appcontants;

import digiwin.library.utils.TelephonyUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.base.BaseApplication;

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
    public static String APK_NAME = BaseApplication.getInstance().getString(R.string.app_name)+ TelephonyUtils.getMAppVersion(BaseApplication.getInstance())+".apk";
    /**
     *  默认测试区地址
     */
    public static String TEST_ADDRESS = "http://180.167.0.43:8089/web/ws/r/aws_ttsrv2?wsdl";
//    public static String TEST_ADDRESS = "http://192.168.0.80/web/ws/r/aws_ttsrv2?WSDL";
    /**
     * 测试区地址标记
     */
    public static String TEST_FLAG = "2";
    /**
     *  默认正式区地址http://172.16.100.24/web/ws/r/aws_ttsrv2?wsdl
     */
    public static String FORMAL_ADDRESS = "http://180.167.0.43:8089/web/ws/r/aws_ttsrv2?wsdl";
//    public static String FORMAL_ADDRESS = "http://192.168.0.80/web/ws/r/aws_ttsrv2?WSDL";
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
     * 装箱单号
     */
    public static final  String PACKAGE_NO="package_no";
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
     * 删除
     */
    public static  final  String DELETE="d";
    /**
     * 新增
     */
    public static  final  String INSTER="i";
    /**
     * 流转卡号
     */
    public static  final  String PROCESSCARD="process_card";
    /**
     * 工序报工
     */
    public static  final  String PROCESSNO="process_no";
    /**
     * 时间
     */
    public static  final  String REPOSTDATETIME="report_datetime";
    /**
     * 线别
     */
    public static  final  String LINENO="line_no";
    /**
     * 工序
     */
    public static  final  String SUBOP_NO="subop_no";
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
     * 出货单
     */
    public static final String RECEIPT_NO = "receipt_no";

    /**
     * 仓库代码
     */
    public static final String WAREHOUSE_NO = "warehouse_no";
    /**
     * 储位代码
     */
    public static final String WAREHOUSE_STORAGE = "warehouse_storage";
    /**
     * 批次
     */
    public static final String ITEMLOTNO = "item_lot_no";
    /**
     * 锁定原因
     */
    public static final String LOCKREASON = "lock_reason";
    /**
     * 人员编号
     */
    public static final String EMPLOYEENO = "employee_no";
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
     * 客户
     */
    public static final String CUSTOM = "custom";
    /**
     * fifoY
     */
    public static final  String FIFOY="Y";
    /**
     * fifoY
     */
    public static final  String FIFON="N";
    /**
     * 物料条码标记1
     */
    public static  final String ONE="1";
    /**
     * 物料条码标记2
     */
    public static  final String TWO="2";
    /**
     *包装箱号
     */
    public static  final String PACKAGENO="package_no";
    /**
     *出货单号
     */
    public static  final String RECEIPTNO="receipt_no";
    /**
     *时间
     */
    public static  final String RECEIPTDATE="receipt_date";
    /**
     * 部分模块使用的单号key值
     */
    public static  final String ISSUING_NO="issuing_no";
    /**
     * 用户名
     */
    public static final String USERNAME = "username";
    /**
     * 密码
     */
    public static final String PASSWORD = "password";
    /**
     * 营运中心
     */
    public static final String PLANT = "plant";
    /**
     * 部门编号
     */
    public static final String DEPARTMENTNO = "department_no";
    /**
     * orderData
     */
    public static final String ORDERDATA = "orderData";
    /**
     * 出仓库
     */
    public static final String WAREHOUSEOUTNO = "warehouse_out_no";
    /**
     * 工单号
     */
    public static final String WORKNO = "work_no";
    /**
     * qty
     */
    public static final String QTY = "qty";

    /**
     * 理由码
     */
    public static final String REASONCODENO = "reason_code_no";

    /**
     * 分页数
     */
    public static final String PAGESIZE = "pagesize";
    /**
     * 项次
     */
    public static final String SEQ = "seq";

    public static final String STORAGE_SPACES_NO="storage_spaces_no";
    /**
     * 流转卡号
     */
    public static final String PROCESSCARDNO = "processcard_no";
}
