package digiwin.smartdepot.core.appcontants;

/**
 * @des      模块编号
 * @author  xiemeng
 * @date    2017/2/22
 */
public interface ModuleCode {
    /**
     * 非具体模块统一使用编号
     */
    public final String OTHER="OTHER";

    /**
     * 采购收货扫描
     */
    public final String PURCHASEGOODSSCAN = "A003";
    /**
     * 收货检验
     */
    public final String PURCHASECHECK = "A004";

    /**
     * 采购入库
     */
    public final String PURCHASEINSTORE="A005";
    /**
     * 采购收货 快速
     */
    public final String MATERIALRECEIPTCODE = "A006";
    /**
     * 快速入库
     */
    public final String QUICKSTORAGE = "A007";
    /**
     * 仓库退料
     */
    public final String STORERETURNMATERIAL="A008";

    /**
     * 依工单发料
     */
    public final String WORKORDERCODE = "B003";
    /**
     * 直接入库编号
     */
    public final  String DIRECTSTORAGE="B005";
    /**
     * 完工入库
     */
    public final String COMPLETINGSTORE = "B006";

    /**
     *  入库上架
     */
    public final String PUTINSTORE = "B007";

    /**
     * 生产退料
     */
    public final  String MATERIALRETURNING="B010";
    /**
     * 生产超领
     */
    public final String PRODUCTIONLEADER = "B011";
    /**
     * 生产完工入库上架编号
     */
    public final  String FINISHEDSTORAGE="B012";

    /**
     * 生产配货上架编号
     */
    public final  String DISTRIBUTE="B013";

    /**
     * 依成品发料
     */
    public final String ACCORDINGMATERIALCODE="B014";


    /**
     * 调拨复核编号
     */
    public final String TRANSFERS_TO_REVIEW="B015";

    /**
     * 领料过账
     */
    public final String POSTMATERIAL ="B016";
    /**
     * 依工单退料
     */
    public final String WORKORDERRETURN="B022";


    /**
     * 依工单补料
     */
    public final String WORKSUPPLEMENT="B023";

    /**
     *装箱打印
     */
    public final String ENCHASEPRINT="B027";
    /**
     *装箱入库
     */
    public final String INBINNING="B028";
    /**
     * 标签补打
     */
    public final String PRINTLABEL="C001";
    /**
     * 库存查询
     */
    public final String STOREQUERY="C004";
    /**
     * 调拨，无来源
     */
    public final String NOCOMESTOREALLOT ="C010";

    /**
     * 杂项发料
     */
    public final String MISCELLANEOUSISSUESOUT="C009";
    /**
     * 杂项收料
     */
    public final String MISCELLANEOUSISSUESIN="C008";

    /**
     * 条码移库
     */
    public final String MOVESTORE="C011";

    /**
     * 库存交易锁定
     */
    public final String STORETRANSLOCK="C012";
    /**
     * 库存交易解锁
     */
    public final String STORETRANSUNLOCK="C015";
    /**
     * 产品装箱
     */
    public final String PRODUCTBINNING="C016";
    /**
     * 产品出箱
     */
    public final String PRODUCTOUTBOX="C017";
    /**
     * 通知出货
     */
    public final String SALEOUTLET="D001";

    /**
     * 捡料出货
     */
    public final String PICKUPSHIPMENT = "D003";

    /**
     * 销售退货
     */
    public final String SALERETURN="D004";

    /**
     * 扫码出货
     */
    public final String SCANOUTSTORE="D005";
    /**
     * 产品质量追溯
     */
    public final String TRANSPRODUCTQUALITY="D006";

    /**
     * 报工
     */
    public final String ORDERDAILYWORK="E001";

    /**
     * 扫入扫描
     */
    public final String SCANINSCAN = "E001";
    /**
     * 开工扫描
     */
    public final String STARTWORKSCAN = "E002";
    /**
     * 完工扫描
     */
    public final String FINISHWORKSCAN = "E003";
    /**
     * 扫出扫描
     */
    public final String SCANOUTSCAN = "E004";


    /**
     * 订单出货
     */
    public final String ORDERSALE = "D007";


    /**
     * 工序报工
     */
    public final String PROCESSREPORTING = "E005";
    /**
     * 收货完成待检验看板
     */
    public final String RCCTBOARD ="F001";
    /**
     * 检验完成待入库看板
     */
    public final String TCTSBOARD="F002";

}
