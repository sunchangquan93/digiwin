package digiwin.smartdepot.module.activity.purchase.purchaseinstore;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseTitleActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.module.bean.common.ListSumBean;
import digiwin.smartdepot.module.bean.common.SaveBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

/**
 * 采购入库 扫描页面
 * @author 唐孟宇
 */
public class PurchaseInStorageScanActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

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
    @BindView(R.id.tv_locator)
    TextView tv_locator;
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

    @BindViews({R.id.et_scan_barocde, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;

    /**
     * 公共区域展示
     */
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;

    @BindView(R.id.includedetail)
    RelativeLayout includeDetail;
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

    CommonLogic commonLogic;

    PurchaseInStorageScanActivity pactivity;

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
        ModuleUtils.tvChange(activity, tv_locator, textViews);
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
                            barcodeShow = barcodeBackBean.getShow();
                            if(!StringUtils.isBlank(barcodeBackBean.getBarcode_qty())){
                                et_input_num.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            }
                            barcodeFlag = true;
                             show();
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
                            locatorShow = locatorBackBean.getShow();
                            locatorFlag = true;
                            show();
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
        module = ModuleCode.PURCHASEINSTORE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_purchase_in_storage_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PurchaseInStorageScanActivity) activity;
        commonLogic = CommonLogic.getInstance(pactivity, module, mTimestamp.toString());
        initData();
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.purchase_in_store);
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
        saveBean.setQty(et_input_num.getText().toString());
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

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(barcodeShow + "\\n" + locatorShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())){
            includeDetail.setVisibility(View.VISIBLE);}else {
            includeDetail.setVisibility(View.GONE);
        }
    }


    /**
     * 保存完成之后的操作
     */
    private void clear() {
        //如果条码类型为1，不清空扫码框
        if(!sumshoubean.getItem_barcode_type().equals("1")){
            et_scan_barocde.setText("");
            et_scan_barocde.requestFocus();
            if (cb_locatorlock.isChecked()){
                barcodeFlag = false;
            }else {
                locatorShow = "";
                et_scan_locator.setText("");
                et_scan_locator.requestFocus();
            }
        }
        et_input_num.setText("");
        if (cb_locatorlock.isChecked()){
            barcodeFlag = false;
        }else {
            locatorShow = "";
            et_scan_locator.setText("");
            et_scan_barocde.requestFocus();
        }
        barcodeShow = "";
        show();
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
        saveBean.setDoc_no(sumshoubean.getReceipt_no());
        saveBean.setAvailable_in_qty(sumshoubean.getShortage_qty());
        //物料条码类型为1
        if(sumshoubean.getItem_barcode_type().equals("1")){
            et_scan_barocde.setText(sumshoubean.getItem_no());
        }else{
            et_scan_barocde.requestFocus();
        }
    }
}