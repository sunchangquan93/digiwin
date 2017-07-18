package digiwin.smartdepot.module.activity.purchase.materialreceiving;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepot.R;
import digiwin.smartdepot.core.appcontants.AddressContants;
import digiwin.smartdepot.core.appcontants.ModuleCode;
import digiwin.smartdepot.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepot.core.modulecommon.ModuleUtils;
import digiwin.smartdepot.module.bean.common.SaveBean;
import digiwin.smartdepot.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepot.module.bean.purchase.ScanSupplierBean;
import digiwin.smartdepot.module.logic.common.CommonLogic;

import static digiwin.library.utils.StringUtils.string2Float;

/**
 * 孙长权
 * 依源料收货
 * 2017/7/7
 */

public class MaterialReceivingActivity extends BaseFirstModuldeActivity {

    private MaterialReceivingActivity activity;
    /**
     * 原料条码
     */
    private final int BARCODEWHAT = 1001;
    /**
     * 供应商
     */
    private final int SUPPLERWHAT = 1002;

    private final String STOCKINQTY = "stock_in_qty";

    CommonLogic commonLogic;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    /**
     * 原料条码
     */
    @BindView(R.id.tv_material_order)
    TextView tv_material_order;
    @BindView(R.id.et_material_order)
    EditText et_material_order;
    @BindView(R.id.ll_material_order)
    LinearLayout ll_material_order;
    /**
     * 供应商
     */
    @BindView(R.id.tv_supplier)
    TextView tv_supplier;
    @BindView(R.id.et_supplier)
    EditText et_supplier;
    @BindView(R.id.ll_supplier)
    LinearLayout ll_supplier;
    /**
     * 数量
     */
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.et_input_num)
    EditText et_input_num;
    @BindView(R.id.ll_input_num)
    LinearLayout ll_input_num;

    @BindViews({R.id.et_material_order,R.id.et_supplier, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_material_order,R.id.ll_supplier, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_material_order,R.id.tv_supplier, R.id.tv_number})
    List<TextView> textViews;

    @BindView(R.id.commit)
    Button btn_Commit;

    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.includedetail)
    View includeDetail;

    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 供应商展示
     */
    String supplierShow;

    /**
     * 用于对比的数量
     */
    float available_in_qty;

    private SaveBean saveBean;

    @OnClick(R.id.commit)
    void commit() {
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                if (StringUtils.isBlank(et_material_order.getText().toString().trim())) {
                    dismissLoadingDialog();
                    showFailedDialog(R.string.please_scan_material_number);
                    return;
                }
                if (StringUtils.isBlank(et_supplier.getText().toString().trim())) {
                    dismissLoadingDialog();
                    showFailedDialog(R.string.please_scan_supplier);
                    return;
                }

                if (StringUtils.isBlank(et_input_num.getText().toString().trim())) {
                    dismissLoadingDialog();
                    showFailedDialog(R.string.input_num);
                    return;
                }
                showLoadingDialog();
                Map<String, String> map = ObjectAndMapUtils.getValueMap(saveBean);
                commonLogic.materialReceivingCommit(map, new CommonLogic.CommitListener() {
                    @Override
                    public void onSuccess(String msg) {
                        dismissLoadingDialog();
                        showCommitSuccessDialog(msg, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                clearData();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showCommitFailDialog(error);
                    }
                });
            }

            @Override
            public void onCallback2() {

            }
        });
    }
    @OnFocusChange(R.id.et_material_order)
    void materialFocusChanage() {
        ModuleUtils.viewChange(ll_material_order, views);
        ModuleUtils.etChange(activity, et_material_order, editTexts);
        ModuleUtils.tvChange(activity, tv_material_order, textViews);
    }
    @OnFocusChange(R.id.et_supplier)
    void supplierFocusChanage() {
        ModuleUtils.viewChange(ll_supplier, views);
        ModuleUtils.etChange(activity, et_supplier, editTexts);
        ModuleUtils.tvChange(activity, tv_supplier, textViews);
    }
    @OnFocusChange(R.id.et_input_num)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(ll_input_num, views);
        ModuleUtils.etChange(activity, et_input_num, editTexts);
        ModuleUtils.tvChange(activity, tv_number, textViews);
    }

    @OnTextChanged(value = R.id.et_material_order, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }
    @OnTextChanged(value = R.id.et_supplier, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void supplierChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(SUPPLERWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(SUPPLERWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_input_num, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void num(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            float number=StringUtils.string2Float(s.toString());
            if (number>available_in_qty){
                showFailedDialog("输入数量不可大于可入库量", new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        et_input_num.setText("");
                        et_input_num.requestFocus();
                    }
                });
                return;
            }
            saveBean.setQty(s.toString().trim()                                                                                       );
        }
    }
    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case BARCODEWHAT://原始条码
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put("supplier_no",saveBean.getSupplier_no());
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            barcodeShow = barcodeBackBean.getShow();
                            available_in_qty= string2Float(barcodeBackBean.getAvailable_in_qty());
                            show();
                            saveBean.setItem_no(barcodeBackBean.getItem_no());//传入物料编号
                            et_input_num.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_material_order.setText("");
                                    et_material_order.requestFocus();
                                }
                            });
                        }
                    });
                    break;
                case SUPPLERWHAT://供应商
                    HashMap<String, String> supplierMap = new HashMap<>();
                    supplierMap.put("supplier_no", String.valueOf(msg.obj));
                    commonLogic.scanSupplier(supplierMap, new CommonLogic.ScanSupplierListener() {
                        @Override
                        public void onSuccess(ScanSupplierBean supplierBean) {
                            supplierShow = supplierBean.getShow();
                            show();
                            saveBean.setSupplier_no(supplierBean.getSupplier_no());//传入供应商编号
                            et_material_order.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_supplier.setText("");
                                    et_supplier.requestFocus();
                                }
                            });
                        }
                    });
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected void doBusiness() {
        clearData();
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.MATERIALRECEIVING;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(R.string.title_material_receiving);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_material_receiving;
    }

    public void clearData() {
        commonLogic = CommonLogic.getInstance(activity, activity.module, activity.mTimestamp.toString());
        saveBean=new SaveBean();
        et_input_num.setText("");
        et_material_order.setText("");
        et_supplier.setText("");
        barcodeShow="";
        supplierShow="";
        et_supplier.requestFocus();
        available_in_qty=0;
        show();
    }

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(supplierShow + "\\n" +barcodeShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())){
            includeDetail.setVisibility(View.VISIBLE);}else {
            includeDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
