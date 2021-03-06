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
     * 扫码收货
     */
    public final String PURCHASEGOODSSCAN = "A003";
    /**
     * 采购收货
     */
    public final String PURCHASERECEIVE = "A001";
    /**
     * 收货检验
     */
    public final String PURCHASECHECK = "A004";

    /**
     * 扫码入库
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
     * 依原料收货
     */
    public final String MATERIALRECEIVING="A009";
    /**
     * pqc 检验
     */
    public final String PQCCHECK="A060";
    /**
     * pqc CARD
     */
    public final String PQCCARD="A061";
    /**
     * fqc 检验
     */
    public final String FQCCHECK="A062";
    /**
     * fqc CARD
     */
    public final String FQCCARD="A063";
    /**
     * 依成品调拨
     */
    public final String ENDPRODUCTALLOT="B001";
    /**
     * 线边发料
     */
    public final String LINESEND="B002";
    /**
     * 依工单发料
     */
    public final String WORKORDERCODE = "B003";
    /**
     * 直接入库编号--快速入库
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
     * 退料过账
     */
    public final  String MATERIALRETURNING="B010";
    /**
     * 生产超领
     */
    public final String PRODUCTIONLEADER = "B011";
    /**
     * 依成品入库
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
     * 配货复核编号
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
     * 依退料补料
     */
    public final String WORKSUPPLEMENT="B023";

    /**
     * 工序转移
     */
    public final String PROCESSTRANSFER="B025";
    /**
     * 生产报工
     */
    public final String PRODUCEDAILYWORK="B025";
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
     * 调拨过账
     */
    public final String POSTALLOCATE="C002";
    /**
     * 库存查询
     */
    public final String STOREQUERY="C004";
    /**
     * 库存盘点
     */
    public final String STORECHECK="C005";
    /**
     * 杂项收料
     */
    public final String MISCELLANEOUSISSUESIN="C008";
    /**
     * 杂项发料
     */
    public final String MISCELLANEOUSISSUESOUT="C009";
    /**
     * 调拨，无来源
     */
    public final String NOCOMESTOREALLOT ="C010";
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
     * 有源杂收
     */
    public final String MISCELLANEOUSACTIVE_IN="C019";
    /**
     * 有源杂发
     */
    public final String MISCELLANEOUSACTIVE_OUT="C020";
    /**
     * 通知出货
     */
    public final String SALEOUTLET="D001";
    /**
     * 门店要货
     */
    public final String ORDERSALE = "D002";
    /**
     * 出货过账
     */
    public final String PICKUPSHIPMENT = "D003";

    /**
     * 销售退货
     */
    public final String SALERETURN="D004";

    /**
     * 扫箱码出货
     */
    public final String SCANOUTSTORE="D005";
    /**
     * 成品质量追溯
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
