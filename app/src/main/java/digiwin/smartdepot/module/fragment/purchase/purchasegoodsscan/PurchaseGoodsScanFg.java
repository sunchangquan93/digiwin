package digiwin.smartdepot.module.fragment.purchase.purchasegoodsscan;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepot.core.base.BaseFragment;
import digiwin.smartdepot.core.coreutil.CommonUtils;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.login.bean.AccoutBean;
import digiwin.smartdepot.login.loginlogic.LoginLogic;
import digiwin.smartdepot.module.activity.purchase.purchasegoodsscan.PurchaseGoodsScanSecondActivity;
import digiwin.smartdepot.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepot.module.bean.common.SaveBackBean;
import digiwin.smartdepot.module.bean.common.SaveBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;


/**
 * @author 唐孟宇
 * @des 采购收货扫描 扫码页面
 */
public class PurchaseGoodsScanFg extends BaseFragment {

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
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindViews({R.id.et_scan_barocde, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_number})
    List<TextView> textViews;

    /**
     * 公共区域展示
     */
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;

    @BindView(R.id.includedetail)
    RelativeLayout includeDetail;

    /**
     * 已扫描量
     */
    @BindView(R.id.tv_scaned_num)
    TextView tv_scaned_num;

    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;

    SaveBean saveBean;

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;

    CommonLogic commonLogic;

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_scan_barcode, views);
        ModuleUtils.etChange(activity, et_scan_barocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
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

    @OnClick(R.id.save)
    void save() {
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if (StringUtils.isBlank(et_input_num.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        saveBean.setDoc_no(orderBean.getDoc_no());
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if(null != accoutBean){
            saveBean.setWarehouse_in_no(accoutBean.getWare());
        }
        saveBean.setQty(et_input_num.getText().toString());
        showLoadingDialog();
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                saveBean.setScan_sumqty(saveBackBean.getScan_sumqty());
                clear();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }

    PurchaseGoodsScanSecondActivity pactivity;

    FilterResultOrderBean orderBean = new FilterResultOrderBean();

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
                    barcodeMap.put(AddressContants.DOC_NO,orderBean.getDoc_no());
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            barcodeShow = barcodeBackBean.getShow();
                            if(!StringUtils.isBlank(barcodeBackBean.getBarcode_qty())){
                                et_input_num.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            }
                            tv_scaned_num.setText(barcodeBackBean.getScan_sumqty());
                            barcodeFlag = true;
                            show();
                            saveBean.setQty(barcodeBackBean.getBarcode_qty());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setScan_sumqty(barcodeBackBean.getScan_sumqty());
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            et_input_num.requestFocus();
                            if (CommonUtils.isAutoSave(saveBean)){
                                save();
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
            }
            return false;
        }
    };

    private Handler mHandler=new WeakRefHandler(mCallback);

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_purchase_goods_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PurchaseGoodsScanSecondActivity) activity;
        initData();
    }

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(barcodeShow));
        if(StringUtils.isBlank(tvDetailShow.getText().toString().trim())){
            includeDetail.setVisibility(View.GONE);
        }else{
            includeDetail.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 保存完成之后的操作
     */
    private void clear() {
        tv_scaned_num.setText(saveBean.getScan_sumqty());
        et_scan_barocde.setText("");
        et_scan_barocde.requestFocus();
        et_input_num.setText("");
        barcodeShow = "";
        show();
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        tv_scaned_num.setText("");
        et_scan_barocde.setText("");
        barcodeShow = "";
        show();
        barcodeFlag = false;
        saveBean = new SaveBean();
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        orderBean = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable(AddressContants.ORDERDATA);
        et_scan_barocde.requestFocus();
        delete();
        }

    /**
     * 进入界面先清空后台存的表
     */
    private void delete() {
        Map<String,String> map = new HashMap<>();
        map.put(AddressContants.FLAG, BaseFirstModuldeActivity.ExitMode.EXITD.getName());
        commonLogic.exit(map, new CommonLogic.ExitListener() {
            @Override
            public void onSuccess(String msg) {

            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
