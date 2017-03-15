package digiwin.smartdepot.module.activity.produce.postmaterial;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.adapter.produce.AccordingMaterialFiFoAdapter;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SaveBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepot.module.bean.produce.FiFoBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * 领料过账 扫描页面
 * @author 唐孟宇
 */
public class PostMaterialScanActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * 品名
     */
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    /**
     * 单位
     */
    @BindView(R.id.tv_product_danwei)
    TextView tv_product_danwei;
    /**
     * 规格
     */
    @BindView(R.id.tv_item_model)
    TextView tv_item_model;
//    /**
//     * 库存
//     */
//    @BindView(R.id.tv_locator_num)
//    TextView tv_locator_num;
//    /**
//     * 欠料
//     */
//    @BindView(R.id.tv_left_material_num)
//    TextView tv_left_material_num;
//    /**
//     * 已扫
//     */
//    @BindView(R.id.tv_scanned_num)
//    TextView tv_scanned_num;

    /**
     * 条码
     */
    @BindView(R.id.tv_barcode)
    TextView tvBarcode;

    /**
     * 条码
     */
    @BindView(R.id.et_scan_barocde)
    EditText et_scan_barocde;

    /**
     * 库位
     */
    @BindView(R.id.tv_locator_string)
    TextView tv_locator_string;
    /**
     * 库位
     */
    @BindView(R.id.et_scan_locator)
    EditText et_scan_locator;
    /**
     * 锁定库位
     */
    @BindView(R.id.cb_locatorlock)
    CheckBox cb_locatorlock;
    /**
     * 数量
     */
    @BindView(R.id.et_input_num)
    EditText et_input_num;

    /**
     * 保存按钮
     */
    @BindView( R.id.save)
    Button save;

    @BindView(R.id.ll_scan_barcode)
    LinearLayout ll_scan_barcode;
    @BindView(R.id.ll_scan_locator)
    LinearLayout ll_scan_locator;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;

    @BindView(R.id.iv_title_setting)
    ImageView iv_title_setting;

    @BindViews({R.id.et_scan_barocde, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_locator_string, R.id.tv_number})
    List<TextView> textViews;

    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 库位展示
     */
    String locatorShow;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;

    SaveBean saveBean;
    /**
     * 从汇总界面带入的单身数据
     */
    ListSumBean sumshoubean;
    /**
     * 从汇总界面带入的单头数据
     */
    FilterResultOrderBean orderData;

    AccordingMaterialFiFoAdapter adapter;

    List<FiFoBean> fiFoList = new ArrayList<FiFoBean>();

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    CommonLogic commonLogic;

    PostMaterialScanActivity pactivity;

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            et_scan_locator.setKeyListener(null);
        } else {
            et_scan_locator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_scan_barcode, views);
        ModuleUtils.etChange(activity, et_scan_barocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_scan_locator)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(ll_scan_locator, views);
        ModuleUtils.etChange(activity, et_scan_locator, editTexts);
        ModuleUtils.tvChange(activity, tv_locator_string, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, et_input_num, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = R.id.et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(LOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            //管控建议
                            if(sumshoubean.getFifo_check().equals("Y")){
                                if(null != fiFoList && fiFoList.size()>0){
                                    int n = 0;
                                    for (int i = 0;i<fiFoList.size();i++){
                                        if(barcodeBackBean.getBarcode_no().equals(fiFoList.get(i).getBarcode_no())){
                                            n++;
                                        }
                                    }
                                    if(n ==0){
                                        showFailedDialog(pactivity.getResources().getString(R.string.barcode_not_in_fifo));
                                        return;
                                    }
                                }
                            }
                            barcodeShow = barcodeBackBean.getShow();
                            if(!StringUtils.isBlank(barcodeBackBean.getBarcode_qty())){
                                et_input_num.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            }
                            barcodeFlag = true;
//                          show();
                            saveBean.setQty(barcodeBackBean.getBarcode_qty());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setScan_sumqty(barcodeBackBean.getScan_sumqty());
                            if (cb_locatorlock.isChecked()){
                                et_input_num.requestFocus();
                            }else {
                                et_scan_locator.requestFocus();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            barcodeFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_scan_barocde.setText("");
                                }
                            });
                        }
                    });
                    break;
                case LOCATORWHAT:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            //管控建议
                            if(null != fiFoList && fiFoList.size()>0){
                                int n = 0;
                                for (int i = 0;i<fiFoList.size();i++){
                                    if(locatorBackBean.getStorage_spaces_no().equals(fiFoList.get(i).getStorage_spaces_no())){
                                        n++;
                                    }
                                }
                                if(n ==0){
                                    showFailedDialog(pactivity.getResources().getString(R.string.locator_not_in_fifo));
                                    return;
                                }
                            }
                            locatorShow = locatorBackBean.getShow();
                            locatorFlag = true;
//                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            et_input_num.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_scan_locator.setText("");
                                }
                            });
                            locatorFlag = false;
                        }
                    });
                    break;
            }
            return false;
        }
    });

    private void getFIFO(ListSumBean sumshoubean ){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("item_no",sumshoubean.getItem_no());
        map.put("lot_no","");
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        String ware = "";
        if(null != accoutBean){
            ware = accoutBean.getWare();
        }
        map.put("warehouse_no",ware);
        //欠料量
        float num1 = StringUtils.string2Float(sumshoubean.getShortage_qty());
        //库存量
        float num2 = StringUtils.string2Float(sumshoubean.getStock_qty());
        float num = (num1 - num2)>0 ? num2 : num1;
        num -= StringUtils.string2Float(sumshoubean.getScan_sumqty());
        map.put("qty",String.valueOf(num));
        commonLogic.getFifo(map, new CommonLogic.FIFOGETListener() {
            @Override
            public void onSuccess(List<FiFoBean> fiFoBeanList ) {
                if (null != fiFoBeanList && fiFoBeanList.size()>0){
                    fiFoList.clear();
                    fiFoList = fiFoBeanList;
                    adapter = new AccordingMaterialFiFoAdapter(pactivity,fiFoList);
                    ryList.setAdapter(adapter);
                }
            }

            @Override
            public void onFailed(String error) {
                showFailedDialog(error);
            }
        });
    }

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.POSTMATERIAL;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_post_material_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PostMaterialScanActivity) activity;
        commonLogic = CommonLogic.getInstance(pactivity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.post_material);
    }

    @OnClick(R.id.save)
    void Save() {
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if (!locatorFlag) {
            showFailedDialog(R.string.scan_locator);
            return;
        }
        if (StringUtils.isBlank(et_input_num.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        showLoadingDialog();
        saveBean.setScan_sumqty(et_input_num.getText().toString().trim());
        saveBean.setQty(et_input_num.getText().toString().trim());
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                clear();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }
//
//    /**
//     * 公共区域展示
//     */
//    private void show() {
//        tvDetailShow.setText(StringUtils.lineChange(barcodeShow + "\\n" + locatorShow));
//        includeDetail.setVisibility(View.VISIBLE);
//    }


    /**
     * 保存完成之后的操作
     */
    private void clear() {

        //保存成功已扫描改变数量
        float num = StringUtils.string2Float(saveBean.getScan_sumqty());
        num += StringUtils.string2Float(et_input_num.getText().toString());
        //更新FIFO建议 实发量
        if(null != fiFoList && fiFoList.size()>0){
            for (int i = 0;i<fiFoList.size();i++){
                //扫描的条码与FIFO中的条码一致
                if(saveBean.getBarcode_no().equals(fiFoList.get(i).getBarcode_no())
                        && saveBean.getStorage_spaces_in_no().equals(fiFoList.get(i).getStorage_spaces_no())){
                    fiFoList.get(i).setScan_sumqty(StringUtils.deleteZero(String.valueOf(num)));
                }
            }
            adapter.notifyDataSetChanged();
        }
        //如果条码类型为1，不清空扫码框
        if(!sumshoubean.getItem_barcode_type().equals("1")){
            et_scan_barocde.setText("");
            et_scan_barocde.requestFocus();
            if (cb_locatorlock.isChecked()){
                barcodeFlag = false;
            }else {
                et_scan_locator.setText("");
                et_scan_locator.requestFocus();
            }
        }
        et_input_num.setText("");
        if (cb_locatorlock.isChecked()){
            barcodeFlag = false;
        }else {
            et_scan_locator.setText("");
        }

    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        et_scan_barocde.setText("");
        et_scan_locator.setText("");
        et_scan_locator.requestFocus();
        barcodeShow = "";
        locatorShow = "";
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        sumshoubean = (ListSumBean) getIntent().getExtras().getSerializable("orderSumData");
        orderData = (FilterResultOrderBean) getIntent().getExtras().getSerializable("orderData");
        saveBean.setDoc_no(orderData.getDoc_no());
        tv_product_name.setText(sumshoubean.getItem_name());
        tv_item_model.setText(sumshoubean.getItem_spec());
        //需求量
        float num1 = StringUtils.string2Float(sumshoubean.getShortage_qty());
        //库存量
        float num2 = StringUtils.string2Float(sumshoubean.getStock_qty());
        if(num1<=num2){
            saveBean.setAvailable_in_qty(sumshoubean.getShortage_qty());
        }else{
            saveBean.setAvailable_in_qty(sumshoubean.getStock_qty());
        }
        //物料条码类型为1
        if(sumshoubean.getItem_barcode_type().equals("1")){
            et_scan_barocde.setText(sumshoubean.getItem_no());
        }else{
            et_scan_barocde.requestFocus();
        }
        //获取FIFO
        getFIFO(sumshoubean);
    }

}
