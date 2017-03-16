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
    public final String OTHER="";
    /**
     * 生产退料
     */
    public final  String MATERIALRETURNING="B010";
    /**
     * 生产完工入库上架编号
     */
    public final  String FINISHEDSTORAGE="B012";

    /**
     * 生产配货上架编号
     */
    public final  String DISTRIBUTE="B013";
    /**
     * 直接入库编号
     */
    public final  String DIRECTSTORAGE="B005";

    /**
     * 调拨复核编号
     */
    public final String TRANSFERS_TO_REVIEW="B015";

    /**
     * 依成品发料
     */
    public final String ACCORDINGMATERIALCODE="B014";
    /**
     * 领料过账
     */
    public final String POSTMATERIAL ="B016";
    /**
     * 收货完成待检验看板
     */
    public final String RCCTBOARD ="F001";
    /**
     * 检验完成待入库看板
     */
    public final String TCTSBOARD="F002";

    /**
     * 调拨，无来源
     */
    public final String NOCOMESTOREALLOT ="C010";

    /**
     * 采购入库
     */
    public final String PURCHASEINSTORE="A005";
    /**
     * 采购收货 快速
     */
    public final String MATERIALRECEIPTCODE = "A006";

    /**
     * 依工单发料
     */
    public final String WORKORDERCODE = "B003";
    /**
     * 报工
     */
    public final String ORDERDAILYWORK="E001";

    /**
     *  入库上架
     */
    public final String PUTINSTORE = "B007";

    /**
     * 完工入库
     */
    public final String COMPLETINGSTORE = "B006";
    /**
     * 销售出库
     */
    public final String SALEOUTLET="D001";

    /**
     * 工序报工
     */
    public final String PROCESSREPORTING = "E005";

}
